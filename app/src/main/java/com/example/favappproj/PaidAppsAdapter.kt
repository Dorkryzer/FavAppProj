package com.example.favappproj
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.favappproj.api.ListItem
import com.example.favappproj.database.DBitem
import com.example.favappproj.databinding.ListItemFreeBinding
import com.example.favappproj.databinding.ListItemPaidBinding

class PaidAppsAdapter(
    private val listItems: List<DBitem>,
    private val OnAppClick: (DBitem) -> Unit
): RecyclerView.Adapter<PaidAppsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaidAppsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaidBinding.inflate(inflater, parent, false)
        return PaidAppsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PaidAppsViewHolder,
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
class PaidAppsViewHolder(
    private val binding: ListItemPaidBinding,

) : RecyclerView.ViewHolder(binding.root) {
    val favButton: ImageView = itemView.findViewById(R.id.paid_item_fav_button)
    fun bind(listItem: DBitem) {
        binding.tvTitle.text = listItem.name

        binding.paidItemImageView.load(listItem.url)
        if (listItem.isMarked)
            binding.paidItemFavButton.setImageResource(R.drawable.ic_favorite_red)
        else
            binding.paidItemFavButton.setImageResource(R.drawable.ic_favorite_grey)

    }
}
