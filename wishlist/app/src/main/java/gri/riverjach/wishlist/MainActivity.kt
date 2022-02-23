package gri.riverjach.wishlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import gri.riverjach.wishlist.databinding.ActivityMainBinding
import gri.riverjach.wishlist.databinding.ViewInputBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = WishlistAdapter(this, viewModel.getWishlists()) {
            startActivity(WishlistDetailActivity.newIntent(it, this))
        }

        binding.buttonAddList.setOnClickListener { showAddListInput() }
    }

    private fun showAddListInput() {
        BottomSheetDialog(this).apply {
            val bottomSheetBinding = ViewInputBottomSheetBinding.inflate(layoutInflater)
            bottomSheetBinding.title.text = getString(R.string.title_list_person)
            bottomSheetBinding.textField.hint = getString(R.string.title_list_person)
            bottomSheetBinding.buttonSave.setOnClickListener {
                viewModel.saveNewList(bottomSheetBinding.editTextInput.text.toString())
                this.dismiss()
            }
            setContentView(bottomSheetBinding.root)
            show()
        }
    }
}