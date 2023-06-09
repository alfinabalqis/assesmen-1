package com.d3if3133.fimo.ui.bank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d3if3133.fimo.R
import com.d3if3133.fimo.databinding.BankItemBinding
import com.d3if3133.fimo.model.Bank
import com.d3if3133.fimo.network.BankApi

class BankAdapter : RecyclerView.Adapter<BankAdapter.ViewHolder>() {
    private val data = mutableListOf<Bank>()

    fun updateData(newData: List<Bank>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: BankItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(bank: Bank) = with(binding) {
            bankName.text = bank.nama
            bankAlamat.text = bank.alamat
            bankWebsite.text = bank.website
            //imageView.setImageResource(R.drawable.angsa)
            Glide.with(bankImageView.context)
                .load(BankApi.getBankURL(bank.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(bankImageView)

            root.setOnClickListener {
                val message = root.context.getString(R.string.message, bank.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BankItemBinding.inflate(inflater, parent, false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}