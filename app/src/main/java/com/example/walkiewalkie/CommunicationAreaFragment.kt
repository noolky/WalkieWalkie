package com.example.walkiewalkie

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walkiewalkie.databinding.FragmentCommunicationAreaBinding


class CommunicationAreaFragment : Fragment(R.layout.fragment_communication_area) {

    //binding code
    private var _binding: FragmentCommunicationAreaBinding? = null
    private val binding get() = _binding!!

    //
    private lateinit var recyclerView: RecyclerView
    lateinit var dbh: DBHelperPost
    private lateinit var newArry: ArrayList<DatalistPost>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        // binding used
        _binding = FragmentCommunicationAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.EnterPostButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.postFragment)
        }

        dbh = DBHelperPost(requireContext())
        recyclerView = binding.recyclerViewCommunicationArea
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        displaypost()
    }

    private fun displaypost() {
        var newcursor: Cursor? = dbh?.gettext()
        newArry = ArrayList<DatalistPost>()
        while (newcursor!!.moveToNext()) {
            val upostID = newcursor.getString(0)
            val uname = newcursor.getString(1)
            val upost = newcursor.getString(2)
            newArry.add(DatalistPost(upostID, uname, upost))
        }
        recyclerView.adapter = MyAdapterPost(newArry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}