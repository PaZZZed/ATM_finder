package com.example.mobg5_53204.fragments.maps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mobg5_53204.R
import com.example.mobg5_53204.databinding.FragmentMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.navigation.fragment.findNavController
import com.example.mobg5_53204.fragments.atm.ATMDetailFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var viewModelFactory: MapsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentMapsBinding>(
            inflater,
            R.layout.fragment_maps, container, false
        )

        viewModelFactory = MapsViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MapsViewModel::class.java]


        // Initialize the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val uiSettings: UiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true

        // Coordinates for Brussels
        val bruxelles = LatLng(50.8503, 4.3517)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bruxelles, 12f))

        // Observe ATM coordinate changes
        viewModel.atmRecordList.observe(viewLifecycleOwner) { atmRecordList ->
            atmRecordList.forEach { record ->
                val fields = record.fields
                val latLng = LatLng(fields.coord[0], fields.coord[1])
                val markerOptions = MarkerOptions().position(latLng)
                    .icon(bitmapDescriptorFromVector(R.drawable.atm))
                val marker = googleMap.addMarker(markerOptions)

                marker?.tag = record.recordid  // Assuming record has an id field that can be used
            }
        }

        mMap.setOnMarkerClickListener { marker ->
            val atmId = marker.tag as? String ?: return@setOnMarkerClickListener false
            Log.d("MAPSS", "ATM ID clicked: $atmId")
            val fragment = ATMDetailFragment.newInstance(atmId)

            // Ajoute le fragment de détail au FrameLayout
            childFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

            true
        }
    }

    private fun bitmapDescriptorFromVector(resourceId: Int): BitmapDescriptor {
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        val smallMarker = Bitmap.createScaledBitmap(bitmap, 70, 70, false)
        return BitmapDescriptorFactory.fromBitmap(smallMarker)
    }
}
