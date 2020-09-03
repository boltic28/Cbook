package com.boltic28.cbook.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val FRAG_TAG = "main_Fragment"

        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragmentManager()
        mainActivity.model.getContacts().observe(this,
            Observer<List<Contact>> { list -> setRecycler(list) })
    }

    private fun setRecycler(list: List<Contact>) {
        recycler_main.layoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)

        recycler_main.adapter =
            ContactItemAdapter(
                mainActivity.model.getSelectedPosition(),
                list,
                object :
                    ContactItemAdapter.OnItemClickListener {
                    override fun onClick(contact: Contact) {
                        mainActivity.model.setContact(contact)
                        mainActivity.openContactFragment()
                    }
                })
    }

    private fun initFragmentManager() {
        val mainActivity: Activity? = activity
        if (mainActivity is MainActivity) {
            this.mainActivity = mainActivity
        }
    }
}