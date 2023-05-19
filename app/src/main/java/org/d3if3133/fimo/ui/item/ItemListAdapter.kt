package org.d3if3133.fimo.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3133.fimo.databinding.ItemListItemBinding
import org.d3if3133.fimo.model.Item
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class ItemListAdapter(
    private val onItemClicked: (Item) -> Unit) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: Item) {
            binding.itemDate.text = dateFormatter.format(Date(item.tanggal))
            binding.itemName.text = item.itemName
            val currencyCode = "IDR" // Kode mata uang Rupiah (IDR)
            val currency = Currency.getInstance(currencyCode)

            val formatter = NumberFormat.getCurrencyInstance()
            formatter.currency = currency

            val price = formatter.format(item.itemPrice)
            binding.itemPrice.text = price
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }
}
