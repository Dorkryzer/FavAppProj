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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.size.OriginalSize
import com.example.favappproj.database.DBitem
import com.example.favappproj.databinding.FragmentFavoriteBinding
import com.example.favappproj.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import kotlin.getValue


class FavoriteFragment : Fragment() {
    val appViewModel: AppViewModel by navGraphViewModels(R.id.nav_graph)
    private var _binding : FragmentFavoriteBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "Cannot access binding because it is null" }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater,container, false)
        binding.combinedList.layoutManager = GridLayoutManager(context,2)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                appViewModel.favoriteItems.collect { items ->
                    binding.combinedList.adapter = FavAppsAdapter(items){item->
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
        binding.combinedList.adapter?.notifyDataSetChanged()
    }

}