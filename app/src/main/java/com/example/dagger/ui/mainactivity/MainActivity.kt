package com.example.dagger.ui.mainactivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.dagger.BaseActivity
import com.example.dagger.R
import com.example.dagger.databinding.ActivityMainBinding
import com.example.dagger.ui.mainactivity.posts.PostFragment
import com.example.dagger.ui.mainactivity.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    fun init() {
        val navigationController =
            Navigation.findNavController(this, R.id.nav_host_fragment_container)
        NavigationUI.setupActionBarWithNavController(
            this,
            navigationController,
            binding.drawerLayout
        )
        NavigationUI.setupWithNavController(binding.navView, navigationController)
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logOut()
            }

            //Always reference the icon of action bar , here the drawer layout icon
            android.R.id.home -> {
                with(binding.drawerLayout) {
                    if (isDrawerOpen(GravityCompat.START)) {
                        closeDrawer(GravityCompat.START)
                        return true
                    } else {
                        return false
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        when (menu.itemId) {
            R.id.nav_profile -> {
                //for clearing back stack
                val option = NavOptions.Builder()
                    .setPopUpTo(R.id.main,true)
                    .build()

                Navigation.findNavController(this, R.id.nav_host_fragment_container)
                    .navigate(R.id.profileScreen,null,option)
            }

            R.id.nav_post -> {
                //Open the post screen when we are not on the post screen
                //If on post screen ,perform no action ,so to avoid adding post screen to back stack every time we click on post fragment
                if (isValidDestination(R.id.postScreen)){
                    Navigation.findNavController(this, R.id.nav_host_fragment_container)
                        .navigate(R.id.postScreen)
                }
            }
        }

        menu.setChecked(true)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //to open drawer on click of drawer icon
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment_container
            ), binding.drawerLayout
        )
    }

    fun isValidDestination(destination:Int):Boolean{
        return destination != Navigation.findNavController(this,R.id.nav_host_fragment_container).currentDestination?.id
    }
}
