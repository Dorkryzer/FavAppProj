package com.example.favappproj



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.favappproj.database.DBitem
import com.example.favappproj.databinding.ListItemFavoriteBinding

class FavAppsAdapter(
    private val listItems: List<DBitem>,
    private val OnAppClick: (DBitem) -> Unit
): RecyclerView.Adapter<FavAppsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavAppsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFavoriteBinding.inflate(inflater,parent,false)
        return FavAppsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavAppsViewHolder,
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


class FavAppsViewHolder(
    private val binding: ListItemFavoriteBinding
): RecyclerView.ViewHolder(binding.root){
    val favButton: ImageView = itemView.findViewById(R.id.fav_item_fav_button)
    fun bind(dBitem: DBitem){
        binding.paidItemImageView.load(dBitem.url)
        if(dBitem.isMarked){
            binding.favItemFavButton.setImageResource(R.drawable.ic_favorite_red)
        }
        else
            binding.favItemFavButton.setImageResource(R.drawable.ic_favorite_grey)


    }
}