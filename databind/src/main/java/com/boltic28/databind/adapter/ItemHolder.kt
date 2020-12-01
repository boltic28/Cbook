package com.boltic28.databind.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.boltic28.databind.workmanagers.HardWorker
import com.boltic28.databind.ItemActivity
import com.boltic28.databind.databinding.ItemPersonBinding
import com.boltic28.databind.dto.interf.BaseItem
import com.boltic28.databind.dto.interf.PetOwner
import java.util.concurrent.TimeUnit

class ItemHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BaseItem) {
        binding.person = item
        binding.pet = (item as? PetOwner)?.pet?.get()
        binding.handler = object : OnRowClicked {
            override fun onClick(item: BaseItem) {
                val context = binding.root.context
                val intent = Intent(context, ItemActivity::class.java)
                intent.putExtra("item", item)
                context.startActivity(intent)
            }
        }
        binding.worker = object : OnWorkClicked {
            override fun onClick(item: BaseItem) {
                //send data into worker
                val data = Data.Builder()
                    .putString("name", item.name.get())
                    .putLong("id", item.id)
                    .build()

                //predicate
                val constraints = Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()

                val myWorkRequest =
                    OneTimeWorkRequest
                        .Builder(HardWorker::class.java)
                        .setConstraints(constraints)
                        .setInputData(data)
                        .addTag("working")
                        .setInitialDelay(15, TimeUnit.SECONDS)
                        .build()
                WorkManager.getInstance().enqueue(myWorkRequest)

            }
        }

        binding.executePendingBindings()
    }
}