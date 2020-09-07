package com.boltic28.cbook.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.boltic28.cbook.R
import com.boltic28.cbook.service.ContactService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity() {

    companion object {
        const val TAG = "cBookt"
    }

    private lateinit var model: MainActivityModel

    var service: ContactService? = null
    private var isBound = false
    private var dualScreen = false
    private lateinit var serviceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "CREATE MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(MainActivityModel::class.java)

        serviceConnect()
        bind()

        dualScreen = extraContainer != null
        checkLayoutOrientationAndSetLayoutManager()
    }

    private fun serviceConnect() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
                Log.d(TAG, "service is connected")
                val binder = iBinder as ContactService.ContactServiceBinder
                service = binder.getService()
                isBound = true
            }
            override fun onServiceDisconnected(componentName: ComponentName?) {
                Log.d(TAG, "service is disconnected")
                service = null
                isBound = false
            }
        }
    }

    private fun checkLayoutOrientationAndSetLayoutManager() {
        if (dualScreen) {
            openTwoFragments()
        } else {
            openMainFragment()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "DESTROY MainActivity")
        super.onDestroy()
        unbind()
    }

    fun openContactFragment() {
        if (dualScreen) openTwoFragments()
        else openOneFragmentForContact()
    }

    private fun openTwoFragments() {
        Log.d(TAG, "Open Dual screen")
        setMainToolbar()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment.getInstance(),
                MainFragment.FRAG_TAG
            )
            .replace(
                R.id.extraContainer,
                ContactFragment.getInstance(),
                ContactFragment.FRAG_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun openOneFragmentForContact() {
        Log.d(TAG, "Open contact screen")
        setContactToolbar()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                ContactFragment.getInstance(),
                ContactFragment.FRAG_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun openMainFragment() {
        Log.d(TAG, "Open List screen")
        setMainToolbar()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment.getInstance(),
                MainFragment.FRAG_TAG
            )
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
            title = model.getOne().name
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            setMainToolbar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bind() {
        Log.d(TAG, "bind service")
        val intent = Intent(this, ContactService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbind() {
        Log.d(TAG, "unbind service")
        unbindService(serviceConnection)
        serviceConnection.onServiceDisconnected(this.componentName)
    }
}