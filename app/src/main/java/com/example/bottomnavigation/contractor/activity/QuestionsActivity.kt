package com.example.bottomnavigation.contractor.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bottomnavigation.R
import com.example.bottomnavigation.chat.ChatActivity
import com.example.bottomnavigation.databinding.ActivityQuestionsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class QuestionsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityQuestionsBinding
    private val contractorId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener { creatingChatBase() }
    }


    private fun creatingChatBase() {
        val clientId = intent.getStringExtra("id")
        val chatRoomId = contractorId + clientId
        val reverseChatRoomId = clientId + contractorId
        val ques1 = binding.ques1.text.toString()
        val ques2 = binding.ques2.text.toString()
        val ques3 = binding.ques3.text.toString()
        val ques4 = binding.ques4.text.toString()
        val ques5 = binding.ques5.text.toString()
        val ans1 = binding.tvAnswer1.text.toString()
        val ans2 = binding.tvAnswer2.text.toString()
        val ans3 = binding.tvAnswer3.text.toString()
        val ans4 = binding.tvAnswer4.text.toString()
        val ans5 = binding.tvAnswer5.text.toString()
        val greetMessage = "Hey there! hope you are doing well\n $ques1 - $ans1,\n $ques2 - $ans2,\n $ques3 - $ans3,\n $ques4 - $ans4,\n $ques5 - $ans5"
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())
        val map = hashMapOf<String,String>()
        map["message"] = greetMessage
        map["senderId"] = contractorId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime
        FirebaseDatabase.getInstance().getReference("Chatbase").child(chatRoomId).push().setValue(map)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("id", clientId)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this,"Message Sent", Toast.LENGTH_SHORT).show()
                }
            }
//        FirebaseDatabase.getInstance().getReference("Chatbase")
//            .addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot.hasChild(chatRoomId)){
//                        getMessages(chatRoomId)
//                    }
//                    else if(snapshot.hasChild(reverseChatRoomId)){
//                        getMessages(reverseChatRoomId)
//                    }
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            })
    }
    private fun getMessages(chatRoomId: String) {
        FirebaseDatabase.getInstance().getReference("Chatbase").child(chatRoomId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}