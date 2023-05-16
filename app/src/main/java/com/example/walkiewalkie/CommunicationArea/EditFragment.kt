package com.example.walkiewalkie.CommunicationArea

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R
import com.example.walkiewalkie.databinding.FragmentEditBinding


class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : Helper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        // binding used
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Helper(requireContext())
        val postID = arguments?.getString("postID")
        val postData = arguments?.getString("postData")
        binding.editPost.setText(postData.toString())
        binding.Editedbutton.setOnClickListener {
            if (binding.editPost.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(),"Cannot be empty", Toast.LENGTH_SHORT).show()

            }
            else if(binding.editPost.text.toString() == postData.toString()){
                Toast.makeText(requireContext(),"Cannot same with old post", Toast.LENGTH_SHORT).show()

            }
            else{
                db.updatePostData(postID!!, binding.editPost.text.toString())
                Toast.makeText(requireContext(),"Change Successfully", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.communicationAreaFragment)
            }
        }
        binding.Backedbutton.setOnClickListener{
            view.findNavController().navigate(R.id.communicationAreaFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}