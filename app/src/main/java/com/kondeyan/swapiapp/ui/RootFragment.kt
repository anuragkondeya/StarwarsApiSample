package com.kondeyan.swapiapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kondeyan.swapiapp.MainActivity
import com.kondeyan.swapiapp.R
import com.kondeyan.swapiapp.databinding.FragmentRootBinding
import com.kondeyan.swapiapp.databinding.FragmentSplashScreenBinding
import com.kondeyan.swapiapp.ui.model.ViewData
import kotlinx.coroutines.launch


class RootFragment : Fragment() {

    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }

    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    val viewModel: SWViewModel by activityViewModels()

    private val recyclerViewAdapter = SWRecyclerViewAdapter()

    private fun getItemClickListner(): IListItemClickListener {
        return object : IListItemClickListener {
            override fun onItemClicked(data: ViewData, position: Int) {
                activity.startNewFragment(DetailsListFragment.newInstance(data.title))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRootBinding.inflate(LayoutInflater.from(activity), container, false)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
            recyclerViewAdapter.itemClickListenerInstance = getItemClickListner()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRootData().observe(activity, {
            lifecycleScope.launch {
                recyclerViewAdapter.submitData(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = RootFragment()

    }
}
