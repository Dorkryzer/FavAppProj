package com.example.favappproj

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.favappproj.api.ListItem
import com.example.favappproj.database.DBitem
import com.example.favappproj.databinding.ListItemFreeBinding

class FreeAppsAdapter(
    private val listItems: List<DBitem>,
    private val OnAppClick: (DBitem) -> Unit
): RecyclerView.Adapter<FreeAppsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FreeAppsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFreeBinding.inflate(inflater,parent,false)
        return FreeAppsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FreeAppsViewHolder,
        position: Int
    ) {
        val item = listItems[position]
        holder.bind(item)
        holder.favButton.setOnClickListener {
            OnAppClick(item)
            return@setOnClickListener
        }
    }

    override fun getItemCount(): Int = listItems.size
}


class FreeAppsViewHolder(
    private val binding: ListItemFreeBinding
): RecyclerView.ViewHolder(binding.root){
    val favButton: ImageView = itemView.findViewById(R.id.free_item_fav_button)
    fun bind(listItem: DBitem){
        binding.tvTitle.text = listItem.name
        binding.freeItemImageView.load(listItem.url)
        if(listItem.isMarked){
            binding.freeItemFavButton.setImageResource(R.drawable.ic_favorite_red)
        }
        else
            binding.freeItemFavButton.setImageResource(R.drawable.ic_favorite_grey)

    }
}