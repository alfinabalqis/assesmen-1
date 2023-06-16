package com.d3if3133.fimo.ui.Item

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.d3if3133.fimo.R
import com.d3if3133.fimo.data.ItemRoomDatabase
import com.d3if3133.fimo.databinding.FragmentItemDetailBinding
import com.d3if3133.fimo.model.Item
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import java.util.*

/**
 * [ItemDetailFragment] displays the details of the selected item.
 */
class ItemDetailFragment : Fragment() {
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    lateinit var item: Item

    private val viewModel: FimoViewModel by lazy {
        val db = ItemRoomDatabase.getDatabase(requireContext())
        val factory = FimoViewModelFactory(db.itemDao())
        ViewModelProvider(this, factory)[FimoViewModel::class.java]
    }

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(item: Item) {
         val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        val currencyCode = "IDR" // Kode mata uang Rupiah (IDR)
        val currency = Currency.getInstance(currencyCode)

        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = currency

        val formattedPrice = formatter.format(item.itemPrice)

        binding.apply {
            itemName.text = item.itemName
            itemDate.text = dateFormatter.format(Date(item.tanggal))
            itemPrice.text = formattedPrice
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    /**
     * Navigate to the Edit item screen.
     */
    private fun editItem() {
        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            getString(R.string.edit_fragment_title),
            item.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                Toast.makeText(requireContext(), "Sukses Menghapus", Toast.LENGTH_SHORT).show()
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.shareItem.setOnClickListener { shareData() }
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun shareData() {
        val message = getString(
            R.string.bagikan_template,
            binding.itemName.text,
            binding.itemPrice.text,
            binding.itemDate.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}