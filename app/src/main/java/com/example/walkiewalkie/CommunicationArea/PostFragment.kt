package com.example.walkiewalkie.CommunicationArea

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.walkiewalkie.DataBase.DBHelperPost
import com.example.walkiewalkie.R
import com.example.walkiewalkie.databinding.FragmentPostBinding


class PostFragment : Fragment(R.layout.fragment_post) {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : DBHelperPost

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postButton.setOnClickListener {

                val postData = binding.userUploadPost.text.toString()
                val newestPostID = db.getNewestPostID()+1
                val postID = newestPostID.toString()
                val userName = "User"
                val savedata = db.saveuserdata(postID,userName,postData)

       if (TextUtils.isEmpty(postData)){
           Toast.makeText(requireContext(),"Please insert Post",Toast.LENGTH_SHORT).show()
       }else{
           if (savedata == true){
               Toast.makeText(requireContext(),"Save Contact",Toast.LENGTH_SHORT).show()
               view.findNavController().navigate(R.id.communicationAreaFragment)
           }
           else{
               Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
           }
       }
        }

        db = DBHelperPost(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}