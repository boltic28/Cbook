package com.boltic28.cbook.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment: Fragment(R.layout.fragment_contact) {

    companion object {
        const val FRAG_TAG = "contact_Fragment"

        fun getInstance(): ContactFragment {
            return ContactFragment()
        }
    }

    private lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentManager()
        mainActivity.model.getContact().observe(this,
            Observer<Contact> { contact -> bindData(contact) })
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

    private fun initFragmentManager() {
        val mainActivity: Activity? = activity
        if (mainActivity is MainActivity) {
            this.mainActivity = mainActivity
        }
    }
}