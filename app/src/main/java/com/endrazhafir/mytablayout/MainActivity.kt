package com.endrazhafir.mytablayout

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        // Start with Login fragment (index 1)
        viewPager.currentItem = 1

        supportActionBar?.elevation = 0f
    }

    override fun onBackPressed() {
        if (isOnHomepage) {
            navigateBackToTabs()
        } else {
            super.onBackPressed()
        }
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
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                navigateBackToTabs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateToHomepage() {
        try {
            Log.d("MainActivity", "navigateToHomepage called")
            
            // Replace the entire layout with homepage
            isOnHomepage = true
            invalidateOptionsMenu()

            Log.d("MainActivity", "Setting visibility")
            // Hide tabs and viewpager, show fragment container
            tabLayout.visibility = android.view.View.GONE
            viewPager.visibility = android.view.View.GONE
            fragmentContainer.visibility = android.view.View.VISIBLE

            Log.d("MainActivity", "Starting fragment transaction")
            // Add Homepage Fragment to the dedicated container
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomepageFragment())
                .addToBackStack("homepage")
                .commitNow()
                
            Log.d("MainActivity", "Fragment transaction completed")
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in navigateToHomepage", e)
            e.printStackTrace()
            Toast.makeText(this, "Error navigating to homepage: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateBackToTabs() {
        try {
            isOnHomepage = false
            invalidateOptionsMenu()

            // Show tabs and viewpager, hide fragment container
            tabLayout.visibility = android.view.View.VISIBLE
            viewPager.visibility = android.view.View.VISIBLE
            fragmentContainer.visibility = android.view.View.GONE

            // Remove the homepage fragment from back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }

            // Navigate to login tab (index 1)
            viewPager.currentItem = 1
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error during logout", Toast.LENGTH_SHORT).show()
        }
    }
}