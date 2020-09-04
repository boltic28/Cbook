package com.boltic28.cbook.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.service.ContactService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_contact.*
import javax.inject.Inject

class ContactFragment @Inject constructor(): Fragment(R.layout.fragment_contact) {

    companion object {
        const val TAG = "cBookt"
        const val FRAG_TAG = "contact_Fragment"

        fun getInstance(): ContactFragment {
            return ContactFragment()
        }
    }

    lateinit var model: ContactFragmentModel
    lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Contact fragment: CREATED")
        super.onViewCreated(view, savedInstanceState)
        getMainActivity()
        setButtons()

        model = ViewModelProviders.of(this).get(ContactFragmentModel::class.java)
        bindData(model.getContact())
    }

    private fun setButtons(){
        contact_button_work.setOnClickListener{
            contact_button_work.isClickable = false
            contact_button_work.isEnabled = false
            contact_progress.visibility = View.VISIBLE

            val intent = Intent(context, ContactService::class.java)
            intent.putExtra("contact", model.getContact())
            mainActivity.startService(intent)
            startWorking()
            //service start working with contact
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startWorking(){
        val timer = (5..15).shuffled().last()
        contact_progress.max = timer

        Thread(Runnable {
            var now = 0
            while (now < timer){
                try {
                    Thread.sleep(990)
                    now++
                }catch (e: Throwable){
                    Log.d(ContactService.TAG, "ERROR counting")
                }
                contact_progress.post(Runnable {
                    contact_button_work.text = " 00 : ${timer - now}"
                    contact_progress.progress = now
                })
            }
            contact_button_work.post(Runnable {
                contact_button_work.isClickable = true
                contact_button_work.isEnabled = true
                contact_button_work.text = resources.getString(R.string.make_work)
                contact_progress.visibility = View.INVISIBLE
            })
        }).start()
    }

    private fun bindData(contact: Contact){
        contact_name.text = contact.name
        contact_number.text = contact.number
        contact_mail.text = contact.mail
        contact_remark.text = contact.remark
        loadPhoto(contact)
    }

    private fun loadPhoto(contact: Contact) {

        if (contact.photo.isEmpty()) {
            Picasso.get().load(R.drawable.nophoto).into(contact_image)
        } else {
            //load file from storage
           //Picasso.get().load(File("path", "photo.jpg")).into(contact_image)
        }
    }

    private fun getMainActivity() {
        val mainActivity: Activity? = activity
        if (mainActivity is MainActivity) {
            this.mainActivity = mainActivity
        }
    }
}