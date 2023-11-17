package com.example.mobg5_53204.fragments.favoris

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobg5_53204.databinding.FragmentFavorisBinding
import com.example.mobg5_53204.model.recycler.ATMAdapter
import com.example.mobg5_53204.model.recycler.ATMListener

class FavorisFragment : Fragment() {
    private lateinit var binding: FragmentFavorisBinding
    private lateinit var viewModel: FavorisViewModel
    private lateinit var viewModelFactory: FavorisViewModelFactory
    private lateinit var favorisAdapter: ATMAdapter  // Votre adaptateur de RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavorisBinding.inflate(inflater, container, false)
        viewModelFactory = FavorisViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavorisViewModel::class.java]

        // Instanciez et configurez l'adaptateur
        favorisAdapter = ATMAdapter(ATMListener { atmId ->
            val action = FavorisFragmentDirections.actionFavorisFragmentToATMDetailFragment(atmId)
            findNavController().navigate(action)
        })

        binding.favorisRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favorisRecyclerView.adapter = favorisAdapter


        // Observez les favoris de l'utilisateur
        viewModel.userFavoris.observe(viewLifecycleOwner, Observer { favorisList ->
            Log.d("FAVORIS OBSERVE", "LISTE DE FAVORIS: $favorisList")

            viewModel.getFavoriteATMRecords()
        })

        // Observez les données des ATMRecords favoris
        viewModel.favoriteATMRecords.observe(viewLifecycleOwner, Observer { atmFieldsList ->
            // Mettez à jour l'interface utilisateur
            favorisAdapter.submitList(atmFieldsList)
        })


        binding.clearFavorisButton.setOnClickListener {
            viewModel.removeFavoris()
            Toast.makeText(requireContext(), "Favoris supprimés", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

}
