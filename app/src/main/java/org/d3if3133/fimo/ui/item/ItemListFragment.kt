package org.d3if3133.fimo.ui.item

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.d3if3133.fimo.R
import org.d3if3133.fimo.databinding.ItemListFragmentBinding
import org.d3if3133.fimo.db.FimoApplication
import org.d3if3133.fimo.db.SettingDataStore
import org.d3if3133.fimo.db.dataStore

class ItemListFragment : Fragment() {
    private var isLinearLayout = true

    private val viewModel: FimoViewModel by activityViewModels {
        FimoViewModelFactory(
            (activity?.application as FimoApplication).database.itemDao()
        )
    }

    private var _binding: ItemListFragmentBinding? = null
    private val binding get() = _binding!!

    private val layoutDataStore: SettingDataStore by lazy {
        SettingDataStore(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            isLinearLayout = it
            setLayout()
            activity?.invalidateOptionsMenu()
        }

        val adapter = ItemListAdapter {
            val action =
                ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }

    private fun setLayout() {
        binding.recyclerView.layoutManager = if (isLinearLayout) LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
    }

    private fun setIcon(menuItem: MenuItem) {
        val iconId = if (isLinearLayout)
            R.drawable.ic_grid else
            R.drawable.ic_list
        menuItem.icon = ContextCompat.getDrawable(requireContext(), iconId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)

        val menuItem = menu.findItem(R.id.action_switch_layout)
        setIcon(menuItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_switch_layout) {
            lifecycleScope.launch {
                layoutDataStore.saveLayout(!isLinearLayout, requireContext())
            }
        }

        when(item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_itemListFragment_to_aboutFragment
                )
                return true
            }

            R.id.menu_hapus -> {
                hapusData()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteAllItems()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}
