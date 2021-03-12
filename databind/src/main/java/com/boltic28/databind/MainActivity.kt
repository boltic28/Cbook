package com.boltic28.databind

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.databind.adapter.ItemAdapter
import com.boltic28.databind.databinding.ActivityMainBinding
import com.boltic28.databind.dto.*
import com.boltic28.databind.dto.interf.BaseItem
import com.boltic28.databind.dto.interf.Pet
import com.boltic28.databind.dto.interf.PetOwner
import com.boltic28.databind.dto.interf.Profession
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val pets: List<Pet> = listOf(
            Dog(
                id = 9,
                photoUrl = ObservableField(getRandomPhotoId(true)),
                name = ObservableField("charlie"),
                age = ObservableInt(3),
                isIll = ObservableBoolean(false),
                weight = ObservableDouble(5.22),
                isCov = ObservableBoolean(false)
            ),
            Dog(
                id = 11,
                photoUrl = ObservableField(getRandomPhotoId(true)),
                name = ObservableField("kysie"),
                age = ObservableInt(7),
                isIll = ObservableBoolean(true),
                weight = ObservableDouble(3.22),
                isCov = ObservableBoolean(true)
            )
        )

        val list: List<BaseItem> = listOf(
            Man(
                id = 1,
                photoUrl = ObservableField(getRandomPhotoId(true)),
                name = ObservableField("jack"),
                age = ObservableInt(33),
                isIll = ObservableBoolean(false),
                profession = Profession.DOCTOR,
                tall = ObservableFloat(1.76f),
                pet = ObservableField(pets[0]),
                isCov = ObservableBoolean(true)
            ),
            Man(
                id = 2,
                photoUrl = ObservableField(getRandomPhotoId(true)),
                name = ObservableField("mike"),
                age = ObservableInt(45),
                isIll = ObservableBoolean(false),
                profession = Profession.TEACHER,
                tall = ObservableFloat(1.56f),
                pet = ObservableField(pets[1]),
                isCov = ObservableBoolean(false)
            ),
            Man(
                id = 4,
                photoUrl = ObservableField(getRandomPhotoId(true)),
                name = ObservableField("petr"),
                age = ObservableInt(21),
                isIll = ObservableBoolean(true),
                profession = Profession.BUILDER,
                tall = ObservableFloat(1.96f),
                pet = ObservableField(pets[0]),
                isCov = ObservableBoolean(true)
            ),
            Woman(
                id = 3,
                photoUrl = ObservableField(getRandomPhotoId(false)),
                name = ObservableField("julia"),
                age = ObservableInt(53),
                isIll = ObservableBoolean(false),
                profession = Profession.DOCTOR,
                isMother = ObservableBoolean(true),
                pet = ObservableField(pets[1]),
                isCov = ObservableBoolean(false)
            ),
            Woman(
                id = 6,
                photoUrl = ObservableField(getRandomPhotoId(false)),
                name = ObservableField("monika"),
                age = ObservableInt(77),
                isIll = ObservableBoolean(false),
                profession = Profession.VET,
                isMother = ObservableBoolean(false),
                pet = ObservableField(pets[0]),
                isCov = ObservableBoolean(true)
            ),
            Woman(
                id = 7,
                photoUrl = ObservableField(getRandomPhotoId(false)),
                name = ObservableField("agnejka"),
                age = ObservableInt(14),
                isIll = ObservableBoolean(false),
                profession = Profession.BUILDER,
                isMother = ObservableBoolean(true),
                pet = ObservableField(pets[1]),
                isCov = ObservableBoolean(false)
            )
        )

        binding.handler = MHandler(list, binding)


        val user: PetOwner = list[4] as PetOwner

        binding.person = user
        binding.pet = user.pet.get()

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        val adapter = ItemAdapter()

        recycler.adapter = adapter
        adapter.setData(list)
    }

    private fun getRandomPhotoId(isMan: Boolean): String{
        val numb = Random.nextInt(1, 50)
        val isMen = if (isMan) "men" else "women"
        return "https://randomuser.me/api/portraits/med/$isMen/$numb.jpg"
    }
}