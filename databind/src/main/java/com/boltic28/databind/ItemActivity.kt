package com.boltic28.databind

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.boltic28.databind.databinding.ActivityItemBinding
import com.boltic28.databind.dto.interf.BaseItem
import com.boltic28.databind.dto.interf.PetOwner

class ItemActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityItemBinding>(this, R.layout.activity_item)

        val user = intent.extras?.getSerializable("item") as BaseItem

        binding.person = user
        binding.pet = (user as? PetOwner)?.pet?.get()
    }
}