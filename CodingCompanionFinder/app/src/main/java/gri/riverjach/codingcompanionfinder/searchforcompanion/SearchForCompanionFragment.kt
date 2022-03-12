package gri.riverjach.codingcompanionfinder.searchforcompanion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gri.riverjach.codingcompanionfinder.MainActivity
import gri.riverjach.codingcompanionfinder.databinding.FragmentSearchForCompanionBinding

class SearchForCompanionFragment : Fragment() {

    private lateinit var accessToken: String

    private var petRecyclerView: RecyclerView? = null

    private lateinit var companionAdapter: CompanionAdapter

    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var fragmentSearchForCompanionBinding: FragmentSearchForCompanionBinding
    private lateinit var searchForCompanionViewModel: SearchForCompanionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchForCompanionBinding =
            FragmentSearchForCompanionBinding.inflate(
                inflater,
                container, false
            )
        searchForCompanionViewModel = ViewModelProvider(this)
            .get(SearchForCompanionViewModel::class.java)
        fragmentSearchForCompanionBinding.searchForCompanionViewModel = searchForCompanionViewModel
        fragmentSearchForCompanionBinding.lifecycleOwner = this
        return fragmentSearchForCompanionBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        fragmentSearchForCompanionBinding.searchButton.setOnClickListener {
            try {
                val inputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager!!.hideSoftInputFromWindow(
                    activity?.getCurrentFocus()?.getWindowToken(), 0
                )
            } catch (e: Exception) {
                // only happens when the keyboard is already closed
            }
            searchForCompanionViewModel.searchForCompanions()
        }
        setupSearchForCompanions()
        super.onActivityCreated(savedInstanceState)
    }


    private fun setupSearchForCompanions() {
        searchForCompanionViewModel.accessToken =
            (activity as MainActivity).accessToken
        searchForCompanionViewModel.petFinderService =
            (activity as MainActivity).petFinderService!!
        viewManager = LinearLayoutManager(context)
        companionAdapter = CompanionAdapter(
            searchForCompanionViewModel.animals.value ?: arrayListOf(),
            this
        )
        petRecyclerView = fragmentSearchForCompanionBinding
            .petRecyclerView.apply {
                layoutManager = viewManager
                adapter = companionAdapter
            }
        searchForCompanionViewModel.animals.observe(viewLifecycleOwner, {
            companionAdapter.animals = it ?: arrayListOf()
            companionAdapter.notifyDataSetChanged()
        })
    }
}
