package com.boltic28.recyclertask.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.recyclertask.recycler.DiffUtilItem
import com.boltic28.recyclertask.recycler.ItemDeleter
import com.boltic28.recyclertask.R
import com.boltic28.recyclertask.classes.BaseInstance
import com.boltic28.recyclertask.classes.PictureInstance
import com.boltic28.recyclertask.classes.TextInstance
import com.boltic28.recyclertask.recycler.BaseAdapter
import com.boltic28.recyclertask.service.ItemService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ItemService {

    private var data1 = mutableListOf(
        TextInstance(1L, "User 1"),
        TextInstance(2L, "User 2"),
        TextInstance(3L, "User 3"),
        TextInstance(4L, "User 4"),
        PictureInstance(5L, "res1"),
        TextInstance(6L, "User 5"),
        TextInstance(7L, "User 6"),
        TextInstance(8L, "User 7"),
        PictureInstance(9L, "res2"),
        PictureInstance(10L, "res3"),
        TextInstance(11L, "User 8"),
        TextInstance(12L, "User 9"),
        PictureInstance(13L, "res4"),
        TextInstance(14L, "User 10"),
        PictureInstance(15L, "res5"),
        TextInstance(16L, "User 11")
    )

    private var counter = 17
    private lateinit var adapter: BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = BaseAdapter(data1, object :
        BaseAdapter.OnItemClickListener {
            override fun onClick(item: BaseInstance) {
                removeItem(item)
            }
        })

        setFAB()

        adapter = recycler.adapter as BaseAdapter

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
            addItem(TextInstance(17,"User ${counter++}"))
        }
    }

    private fun refreshData(list: List<BaseInstance>) {
        val itemDiff =
            DiffUtilItem(data1, list)
        val result = DiffUtil.calculateDiff(itemDiff)

        adapter.setData(list)
        setNewData(list)
        result.dispatchUpdatesTo(adapter)
    }

    private fun showDialog(item: BaseInstance, flagUndo: Boolean) {
        DeleteAlertDialog(item, flagUndo)
            .show(supportFragmentManager, "undo")
    }

    private fun showMessage(item: BaseInstance, ind: Int) {
        Snackbar.make(
            findViewById(android.R.id.content)
            , "${item.id} deleted", Snackbar.LENGTH_LONG
        ).apply {
            setAction(R.string.snack_alert_undo) {
                undoRemove(item, ind)
            }
            show()
        }
    }

    override fun removeItem(item: BaseInstance) {
        val newList = mutableListOf<BaseInstance>()
        newList.addAll(data1)
        newList.remove(item)
        refreshData(newList)
        Toast.makeText(this, "Item with ID: ${item.id} deleted", Toast.LENGTH_SHORT).show()
    }

    override fun removeItemUndo(item: BaseInstance) {
        val newList = mutableListOf<BaseInstance>()
        newList.addAll(data1)
        val ind = newList.indexOf(item)
        newList.remove(item)
        refreshData(newList)
        showMessage(item, ind)
    }

    override fun addItem(item: BaseInstance) {
        val newList = mutableListOf<BaseInstance>()
        newList.addAll(data1)
        newList.add(item)
        refreshData(newList)
    }

    override fun undoRemove(item: BaseInstance, index: Int) {
        addItem(item, index)
    }

    override fun addItem(item: BaseInstance, index: Int) {
        val newList = mutableListOf<BaseInstance>()
        newList.addAll(data1)
        newList.add(index, item)
        refreshData(newList)
    }

    override fun cancel() {
        adapter.reload()
    }

    override fun setNewData(data: List<BaseInstance>) {
        this.data1 = data as MutableList<BaseInstance>
    }

    override fun shuffleItems() {
        val newList = mutableListOf<BaseInstance>()
        newList.addAll(data1)
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
