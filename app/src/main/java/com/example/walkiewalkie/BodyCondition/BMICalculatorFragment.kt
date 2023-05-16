package com.example.walkiewalkie.BodyCondition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.walkiewalkie.DataBase.DataBMI
import com.example.walkiewalkie.R

class BMICalculatorFragment : Fragment() {
    //this can the DataBMI name when combine aden data
   private lateinit var dbBMI: DataBMI
    private lateinit var weightText: EditText
    private lateinit var heightText: EditText
    private lateinit var calButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,


    ): View? {
        val view = inflater.inflate(R.layout.fragment_b_m_i_calculator, container, false)

        //this can the DataBMI name when combine aden data
        dbBMI= DataBMI(requireContext())
        weightText = view.findViewById(R.id.etWeight)
        heightText = view.findViewById(R.id.etHeight)
        calButton = view.findViewById(R.id.btnCalculate)

        calButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            // validate the input
            if (validateInput(weight, height)) {
                val weightValue=weight.toFloat()
                val heightValue=height.toFloat()
                if(weightValue==0f){

                    Toast.makeText(requireContext(), "Weight cannot be 0", Toast.LENGTH_LONG).show()

                }
                else if(heightValue==0f){
                    Toast.makeText(requireContext(), "Height cannot be 0", Toast.LENGTH_LONG).show()
                }
                else {
                    // calculate the bmi with float (2 decimal)
                    val bmi =
                        weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                    // get result with 2 decimal
                    val bmi2Digit = String.format("%.2f", bmi).toFloat()
                    displayResult(bmi2Digit)


                    dbBMI.saveBMI(weight, height, bmi2Digit)
                }
            }

            closeKeyboard(view)
        }

        return view

    }

    private fun validateInput(weight: String?, height: String?): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Weight is empty", Toast.LENGTH_LONG).show()
                false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Height is empty", Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }


    // display the description for the result and changing the color
    private fun displayResult(bmi: Float) {
        val resultIndex = view?.findViewById<TextView>(R.id.TvIndex)
        val resultDesc = view?.findViewById<TextView>(R.id.Tvresult)
        val info = view?.findViewById<TextView>(R.id.TvInfo)

        resultIndex?.text = bmi.toString()
        info?.text = "(Normal Range is 18.5 - 24.9)"

        var resultText = ""
        var color = 0

        when {
            bmi < 18.5 -> {
                resultText = "UnderWeight"
                color = R.color.UnderWeight
            }

            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.Normal
            }

            bmi in 25.00..29.99 -> {
                resultText = "OverWeight"
                color = R.color.Over_Weight
            }

            bmi > 29.99 -> {
                resultText = "Obese"
                color = R.color.Obese
            }
        }

        resultDesc?.setTextColor(ContextCompat.getColor(requireContext(), color))
        resultDesc?.text = resultText
    }


    private fun closeKeyboard(view: View) {
        val close =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        close.hideSoftInputFromWindow(view.windowToken, 0)
    }

}