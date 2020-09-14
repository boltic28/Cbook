package com.boltic28.recyclertask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        "User 1", "User 2", "User 3", "User 4","res1", "res2",
        "res5", "User 5", "User 6","res4", "User 7", "User 8", "res3"
    )

    private var counter = 9
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = ItemAdapter(data, object :
            ItemAdapter.OnItemClickListener {
            override fun onClick(item: String) {
                showDialog(item, false)
            }
        })
        setFAB()

        adapter = recycler.adapter as ItemAdapter

        val deleter = object : ItemDeleter(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(adapter.getAtIndex(viewHolder.adapterPosition), true)
            }
        }

        val itemTouchHelper = ItemTouchHelper(deleter)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun setFAB() {
        button_add.setOnClickListener {
            addItem("User ${counter++}")
        }
    }

    private fun refreshData(list: List<String>) {
        val itemDiff = DiffUtilItem(data, list)
        val result = DiffUtil.calculateDiff(itemDiff)

        adapter.setData(list)
        setNewData(list)
        result.dispatchUpdatesTo(adapter)
    }

    private fun showDialog(item: String, flagUndo: Boolean) {
        DeleteAlertDialog(item, flagUndo).show(supportFragmentManager, "undo")
    }

    private fun showMessage(item: String, ind: Int) {
        Snackbar.make(
            findViewById(android.R.id.content)
            , "$item deleted", Snackbar.LENGTH_LONG
        ).apply {
            setAction(R.string.snack_alert_undo) {
                undoRemove(item, ind)
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
    }

    override fun removeItemUndo(item: String) {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        val ind = newList.indexOf(item)
        newList.remove(item)
        refreshData(newList)
        showMessage(item, ind)
    }

    override fun addItem(item: String) {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        newList.add(item)
        refreshData(newList)
    }

    override fun undoRemove(item: String, index: Int) {
        addItem(item, index)
    }

    override fun addItem(item: String, index: Int) {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        newList.add(index, item)
        refreshData(newList)
    }

    override fun cancel() {
        adapter.reload()
    }

    override fun setNewData(data: List<String>) {
        this.data = data as MutableList<String>
    }

    override fun shuffleItems() {
        val newList = mutableListOf<String>()
        newList.addAll(data)
        newList.shuffle()
        refreshData(newList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shuffle -> {shuffleItems()}
        }
        return true
    }
}
