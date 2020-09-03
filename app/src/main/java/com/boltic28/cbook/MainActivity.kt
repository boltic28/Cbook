package com.boltic28.cbook

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private var dualScreen = false
    lateinit var contact: Contact
    lateinit var model: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        dualScreen = extraContainer != null
        fragmentManager = supportFragmentManager
        checkLayoutOrientationAndSetLayoutManager()
    }

    private fun checkLayoutOrientationAndSetLayoutManager() {
        if (dualScreen) openTwoFragments()
        else openMainFragment()
    }

    fun openContactFragment() {
        if (dualScreen) openTwoFragments()
        else openOneFragmentForContact()
    }

    private fun openTwoFragments() {
        setMainToolbar()
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.getInstance(), MainFragment.FRAG_TAG)
            .replace(
                R.id.extraContainer,
                ContactFragment.getInstance(),
                ContactFragment.FRAG_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun openOneFragmentForContact() {
        setContactToolbar()
        fragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ContactFragment.getInstance(),
                ContactFragment.FRAG_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun openMainFragment() {
        setMainToolbar()
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.getInstance(), MainFragment.FRAG_TAG)
            .commit()
    }

    private fun setMainToolbar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    private fun setContactToolbar() {
        supportActionBar?.apply {
            title = model.getContact().value?.name
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}