package space.beka.newchatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import space.beka.newchatapp.adapter.RvAdapter
import space.beka.newchatapp.adapter.RvClick
import space.beka.newchatapp.databinding.FragmentHomeBinding
import space.beka.newchatapp.models.User

class HomeFragment : Fragment(), RvClick {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var rvAdapter: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
        userReference = database.getReference("users")
        rvAdapter = RvAdapter(rvClick = this)
        binding.rvUsers.adapter = rvAdapter
        loadData()
        return binding.root
    }

    private fun loadData() {
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value != null) {
                        rvAdapter.list.add(value)
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun itemClick(user: User) {
        findNavController().navigate(space.beka.newchatapp.R.id.messageFragment, bundleOf("keyUser" to user))
    }
}