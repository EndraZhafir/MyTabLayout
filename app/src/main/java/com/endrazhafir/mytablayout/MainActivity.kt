package com.endrazhafir.mytablayout

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var isOnHomepage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set initial fragment to RegisterFragment
        if (savedInstanceState == null) {
            replaceFragment(RegisterFragment())
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
                navigateToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun navigateToLogin() {
        isOnHomepage = false
        invalidateOptionsMenu() // Refresh menu
        replaceFragment(LoginFragment())
    }

    fun navigateToRegister() {
        isOnHomepage = false
        invalidateOptionsMenu()
        replaceFragment(RegisterFragment())
    }

    fun navigateToHomepage() {
        isOnHomepage = true
        invalidateOptionsMenu() // Refresh menu to show logout
        replaceFragment(HomepageFragment())
    }
}