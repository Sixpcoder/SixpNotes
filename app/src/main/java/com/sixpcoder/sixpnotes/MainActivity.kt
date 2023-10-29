package com.sixpcoder.sixpnotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sixpcoder.sixpnotes.Adapter.NotesAdapter
import com.sixpcoder.sixpnotes.Database.NoteDatabase
import com.sixpcoder.sixpnotes.Models.Note
import com.sixpcoder.sixpnotes.Models.NoteViewModel
import com.sixpcoder.sixpnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() ,NotesAdapter.NotesClickListener {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel:NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var note:Note

    private val updatenote=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
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
        binding.recview.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
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
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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


}