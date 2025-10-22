package com.example.favappproj

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favappproj.database.DBitem
import com.example.favappproj.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    val appViewModel: AppViewModel by navGraphViewModels(R.id.nav_graph)
    private var _binding : FragmentHomeBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding because it is null" }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        binding.freeApps.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.paidApps.layoutManager = LinearLayoutManager(context)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                appViewModel.freeDBItems.collect { items ->
                    binding.freeApps.adapter = FreeAppsAdapter(items){item->
                        markItem(item)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                appViewModel.paidDBItems.collect { items ->
                    binding.paidApps.adapter = PaidAppsAdapter(items){item ->
                        markItem(item)
                    }
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun markItem(item: DBitem){
        item.isMarked = !item.isMarked
        appViewModel.updateDbItem(item)
        if(item.isFree)
            binding.freeApps.adapter?.notifyDataSetChanged()
        else
            binding.paidApps.adapter?.notifyDataSetChanged()
    }

}