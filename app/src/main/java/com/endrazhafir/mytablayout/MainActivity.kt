package com.endrazhafir.mytablayout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var fragmentContainer: android.widget.FrameLayout
    private var isOnHomepage = false

    companion object {
        private val TAB_TITLES = arrayOf("Register", "Login")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        fragmentContainer = findViewById(R.id.fragment_container)

        sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        // Login fragment (index 1)
        viewPager.currentItem = 1

        supportActionBar?.elevation = 0f
        
        // Set ActionBar ke warna biru
        supportActionBar?.let { actionBar ->
            val blueColor = ContextCompat.getColor(this, R.color.blue)
            actionBar.setBackgroundDrawable(ColorDrawable(blueColor))
        }
        
        // Tombol back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isOnHomepage) {
                    navigateBackToTabs()
                } else {
                    finish()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isOnHomepage) {
            menuInflater.inflate(R.menu.menu_logout, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                navigateBackToTabs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateToHomepage() {
        try {
            Log.d("MainActivity", "navigateToHomepage called")
            
            // Mengganti layout dengan homepage fragment
            isOnHomepage = true
            
            Log.d("MainActivity", "Setting visibility")
            // Hide tabs dan viewpager, show fragment container
            tabLayout.visibility = android.view.View.GONE
            viewPager.visibility = android.view.View.GONE
            fragmentContainer.visibility = android.view.View.VISIBLE

            Log.d("MainActivity", "Starting fragment transaction")
            // Tambah homepage ke fragment container
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomepageFragment())
            transaction.addToBackStack("homepage")
            transaction.commit()
            
            // Update menu setelah navigasi
            invalidateOptionsMenu()
                
            Log.d("MainActivity", "Fragment transaction completed")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in navigateToHomepage", e)
            e.printStackTrace()
        }
    }

    private fun navigateBackToTabs() {
        try {
            isOnHomepage = false
            invalidateOptionsMenu()

            // Show tabs dan viewpager, hide fragment container
            tabLayout.visibility = android.view.View.VISIBLE
            viewPager.visibility = android.view.View.VISIBLE
            fragmentContainer.visibility = android.view.View.GONE

            // Menghapus homepage fragment dari back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }

            // Navigasi ke login fragment (index 1)
            viewPager.currentItem = 1
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}