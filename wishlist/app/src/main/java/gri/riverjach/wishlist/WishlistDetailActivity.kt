package gri.riverjach.wishlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import gri.riverjach.wishlist.databinding.ActivityWishlistDetailBinding
import gri.riverjach.wishlist.databinding.ViewInputBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_WISHLIST = "EXTRA_WISHLIST"

        fun newIntent(wishlist: Wishlist, context: Context): Intent {
            return Intent(context, WishlistDetailActivity::class.java).apply {
                putExtra(EXTRA_WISHLIST, wishlist.id)
            }
        }
    }

    private val viewModel: DetailViewModel by viewModel()
    private val wishlistAdapter: WishItemAdapter = WishItemAdapter()
    private lateinit var binding: ActivityWishlistDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerWishes.layoutManager = LinearLayoutManager(this)
        binding.recyclerWishes.adapter = wishlistAdapter
        viewModel.getWishlist(intent.getIntExtra(EXTRA_WISHLIST, 0)).observe(this, {
            render(it)
        })
    }

    private fun render(wishlist: Wishlist) {
        binding.textViewTitle.text = wishlist.receiver
        wishlistAdapter.items.clear()
        wishlistAdapter.items.addAll(wishlist.wishes)
        wishlistAdapter.notifyDataSetChanged()

        binding.buttonAddList.setOnClickListener { showAddListInput(wishlist) }
    }

    private fun showAddListInput(wishlist: Wishlist) {
        BottomSheetDialog(this).apply {
            val bottomSheetBinding = ViewInputBottomSheetBinding.inflate(layoutInflater)
            bottomSheetBinding.title.text = getString(R.string.title_add_wish)
            bottomSheetBinding.textField.hint = getString(R.string.title_add_wish)
            bottomSheetBinding.buttonSave.setOnClickListener {
                viewModel.saveNewItem(wishlist, bottomSheetBinding.editTextInput.text.toString())
                this.dismiss()
            }
            setContentView(bottomSheetBinding.root)
            show()
        }
    }
}
