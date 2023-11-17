package com.example.mobg5_53204

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.mobg5_53204.databinding.ActivityMainBinding
import com.example.mobg5_53204.fragments.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        // Initialisation de NavController
        val navController = this.findNavController(R.id.myNavHostFragment)


        // Configuration des destinations de niveau supérieur
        appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.fragmentAbout,
                R.id.mapsFragment,
                R.id.homeFragment,
                R.id.favorisFragment,
                R.id.loginActivity
            )
            .setDrawerLayout(drawerLayout)
            .build()


        // Configuration du NavigationView pour travailler avec NavController
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Configuration de la barre d'action pour travailler avec NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
