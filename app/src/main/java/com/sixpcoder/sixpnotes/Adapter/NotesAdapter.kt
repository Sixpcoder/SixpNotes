package com.sixpcoder.sixpnotes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sixpcoder.sixpnotes.Models.Note
import com.sixpcoder.sixpnotes.R

class NotesAdapter(private val context: Context,val listener:NotesClickListener):
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private lateinit var  fulllist:ArrayList<Note>
    private lateinit var notelist:ArrayList<Note>
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val note_layout = itemView.findViewById<CardView>(R.id.card)
        val title= itemView.findViewById<TextView>(R.id.title)
        val note= itemView.findViewById<TextView>(R.id.note)
        val date= itemView.findViewById<TextView>(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.each_item,parent,false))
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentnote = notelist[position]
        holder.title.text = currentnote.title
        holder.title.isSelected = true

        holder.note.text = currentnote.note
        holder.date.text = currentnote.date
        holder.date.isSelected = true

        holder.note_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomcolour(),
                null
            )
        )

        holder.note_layout.setOnClickListener {
            listener.OnItemClicked(notelist[holder.adapterPosition])

        }

        holder.note_layout.setOnLongClickListener {
            listener.OnLongItemClicked(notelist[holder.adapterPosition],holder.note_layout)
            true
        }

    }

    fun randomcolour():Int{

        val list = ArrayList<Int>()
        list.add(R.color.note1)
        list.add(R.color.note2)
        list.add(R.color.note3)
        list.add(R.color.note4)
        list.add(R.color.note5)
        list.add(R.color.note6)
        list.add(R.color.note7)
        list.add(R.color.note8)
        return list.random()




    }

    interface NotesClickListener{
        fun OnItemClicked(note: Note)
        fun OnLongItemClicked(note: Note,cardView: CardView)
    }

    fun updateList(newList:List<Note>){
        fulllist.clear()
        fulllist.addAll(newList)

        notelist.clear()
        notelist.addAll(newList)
        notifyDataSetChanged()

    }

    fun searchlist(search:String){
        notelist.clear()

        for (item in fulllist){
            if (item.title?.lowercase()?.contains(search.lowercase())==true or
                    item.note?.lowercase()!!.contains(search.lowercase())
            ){
                notelist.add(item)

            }
        }
        notifyDataSetChanged()
    }
}













