package com.kondeyan.swapiapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.kondeyan.swapiapp.MainActivity
import com.kondeyan.swapiapp.databinding.FragmentDetailsBinding
import com.kondeyan.swapiapp.ui.base.State


private const val KEY_URL = "key_url"

class DetailsFragment : Fragment() {

    private var url: String? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    val viewModel: SWViewModel by activityViewModels()

    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(KEY_URL)
        }
    }

    private val responseStateObserver = Observer<State> { state ->
        if(state == State.SUCCESS) {
            binding.details.text= viewModel.details
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.networkStateLivedata.observe(viewLifecycleOwner,responseStateObserver)
        viewModel.fetchDetailsData(url?:"")
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_URL, url)
                }
            }
    }
}