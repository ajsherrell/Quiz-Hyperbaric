 package com.ajsherrell.android.quiz_hyperbaric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ajsherrell.android.quiz_hyperbaric.databinding.ActivityMainBinding
import com.ajsherrell.android.quiz_hyperbaric.databinding.NavHeaderBinding
import com.ajsherrell.android.quiz_hyperbaric.viewModel.QuizListViewModel
import timber.log.Timber
import java.lang.IllegalArgumentException

 class MainActivity : AppCompatActivity() {

     //nav drawer made with https://www.raywenderlich.com/6014-the-navigation-architecture-component-tutorial-getting-started#toc-anchor-011
     private lateinit var drawerLayout: DrawerLayout
     private lateinit var appBarConfiguration: AppBarConfiguration
     private lateinit var navController: NavController
     private var model: QuizListViewModel? = null

    //data binding
    private lateinit var binding: ActivityMainBinding
     private lateinit var navHeaderBinding: NavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity has started in onCreate!!!")
        setupBinding()
        drawerLayout = binding.drawerLayout

        navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //set up actionbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //set up nav menu
        binding.navigationView.setupWithNavController(navController)

        setupViewModel()
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

     private fun setupViewModel() {
         try {
             val viewModelProvider = ViewModelProvider(
                 navController.getViewModelStoreOwner(R.id.nav_graph),
                 ViewModelProvider.AndroidViewModelFactory(application)
             )
             model = viewModelProvider.get(QuizListViewModel::class.java)
             navHeaderBinding.model = model
             //todo: loadProfile() from model?.loadProfile()
         } catch (e: IllegalArgumentException) {
             e.printStackTrace()
         }
     }

     private fun setupBinding() {
         binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

         navHeaderBinding = DataBindingUtil.inflate(
             layoutInflater, R.layout.nav_header, binding.navigationView, false
         )

         navHeaderBinding.personImage.setOnClickListener{
             navController.navigate(R.id.edit_profile_fragment)

             drawerLayout.closeDrawer(GravityCompat.START)
         }

         binding.navigationView.addHeaderView(navHeaderBinding.root)
     }
}

