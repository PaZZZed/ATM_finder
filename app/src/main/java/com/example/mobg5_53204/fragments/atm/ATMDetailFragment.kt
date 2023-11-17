package com.example.mobg5_53204.fragments.atm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobg5_53204.databinding.FragmentDetailBinding
import com.example.mobg5_53204.model.user.UserSession

class ATMDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: ATMDetailViewModel
    private lateinit var viewModelFactory: ATMDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModelFactory = ATMDetailViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ATMDetailViewModel::class.java]

        val atmId = arguments?.getString("atmId")
        val userEmail = UserSession.userEmail
        if (atmId != null) {
            viewModel.fetchATMDetailsById(atmId)
            viewModel.checkIfFavori(atmId, userEmail)
        }


        binding.favorisButton.setOnClickListener {
            val atmId = arguments?.getString("atmId")
            val userEmail = UserSession.userEmail  // Get the user's email
            if (atmId != null && userEmail != null) {
                viewModel.addFavoris(atmId, userEmail)
                Toast.makeText(requireContext(), "Favori ajouté", Toast.LENGTH_SHORT).show()
                binding.favorisButton.visibility = View.GONE
            } else {
                Toast.makeText(
                    requireContext(),
                    "Erreur lors de l'ajout aux favoris",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.isFavori.observe(viewLifecycleOwner, Observer { isFavori ->
            if (isFavori) {
                binding.favorisButton.visibility = View.GONE
            } else {
                binding.favorisButton.visibility = View.VISIBLE
            }
        })

        viewModel.agence.observe(viewLifecycleOwner, Observer { agence ->
            binding.agenceTextView.text = agence
            Log.d("agence", "ATM ID clicked: $agence")
        })

        viewModel.adresseFr.observe(viewLifecycleOwner, Observer { adresse ->
            binding.adresseFrTextView.text = adresse
        })

        viewModel.adresseNl.observe(viewLifecycleOwner, Observer { adresse ->
            binding.adresseNlTextView.text = adresse
        })


        return binding.root
    }

    companion object {
        fun newInstance(atmId: String): ATMDetailFragment {
            val fragment = ATMDetailFragment()
            val args = Bundle()
            args.putString("atmId", atmId)
            fragment.arguments = args
            return fragment
        }
    }
}
