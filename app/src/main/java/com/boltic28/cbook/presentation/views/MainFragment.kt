package com.boltic28.cbook.presentation.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.presentation.adapters.ContactItemAdapter
import com.boltic28.cbook.presentation.models.MainFragmentModel
import com.boltic28.cbook.presentation.interfaces.Worker
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment(R.layout.fragment_main) {

    companion object {
        const val TAG = "cBookt"
        const val FRAG_TAG = "main_Fragment"

        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    lateinit var model: MainFragmentModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG,"LIST: CREATE")
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this).get(MainFragmentModel::class.java)
        setAdapter(model.getTestAll())
    }

    private fun setAdapter(list: List<Contact>) {
        Log.d(MainActivity.TAG,"LIST: refreshing the adapter")

        recycler_main.layoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)

        recycler_main.adapter =
            ContactItemAdapter(
                model.getSelectedId(),
                list,
                object :
                    ContactItemAdapter.OnItemClickListener {
                    override fun onClick(contact: Contact) {
                        model.setContact(contact)
                        (activity as? Worker)?.openContactFragment()
                    }
                })
    }
}