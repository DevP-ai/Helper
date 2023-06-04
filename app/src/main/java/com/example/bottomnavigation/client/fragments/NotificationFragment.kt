package com.example.bottomnavigation.client.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigation.chat.NotificationFragmentAdapter
import com.example.bottomnavigation.databinding.FragmentNotificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationFragment : Fragment() {

    private lateinit var binding : FragmentNotificationBinding
    private lateinit var notificationFragmentAdapter: NotificationFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        binding.notificationToolbar.apply {
            title = "Notification"
            (activity as AppCompatActivity).setSupportActionBar(this)
        }
        prepareChatFragmentAdapterForShowingContractors()

        showingContractors()


        return binding.root
    }

    private fun showingContractors() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseDatabase.getInstance().getReference("Chatbase")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val clientsIdList = ArrayList<String>()
                    val chatRoomList = ArrayList<String>()

                    for(chatIds in snapshot.children){
                        if(chatIds.key!!.contains(currentUserId!!)){
                            chatRoomList.add(chatIds.key!!)
                            clientsIdList.add(chatIds.key!!.replace(currentUserId,""))
                        }
                    }

                    notificationFragmentAdapter.setClientIdList(clientsIdList)
                    notificationFragmentAdapter.setChatRoom(chatRoomList)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun prepareChatFragmentAdapterForShowingContractors() {
        notificationFragmentAdapter = NotificationFragmentAdapter(requireContext())
        binding.contractorsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = notificationFragmentAdapter
        }
    }


}