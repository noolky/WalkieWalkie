package com.example.walkiewalkie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class BodyConditionFragment : Fragment() {

    private lateinit var buttonCal: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_body_condition, container, false )
        buttonCal=view.findViewById<ImageButton>(R.id.BMIbutton)

        buttonCal.setOnClickListener{
            view:View -> view.findNavController().navigate(R.id.action_bodyConditionFragment_to_BMICalculatorFragment)
        }
        return view
    }




}