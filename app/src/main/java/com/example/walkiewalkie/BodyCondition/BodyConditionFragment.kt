package com.example.walkiewalkie.BodyCondition


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.walkiewalkie.DataBase.Helper
import com.example.walkiewalkie.R


class BodyConditionFragment : Fragment() {

    private lateinit var buttonCal: ImageButton
    private lateinit var BMIText:TextView
    private lateinit var resultChangesH:TextView
    private lateinit var resultChangesW:TextView

    //this can the DataBMI name when combine aden data
    private lateinit var dataBaseBMI: Helper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_body_condition, container, false )
        val sharedPreferences = context?.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", null)
        dataBaseBMI= Helper(requireContext())

        buttonCal=view.findViewById<ImageButton>(R.id.BMIbutton)
        buttonCal.setOnClickListener{
            view:View -> view.findNavController().navigate(R.id.action_bodyConditionFragment_to_BMICalculatorFragment)
        }

        BMIText =view.findViewById(R.id.TvBmiResult)
        resultChangesH=view.findViewById(R.id.TvHChanges)
        resultChangesW=view.findViewById(R.id.TvWChanges)

        val LastestBMI=dataBaseBMI.getLatestBMIForUser(username.toString())
        val BMIResult2Digit =LastestBMI

        val LastestHeight=dataBaseBMI.getLatestHeightForUser(username.toString())
        val CurrentHeight =LastestHeight.toString()

        val LastestWeight=dataBaseBMI.getLatestWeightForUser(username.toString())
        val CurrentWeight =LastestWeight.toString()

        val changesHeight=dataBaseBMI.getLatestHeightChangestForUser(username.toString())
        val currentChangesHeight=changesHeight

        val changesWeight=dataBaseBMI.getLatestWeightChangestForUser(username.toString())
        val currentChangesWeight=changesWeight


        val resultSuggestion1 = view?.findViewById<TextView>(R.id.TvSg1Result)
        val resultSuggestion2 = view?.findViewById<TextView>(R.id.TvSg2Result)
        val resultSuggestion3 = view?.findViewById<TextView>(R.id.TvSg3result)
        val foodDetails=view?.findViewById<TextView>(R.id.TvDetails1)
        val exerciseDetails=view?.findViewById<TextView>(R.id.TvDetails2)
        val Height=view?.findViewById<TextView>(R.id.CurrentHeight)
        val Weight=view?.findViewById<TextView>(R.id.CurrentWeight)


        var Sg1=""
        var Sg2=""
        var Sg3=""
        var color = 0
        var colorChangesH = 0
        var colorChangesW = 0
        var bodyIndex=""
        var textBDdec=""
        var textHeight=""
        var textWeight=""
        var food=""
        var exercise=""
        var tvHeightChanges=""
        var tvWeightChanges=""

        if(LastestBMI.toString().isEmpty()){
            // Handle the case where no latest BMI value is available
            BMIText.text = "No data available"
            // Set default values for suggestions or handle them accordingly
            resultSuggestion1?.text = "Enter your Height and Weight to calculate BMI"
            resultSuggestion2?.text = ""
            resultSuggestion3?.text = ""
            color= R.color.white

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
                    food="1)Increase calorie intake with nutrient-dense foods like avocados, nuts, and lean protein sources.\n" +
                            "2)Include healthy carbohydrates like whole grains, potatoes, and quinoa.\n" +
                            "3)Incorporate calorie-rich snacks such as dried fruits and nut butter."

                    exercise="1)Focus on strength training exercises to build muscle mass.\n" +
                            "2)Incorporate resistance training with moderate weights and proper form.\n" +
                            "3)Engage in activities like yoga or Pilates to improve flexibility and overall body strength."
                }

                BMIResult2Digit in 18.50..24.99 -> {
                    Sg1="Maintain a balanced diet with a variety of nutrients."
                    Sg2="Engage in regular physical activity for overall health."
                    Sg3="Prioritize stress management and quality sleep for overall well-being."
                    textBDdec=" You BMI Status is "
                    color = R.color.Normal
                    bodyIndex="Normal"
                    food="1)Maintain a balanced diet with a variety of fruits, vegetables, whole grains, lean proteins, and healthy fats.\n" +
                            "2)Focus on portion control and mindful eating.\n" +
                            "3)Stay hydrated and limit the consumption of sugary foods and beverages."

                    exercise="1)Aim for a balanced fitness routine that includes cardiovascular exercises like brisk walking, jogging, or cycling.\n" +
                            "2)Incorporate strength training exercises to maintain muscle tone and strength.\n" +
                            "3)Include flexibility exercises like stretching or yoga for improved range of motion."
                }

                BMIResult2Digit in 25.00..29.99 -> {
                    Sg1="Reduce calorie intake by making healthier food choices."
                    Sg2="Increase physical activity levels to burn calories."
                    Sg3="Incorporate more fruits, vegetables, and whole grains into your meals."
                    color = R.color.Over_Weight
                    textBDdec=" You BMI Status is "
                    bodyIndex="Over Weight"
                    food="1)Choose nutrient-rich, low-calorie foods such as fruits, vegetables, and lean proteins.\n" +
                            "2)Incorporate whole grains and healthy fats in moderate portions.\n" +
                            "3)Limit processed foods, sugary snacks, and beverages high in added sugars."
                    exercise="1)Engage in moderate-intensity cardiovascular exercises like swimming, dancing, or using an elliptical machine.\n" +
                            "2)Gradually increase intensity and duration of workouts over time.\n" +
                            "3)Consider low-impact activities like cycling or water aerobics to reduce stress on joints."
                }

                BMIResult2Digit > 29.99 -> {
                    Sg1="Consult a healthcare professional for comprehensive medical evaluation and guidance."
                    Sg2="Follow a balanced and calorie-controlled diet plan"
                    Sg3="Engage in regular physical activity and consider working with a qualified fitness professional."
                    color = R.color.Obese
                    textBDdec=" You BMI Status is "
                    bodyIndex="Obesity"
                    food="1)Prioritize a diet rich in fruits, vegetables, whole grains, lean proteins, and healthy fats.\n" +
                            "2)Reduce portion sizes and practice mindful eating.\n" +
                            "3)Avoid or limit high-calorie, processed foods, sugary snacks, and sugary beverages."
                    exercise="1)Begin with low-impact exercises like walking or water-based exercises.\n" +
                            "2)Gradually increase intensity and duration as fitness improves.\n" +
                            "3)Seek guidance from a certified fitness professional or physical therapist for safe and effective exercise options."
                }

                else ->{
                    // Set default values for suggestions or handle them accordingly
                    Sg1 = "Enter your Height and Weight to calculate BMI"
                    color= R.color.black
                    textBDdec=""
                    bodyIndex=" No data available "
                }

            }


            if(validateHeightWeight(CurrentWeight,CurrentHeight)){
                textHeight=CurrentHeight
                textWeight=CurrentWeight
            }

            else{
                textHeight="0"
                textWeight="0"
            }


            when{
                currentChangesWeight>0f ->{
                    tvWeightChanges="Compared with the last time Your body weight has increased by $currentChangesWeight KG"
                    colorChangesW=R.color.Obese
                }
                currentChangesWeight<0f ->{
                    tvWeightChanges="Compared with the last time Your body weight decreased by  ${kotlin.math.abs(currentChangesWeight)} KG"
                    colorChangesW=R.color.Normal
                }

                currentChangesWeight==0f ->{
                    tvWeightChanges="you didn't gain any weight"
                    colorChangesW=R.color.black
                }
            }

            when{
                currentChangesHeight>0f->{
                    tvHeightChanges="Compared with the last time, you have grown $currentChangesHeight CM taller "
                    colorChangesH=R.color.Normal
                }
                currentChangesHeight<0f->{
                    tvHeightChanges="You are ${kotlin.math.abs(currentChangesHeight)} CM shorter than last time  "
                    colorChangesH=R.color.Obese
                }
                currentChangesHeight==0f->{
                    tvHeightChanges="you didn't grow taller"
                    colorChangesH=R.color.black
                }
            }


            BMIText.setTextColor(ContextCompat.getColor(requireContext(), color))

            resultSuggestion1?.text=Sg1
            resultSuggestion2?.text=Sg2
            resultSuggestion3?.text=Sg3
            Height?.text="Current Height="+textHeight+"CM"
            Weight?.text="Current Weight="+textWeight+"KG"
            foodDetails?.text=food
            exerciseDetails?.text=exercise

            resultChangesH.setTextColor(ContextCompat.getColor(requireContext(), colorChangesH))
            resultChangesH.text=tvHeightChanges

            resultChangesW.setTextColor(ContextCompat.getColor(requireContext(), colorChangesW))
            resultChangesW.text=tvWeightChanges

            BMIText.text=BMIResult2Digit.toString()+textBDdec+bodyIndex
            Log.i("W Changes ","$currentChangesWeight")
            Log.i("H Changes ","$currentChangesHeight")

        }


        return view
    }

    private fun validateHeightWeight(weight: String? ,height: String?): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                false
            }

            height.isNullOrEmpty() -> {
                false
            }
            else -> true
        }
    }




}