package com.kondeyan.swapiapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kondeyan.swapiapp.MainActivity
import com.kondeyan.swapiapp.databinding.FragmentSplashScreenBinding
import com.kondeyan.swapiapp.ui.base.State

class SplashScreenFragment : Fragment() {

    private val activity: MainActivity by lazy {
        getActivity() as MainActivity
    }

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    val viewModel: SWViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSplashScreenBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    private fun openNextFragment() {
        activity.startNewFragment(RootFragment.newInstance())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.networkStateLivedata.observe(viewLifecycleOwner, {
            if (it == State.SUCCESS) openNextFragment()
            else activity.handleDefaultStates(it)
        })
        viewModel.fetchRootData()
    }

    companion object {
        fun newInstance() = SplashScreenFragment()
    }
}