package com.boltic28.cbook.presentation.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.presentation.adapters.ContactDeleter
import com.boltic28.cbook.presentation.adapters.ContactDiffUtil
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
    lateinit var adapter: ContactItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG,"LIST: CREATE")
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this).get(MainFragmentModel::class.java)
    }

    override fun onResume() {
        super.onResume()
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

        adapter = recycler_main.adapter as ContactItemAdapter

        val swipeHandler = object : ContactDeleter(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model.deleteContact(adapter.removeAt(viewHolder.adapterPosition))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_main)
    }

    //method for refreshing list with DiffUtil
    private fun refreshContacts(newList: List<Contact>){
        val contactDiff = ContactDiffUtil(adapter.getData() , newList)
        val diffResult = DiffUtil.calculateDiff(contactDiff)

        adapter.setData(newList)
        diffResult.dispatchUpdatesTo(adapter)
    }
}