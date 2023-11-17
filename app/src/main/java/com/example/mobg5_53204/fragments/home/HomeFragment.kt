package com.example.mobg5_53204.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobg5_53204.R
import com.example.mobg5_53204.databinding.FragmentHomeBinding
import com.example.mobg5_53204.model.recycler.ATMAdapter
import com.example.mobg5_53204.model.recycler.ATMListener
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ATMAdapter
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        viewModelFactory = HomeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]


        // CLICKER SUR UN ITEM ---> passer l'id de l'atm
        adapter = ATMAdapter(ATMListener { atmId ->
            Log.d("HomeFragment", "ATM ID clicked: $atmId")
            val action = HomeFragmentDirections.actionHomeFragmentToATMDetailFragment(atmId)
            findNavController().navigate(action)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter // Maintenant, 'adapter' a été initialisé

        viewModel.atmList.observe(viewLifecycleOwner, Observer { atmRecords ->
            atmRecords?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }


}
