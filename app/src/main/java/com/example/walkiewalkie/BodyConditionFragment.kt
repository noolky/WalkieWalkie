package com.example.walkiewalkie


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController



class BodyConditionFragment : Fragment() {

    private lateinit var buttonCal: ImageButton
    private lateinit var BMIText:TextView
    //this can the DataBMI name when combine aden data
    private lateinit var dataBaseBMI: DataBMI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_body_condition, container, false )

        dataBaseBMI= DataBMI(requireContext())

        buttonCal=view.findViewById<ImageButton>(R.id.BMIbutton)
        buttonCal.setOnClickListener{
            view:View -> view.findNavController().navigate(R.id.action_bodyConditionFragment_to_BMICalculatorFragment)
        }

        BMIText =view.findViewById(R.id.TvBmiResult)

        val LastestBMI=dataBaseBMI.getLastestBMI()
        val BMIResult2Digit =LastestBMI

        val resultSuggestion1 = view?.findViewById<TextView>(R.id.TvSg1Result)
        val resultSuggestion2 = view?.findViewById<TextView>(R.id.TvSg2Result)
        val resultSuggestion3 = view?.findViewById<TextView>(R.id.TvSg3result)


        var Sg1=""
        var Sg2=""
        var Sg3=""
        var color = 0
        var bodyIndex=""
        var textBDdec=""

            if(LastestBMI.toString().isEmpty()){
            // Handle the case where no latest BMI value is available
            BMIText.text = "No data available"
            // Set default values for suggestions or handle them accordingly
            resultSuggestion1?.text = "Enter your Height and Weight to calculate BMI"
            resultSuggestion2?.text = ""
            resultSuggestion3?.text = ""
            color=R.color.white

        }
        else{

            when {
                BMIResult2Digit in 0.01.. 18.5 -> {
                    Sg1="Increase caloric intake with nutrient-dense foods."
                    Sg2="Incorporate strength training to build muscle mass."
                    Sg3="Seek guidance from a healthcare professional or dietitian for personalized advice."
                    color = R.color.UnderWeight
                    textBDdec=" You BMI Status is "
                    bodyIndex="UnderWeight"
                }

                BMIResult2Digit in 18.50..24.99 -> {
                    Sg1="Maintain a balanced diet with a variety of nutrients."
                    Sg2="Engage in regular physical activity for overall health."
                    Sg3="Prioritize stress management and quality sleep for overall well-being."
                    color = R.color.Normal
                    bodyIndex="Normal"
                }

                BMIResult2Digit in 25.00..29.99 -> {
                    Sg1="Reduce calorie intake by making healthier food choices."
                    Sg2="Increase physical activity levels to burn calories."
                    Sg3="Incorporate more fruits, vegetables, and whole grains into your meals."
                    color = R.color.Over_Weight
                    textBDdec=" You BMI Status is "
                    bodyIndex="Over Weight"
                }

                BMIResult2Digit > 29.99 -> {
                    Sg1="Consult a healthcare professional for comprehensive medical evaluation and guidance."
                    Sg2="Follow a balanced and calorie-controlled diet plan"
                    Sg3="Engage in regular physical activity and consider working with a qualified fitness professional."
                    color = R.color.Obese
                    textBDdec=" You BMI Status is "
                    bodyIndex="Obesity"
                }

                else ->{
                    // Set default values for suggestions or handle them accordingly
                    Sg1 = "Enter your Height and Weight to calculate BMI"
                    color=R.color.black
                    textBDdec=""
                    bodyIndex=" No data available "
                }

            }
            BMIText.setTextColor(ContextCompat.getColor(requireContext(), color))
            resultSuggestion1?.text=Sg1
            resultSuggestion2?.text=Sg2
            resultSuggestion3?.text=Sg3

            BMIText.text=BMIResult2Digit.toString()+textBDdec+bodyIndex
            Log.i("BMI Result ","$BMIResult2Digit")
            Log.i("BMI Result ","${resultSuggestion1.toString()}")

        }

        return view
    }





}