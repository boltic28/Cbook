package com.boltic28.recyclertask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemService {

    private var data = mutableListOf(
        "User 1","User 2","User 3","User 4","User 5","User 6","User 7","User 8"
    )
    private var counter = 9

    private lateinit var adapter : ItemAdapter

    private lateinit var deleter: ItemDeleter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = ItemAdapter(data, object :
            ItemAdapter.OnItemClickListener {
            override fun onClick(item: String) {
                showDialog(item)
            }
        })
        setFAB()

        adapter = recycler.adapter as ItemAdapter

        deleter = object : ItemDeleter(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(adapter.getAtIndex(viewHolder.adapterPosition))
            }
        }

        val itemTouchHelper = ItemTouchHelper(deleter)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun setFAB(){
        button_add.setOnClickListener{
            addItem("User ${counter++}")
        }
    }

    private fun refreshData(list: List<String>){
        val itemDiff = DiffUtilItem(data, list)
        val result = DiffUtil.calculateDiff(itemDiff)

        adapter.setData(list)
        setNewData(list)
        result.dispatchUpdatesTo(adapter)
    }

    private fun showDialog(item: String) {
        DeleteAlertDialog(item).show(supportFragmentManager, "delete")
    }

    private fun showMessage(item: String){
        Snackbar.make( findViewById(android.R.id.content)
            , "$item deleted", Snackbar.LENGTH_LONG).apply {
            setAction(R.string.snack_alert_undo) {
                undoRemove(item)
            }
            show()
        }
    }

    override fun removeItem(item: String) {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        newList.remove(item)
        refreshData(newList)
        Toast.makeText(this, "$item deleted", Toast.LENGTH_SHORT).show()
        showMessage(item)
    }

    override fun undoRemove(item: String) {
        addItem(item)
    }

    override fun addItem(item: String) {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        newList.add(item)
        refreshData(newList)
    }

    override fun shuffle() {
//        val newList = mutableListOf<String>()
//        newList.addAll(data)
//        newList.shuffle()
//        refreshData(newList)
    }

    override fun setNewData(data: List<String>) {
        this.data = data as MutableList<String>
    }
}
