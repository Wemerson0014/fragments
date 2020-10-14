package com.estudo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estudo.fragments.model.Character
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.lang.ClassCastException

class CharacterListFragment: Fragment() {

    private lateinit var names: Array<String>
    private lateinit var descriptions: Array<String>
    private lateinit var imageResIds: IntArray
    private lateinit var listener : OnListSelected

    companion object {
        fun newInstance() = CharacterListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val activity = activity as Context
        val recyclerView = view.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = CharacterListAdapter()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val resources = context.resources
        names = resources.getStringArray(R.array.names)
        descriptions = resources.getStringArray(R.array.descriptions)

        val typedArray = resources.obtainTypedArray(R.array.images)
        val imageCount = names.size
        imageResIds = IntArray(imageCount)
        for (i in 0 until imageCount) {
            imageResIds[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()
        if (context is OnListSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + getString(R.string.warning))
        }
    }

    internal inner class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
            )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val character = Character(
                names[position],
                descriptions[position],
                imageResIds[position]
            )
            holder.bind(character)
            holder.itemView.setOnClickListener {
                listener.onSelected(character)
            }
        }

        override fun getItemCount(): Int = names.size

        internal inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(character: Character) {
                itemView.list_image.setImageResource(character.imageResId)
                itemView.list_name.text = character.name
            }
        }
    }

    interface OnListSelected {
        fun onSelected(character: Character)
    }
}