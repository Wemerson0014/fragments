package com.estudo.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estudo.fragments.model.Character

class MainActivity : AppCompatActivity(), CharacterListFragment.OnListSelected {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_root, CharacterListFragment.newInstance(), "characterList")
                .commit()
        }
    }

    override fun onSelected(character: Character) {
        println(character)
    }
}