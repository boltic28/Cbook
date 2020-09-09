package com.boltic28.cbook.presentation.views

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.Process
import com.boltic28.cbook.presentation.models.ContactFragmentModel
import com.boltic28.cbook.presentation.interfaces.Worker
import com.boltic28.cbook.util.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_contact.*
import javax.inject.Inject

class ContactFragment @Inject constructor() : Fragment(R.layout.fragment_contact) {

    companion object {
        const val TAG = "cBookt"
        const val FRAG_TAG = "contact_Fragment"

        fun getInstance(): ContactFragment {
            return ContactFragment()
        }
    }

    private lateinit var contact: Contact
    private lateinit var model: ContactFragmentModel
    private var countingThread: Thread? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "CONTACT: CREATED")
        super.onViewCreated(view, savedInstanceState)
        setButtons()

        model = ViewModelProviders.of(this).get(ContactFragmentModel::class.java)
        contact = model.getContact()

        bindData(contact)
    }

    override fun onResume() {
        checkForProcess()
        super.onResume()
    }

    override fun onStop() {
        countingThread?.interrupt()
        super.onStop()
    }

    private fun bindData(contact: Contact) {
        contact_name.text = contact.name
        contact_number.text = contact.number
        contact_mail.text = contact.mail
        contact_remark.text = contact.remark
        Picasso.get()
            .load("https://randomuser.me/api/portraits/men/${contact.id}.jpg")
            .placeholder(R.drawable.nophoto)
            .error(R.drawable.nophoto)
            .transform(CircleTransform())
            .into(contact_image1)
    }

    private fun checkForProcess() {
        Log.d(TAG, "CONTACT: CheckProcess")
        val process = (activity as? Worker)?.getProcessFor(contact)
        if (process != null) {
            contact_button_work.isEnabled = false
            contact_progress.visibility = View.VISIBLE

            lookingForProcess()
        }
    }

    private fun lookingForProcess() {
        (activity as? Worker)?.mGetProcesses()?.observe(this,
            Observer { processes ->
                val process = processes.firstOrNull { it.id == contact.id }
                if (process != null) {
                    setProcessingData(process)
                } else {
                    turnOnButtonStartWork()
                }
            })
    }

    private fun setButtons() {
        contact_button_work.setOnClickListener {
            contact_button_work.isEnabled = false
            contact_progress.visibility = View.VISIBLE

            startWorking()
        }
    }

    private fun startWorking() {
        (activity as? Worker)?.startWork(contact)
        Handler(context?.mainLooper!!).postDelayed({
            checkForProcess()
        }, 100)
    }

    private fun setProcessingData(process: Process) {
        Log.d(TAG, "CONTACT: gotten new data: ${process.name} ${process.left()}")
        contact_button_work.text =
            resources.getString(R.string.contact_button_timer, process.left().toString())
        contact_progress.max = process.timer
        contact_progress.progress = process.now
    }

    private fun turnOnButtonStartWork() {
        Log.d(TAG, "CONTACT: process is finished")
        contact_button_work.isEnabled = true
        contact_button_work.text = resources.getString(R.string.make_work)
        contact_progress.visibility = View.INVISIBLE
    }

}