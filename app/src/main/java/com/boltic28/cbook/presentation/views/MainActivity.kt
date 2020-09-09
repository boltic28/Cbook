package com.boltic28.cbook.presentation.views

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.Process
import com.boltic28.cbook.presentation.interfaces.Worker
import com.boltic28.cbook.presentation.models.MainActivityModel
import com.boltic28.cbook.service.ContactService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity(),
    Worker {

    companion object {
        const val TAG = "cBookt"
        const val CONTACT_ID = "id"
    }

    private lateinit var model: MainActivityModel

    private var service: ContactService? = null
    private var isBound = false
    private var isTarget = false
    private var dualScreen = false
    private lateinit var serviceConnection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MAIN: CREATE MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceConnect()
        bind()

        model = ViewModelProviders.of(this).get(MainActivityModel::class.java)
        tryToOpenContact(intent)

        dualScreen = extraContainer != null
        checkLayoutOrientationAndSetLayoutManager()
    }

    override fun onNewIntent(intent: Intent?) {
        Log.d(TAG, "MAIN: took new Intent ${intent.toString()}")
        super.onNewIntent(intent)
        tryToOpenContact(intent)
    }

    private fun tryToOpenContact(intent: Intent?) {
        intent?.extras?.containsKey(CONTACT_ID)?.let {
            if (it) {
                model.dataBase.setContact(intent.extras!!.getLong(CONTACT_ID, 1))
                Log.d(TAG, "MAIN: contact from notification is ${model.dataBase.getOne().name}")
                isTarget = true
            }
        }
    }

    private fun serviceConnect() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
                Log.d(TAG, "MAIN: service is connected")
                val binder = iBinder as ContactService.ContactServiceBinder
                service = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(componentName: ComponentName?) {
                Log.d(TAG, "MAIN: service is disconnected")
                service = null
                isBound = false
            }
        }
    }

    private fun checkLayoutOrientationAndSetLayoutManager() {
        Handler(mainLooper).postDelayed({
            if (dualScreen) {
                openTwoFragments()
            } else {
                openMainFragment()
            }
        }, 100)
    }

    override fun onDestroy() {
        Log.d(TAG, "MAIN: DESTROY MainActivity")
        unbind()
        super.onDestroy()
    }

    private fun openTwoFragments() {
        Log.d(TAG, "MAIN: Open Dual screen")
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
            .commit()
    }

    private fun openOneFragmentForContact() {
        Log.d(TAG, "MAIN: Open contact screen")
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
        Log.d(TAG, "MAIN: Open List screen")
        setMainToolbar()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment.getInstance(),
                MainFragment.FRAG_TAG
            )
            .commit()
        if (isTarget) {
            openContactFragment()
        }
    }

    private fun setMainToolbar() {
        supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    override fun setContactToolbar() {
        supportActionBar?.apply {
            title = model.getOne().name
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        isTarget = false
    }

    override fun onBackPressed() {
        if (dualScreen) finish()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            isTarget = false
            supportFragmentManager.popBackStack()
            openMainFragment()
            setMainToolbar()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bind() {
        val intent = Intent(this, ContactService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbind() {
        unbindService(serviceConnection)
        serviceConnection.onServiceDisconnected(this.componentName)
    }



    override fun openContactFragment() {
        if (dualScreen) openTwoFragments()
        else openOneFragmentForContact()
    }

    override fun startWork(contact: Contact) {
        service?.startWork(contact)
    }

    override fun getProcessFor(contact: Contact): Process? {
        return service?.getProcessByContactIdIfExist(contact.id)
    }

    override fun mGetProcesses(): LiveData<List<Process>>? {
        return service?.getLiveProcesses()
    }

    override fun deleteProcess(process: Process) {
        service?.finishProcess(process)
    }
}