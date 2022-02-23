package gri.riverjach.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gri.riverjach.wishlist.databinding.ItemWishlistBinding


class WishlistAdapter(
    lifecycleOwner: LifecycleOwner,
    private val wishlist: LiveData<List<Wishlist>>,
    private val onItemSelected: (Wishlist) -> Unit
) : RecyclerView.Adapter<WishListViewHolder>() {

    init {
        wishlist.observe(lifecycleOwner, { notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        return WishListViewHolder(
            ItemWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemSelected
        )
    }

    override fun getItemCount(): Int {
        return wishlist.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        wishlist.value?.get(position)?.let { holder.bind(it) }
    }
}

class WishListViewHolder(
    private val binding: ItemWishlistBinding,
    val onItemSelected: (Wishlist) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private val wishItemAdapter = WishItemAdapter()

    init {
        binding.recyclerWishes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerWishes.adapter = wishItemAdapter
    }

    fun bind(wishlist: Wishlist) {
        binding.title.text = wishlist.receiver
        binding.root.setOnClickListener {
            onItemSelected(wishlist)
        }
        wishItemAdapter.items.clear()
        wishItemAdapter.items.addAll(wishlist.wishes)
        wishItemAdapter.notifyDataSetChanged()
    }
}

class WishItemAdapter : RecyclerView.Adapter<WishViewHolder>() {

    val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        return WishViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class WishViewHolder(private val view: TextView) : RecyclerView.ViewHolder(view) {
    fun bind(wish: String) {
        view.text = wish
    }
}

