 package com.ajsherrell.android.quiz_hyperbaric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ajsherrell.android.quiz_hyperbaric.databinding.ActivityMainBinding
import timber.log.Timber

 class MainActivity : AppCompatActivity() {

     //nav drawer made with https://www.raywenderlich.com/6014-the-navigation-architecture-component-tutorial-getting-started#toc-anchor-011
     private lateinit var drawerLayout: DrawerLayout
     private lateinit var appBarConfiguration: AppBarConfiguration
     private lateinit var navController: NavController

    //data binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity has started in onCreate!!!")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //set up actionbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //set up nav menu
        binding.navigationView.setupWithNavController(navController)

    }

     override fun onSupportNavigateUp(): Boolean {
         return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
     }

     override fun onBackPressed() {
         if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
             drawerLayout.closeDrawer(GravityCompat.START)
         } else {
             super.onBackPressed()
         }
     }
}
