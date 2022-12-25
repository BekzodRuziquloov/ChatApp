package space.beka.newchatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import space.beka.newchatapp.adapter.MyMessageAdapter
import space.beka.newchatapp.databinding.FragmentMessageBinding
import space.beka.newchatapp.models.MyMessage
import space.beka.newchatapp.models.User

class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private lateinit var user: User
    private lateinit var referenceMessage: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var myMessageAdapter: MyMessageAdapter
    private lateinit var auth: FirebaseAuth
    val a = 5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        user = arguments?.getSerializable("keyUser") as User
        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceMessage = firebaseDatabase.getReference("messages")
        auth = FirebaseAuth.getInstance()
        referenceMessage.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MyMessage>()
                for (child in snapshot.children) {
                    val value = child.getValue(MyMessage::class.java)
                    if (value != null) {
                        if ((value.fromUid == auth.uid && value.toUid == user.uid) || (value.fromUid == user.uid && value.toUid == auth.uid)) list.add(
                            value
                        )
                    }
                }
                myMessageAdapter = MyMessageAdapter(auth.uid!!, list)
                binding.rvChat.adapter = myMessageAdapter
                binding.rvChat.scrollToPosition(list.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
        binding.btnSend.setOnClickListener {
            if (binding.edtMessage.text.isNotBlank()) {
                val myMessage = MyMessage(
                    binding.edtMessage.text.toString().trim(),
                    fromUid = auth.uid!!,
                    toUid = user.uid
                )
                val key = referenceMessage.push().key
                referenceMessage.child(key!!).setValue(myMessage)
                Toast.makeText(context, "Send message", Toast.LENGTH_SHORT).show()
                binding.edtMessage.text.clear()
            }else{
                Toast.makeText(context, "Biror narsa yozing", Toast.LENGTH_SHORT).show()
            }
            }

        return binding.root
    }
}