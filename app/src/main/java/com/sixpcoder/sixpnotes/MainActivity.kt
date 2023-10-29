@file:Suppress("DEPRECATION")

package com.sixpcoder.sixpnotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sixpcoder.sixpnotes.Adapter.NotesAdapter
import com.sixpcoder.sixpnotes.Database.NoteDatabase
import com.sixpcoder.sixpnotes.Models.Note
import com.sixpcoder.sixpnotes.Models.NoteViewModel
import com.sixpcoder.sixpnotes.databinding.ActivityMainBinding
import com.sixpcoder.sixpnotes.utilities.NOTE

class MainActivity : AppCompatActivity() ,
    NotesAdapter.NotesClickListener ,
    PopupMenu.OnMenuItemClickListener{

    private  lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel:NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selnote:Note

    private val updatenote=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {result->
        if(result.resultCode==Activity.RESULT_OK){
             val note =result.data?.getSerializableExtra("note") as Note
            if (note!=null){
                viewModel.update(note)
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initializing the ui
        initui()

        viewModel=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(application))
            .get(NoteViewModel::class.java)

        viewModel.allnotesdata.observe(this){list->
            list?.let {

                adapter.updateList(list)

            }
        }

        database=NoteDatabase.getdatabase(this)

    }

    private fun initui() {
        binding.recview.setHasFixedSize(true)
        binding.recview.layoutManager=
            StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter= NotesAdapter(this,this)
        binding.recview.adapter=adapter

        val getContent=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {result->
            if(result.resultCode== Activity.RESULT_OK){
                val note = result.data?.getSerializableExtra("note") as Note
                if (note!=null)
                {
                    viewModel.insert(note)
                }
            }

        }
        binding.fab.setOnClickListener {
            val intents = Intent(this,AddNote::class.java)
            getContent.launch(intents)
        }

        binding.searchView.setOnQueryTextListener(
            object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!=null){
                    adapter.searchlist(newText)

                }
                return true
            }

        })

    }

    override fun OnItemClicked(note: Note) {
        val intent=Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra(NOTE,note)
        updatenote.launch(intent)

    }

    override fun OnLongItemClicked(note: Note, cardView: CardView) {
        selnote=note
        popupdisplay(cardView)

    }

    private fun popupdisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId==R.id.delete){
            viewModel.delete(selnote)
            return true

        }
        return false
    }


}