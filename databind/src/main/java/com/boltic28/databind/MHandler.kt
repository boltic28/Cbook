package com.boltic28.databind

import android.content.Intent
import android.view.View
import com.boltic28.databind.databinding.ActivityMainBinding
import com.boltic28.databind.dto.interf.BaseItem
import com.boltic28.databind.dto.interf.PetOwner
import kotlin.random.Random

class MHandler(
    val persons: List<BaseItem>,
    val binding: ActivityMainBinding
) {

    fun onOther() {
        val user: PetOwner = persons[Random.nextInt(persons.size)] as PetOwner
        binding.person = user
        binding.pet = user.pet.get()
    }

    fun onRowClick(view: View, person: BaseItem) {
        val context = view.context
        val intent = Intent(context, ItemActivity::class.java)
        intent.putExtra ("item", person)
        view.context.startActivity(intent)
    }

}