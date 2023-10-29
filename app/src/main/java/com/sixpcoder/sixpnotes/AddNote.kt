@file:Suppress("DEPRECATION")

package com.sixpcoder.sixpnotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sixpcoder.sixpnotes.databinding.ActivityAddNoteBinding
import com.sixpcoder.sixpnotes.utilities.NOTE
import com.sixpcoder.sixpnotes.utilities.NOTES
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: com.sixpcoder.sixpnotes.Models.Note
    private lateinit var old_note:com.sixpcoder.sixpnotes.Models.Note

    var isupdate=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        try {
            old_note = intent.getSerializableExtra(NOTE)
                    as com.sixpcoder.sixpnotes.Models.Note
            binding.ettitle.setText(old_note.title)
            binding.etnote.setText(old_note.note)
            isupdate=true

        }catch (e:Exception){
            e.printStackTrace()

        }


        binding.checkarrow.setOnClickListener {
            val name = binding.ettitle.text.toString()
            val body  = binding.etnote.text.toString()

            if (name.isNotEmpty() and body.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isupdate){
                    note=com.sixpcoder.sixpnotes.Models.Note(
                        old_note.id,name,body,formatter.format(Date())
                    )
                }else {
                    note = com.sixpcoder.sixpnotes.Models.Note(
                        null, name, body, formatter.format(Date())
                    )
                }
                val intent= Intent()
                intent.putExtra(NOTES,note)
                setResult(Activity.RESULT_OK)
                finish()
            }
            else{
                Toast.makeText(this,
                    "Please fill the details",
                    Toast.LENGTH_SHORT)
                    .show()
            }


        }

        binding.backarrow.setOnClickListener {
            onBackPressed()
        }


    }
}