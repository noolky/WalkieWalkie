package com.example.walkiewalkie.AchievementStepActivity

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.walkiewalkie.DataBase.DatabaseHelper
import com.example.walkiewalkie.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AchievementFragment : Fragment() {

    private val TAG = "AchievementFragment"
    private var totalCoins = 0
    private var adLoadProgress = 0
    private lateinit var adView: AdView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dBhelper: DBhelper
    private val LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY = "last_rewarded_ad_view_timestamp"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MobileAds.initialize(requireContext())
        bannerAd()
        loadRewardedAd()
        databaseHelper = DatabaseHelper(requireContext())
        totalCoins = databaseHelper.getTotalCoins()
        view.findViewById<TextView>(R.id.tv_total_coins).text = " $totalCoins"

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("com.example.walkiewalkie", Context.MODE_PRIVATE)
        rewardedAdViewCount = sharedPreferences.getInt("rewarded_ad_view_count", 0)

       /*  // add value
         val databaseHelper = DatabaseHelper(requireContext())
         // Add a value to the total coins in the database
         val coinsToAdd = 18000000
         databaseHelper.updateTotalCoins(databaseHelper.getTotalCoins() + coinsToAdd)
*/
        val updatedTotalCoins = databaseHelper.getTotalCoins()
        view.findViewById<TextView>(R.id.tv_total_coins).text = " $updatedTotalCoins"
        val voucher1Image1 = view?.findViewById<ImageView>(R.id.voucher1Image1)
        val voucher1Image2 = view?.findViewById<ImageView>(R.id.voucher1Image2)
        val voucher1ImageButton = view?.findViewById<Button>(R.id.voucher1ImageButton)
        val voucher1ImageButtonR = view?.findViewById<Button>(R.id.voucher1ImageButtonR)
        val dbHelper = context?.let { DatabaseHelper(it) }
        val vChecker1Value = dbHelper?.getVChecker1()
        val vChecker2Value = dbHelper?.getVChecker2()
        val vChecker3Value = dbHelper?.getVChecker3()


        if(vChecker1Value==1){
            voucher1Image1?.visibility = View.GONE
            voucher1Image2?.visibility = View.VISIBLE
            voucher1ImageButton?.visibility = View.GONE
            voucher1ImageButtonR?.visibility = View.VISIBLE
        }

        view?.findViewById<Button>(R.id.voucher1ImageButton)?.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to redeem Voucher 1 with 1000 Coins?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                if (totalCoins >= 1) {
                    totalCoins -= 1
                    databaseHelper.updateTotalCoins(totalCoins)
                    view?.findViewById<TextView>(R.id.tv_total_coins)?.text = " $totalCoins"
                    if (voucher1Image1?.visibility == View.VISIBLE) {
                        voucher1Image1.visibility = View.GONE
                        voucher1Image2?.visibility = View.VISIBLE
                        var updateV = 1
                        databaseHelper.updateVChecker1(updateV)
                        Toast.makeText(requireContext(), "Voucher1 Redeemed", Toast.LENGTH_SHORT).show()
                        voucher1ImageButton?.visibility = View.GONE
                        voucher1ImageButtonR?.visibility = View.VISIBLE
                    }
                }else {
                    Toast.makeText(requireContext(), "Not enough coins.", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        val voucher2Image1 = view?.findViewById<ImageView>(R.id.voucher2Image1)
        val voucher2Image2 = view?.findViewById<ImageView>(R.id.voucher2Image2)
        val voucher2ImageButton = view?.findViewById<Button>(R.id.voucher2ImageButton)
        val voucher2ImageButtonR = view?.findViewById<Button>(R.id.voucher2ImageButtonR)
        if(vChecker2Value==2){
            voucher2Image1?.visibility = View.GONE
            voucher2Image2?.visibility = View.VISIBLE
            voucher2ImageButton?.visibility = View.GONE
            voucher2ImageButtonR?.visibility = View.VISIBLE
        }

        view?.findViewById<Button>(R.id.voucher2ImageButton)?.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to redeem Voucher 2 with 1200 Coins?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                if (totalCoins >= 1200) {
                    totalCoins -= 1200
                    databaseHelper.updateTotalCoins(totalCoins)
                    view?.findViewById<TextView>(R.id.tv_total_coins)?.text = " $totalCoins"

                    if (voucher2Image1?.visibility == View.VISIBLE) {
                        voucher2Image1.visibility = View.GONE
                        voucher2Image2?.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Voucher2 Redeemed", Toast.LENGTH_SHORT).show()
                        var updateV = 2
                        databaseHelper.updateVChecker2(updateV)
                        voucher2ImageButton?.visibility = View.GONE
                        voucher2ImageButtonR?.visibility = View.VISIBLE

                    }
                }else {
                    Toast.makeText(requireContext(), "Not enough coins.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }



        val voucher3Image1 = view?.findViewById<ImageView>(R.id.voucher3Image1)
        val voucher3Image2 = view?.findViewById<ImageView>(R.id.voucher3Image2)
        val voucher3ImageButton = view?.findViewById<Button>(R.id.voucher3ImageButton)
        val voucher3ImageButtonR = view?.findViewById<Button>(R.id.voucher3ImageButtonR)
        if(vChecker3Value==1){
            voucher3Image1?.visibility = View.GONE
            voucher3Image2?.visibility = View.VISIBLE
            voucher3ImageButton?.visibility = View.GONE
            voucher3ImageButtonR?.visibility = View.VISIBLE
        }
        view?.findViewById<Button>(R.id.voucher3ImageButton)?.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to redeem Voucher 3 with 1500 Coins?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->

                if (totalCoins >= 1500) {
                    totalCoins -= 1500
                    databaseHelper.updateTotalCoins(totalCoins)
                    view?.findViewById<TextView>(R.id.tv_total_coins)?.text =
                        " $totalCoins"

                    if (voucher3Image1?.visibility == View.VISIBLE) {
                        voucher3Image1.visibility = View.GONE
                        voucher3Image2?.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Voucher3 Redeemed", Toast.LENGTH_SHORT).show()
                        var updateV = 1
                        databaseHelper.updateVChecker3(updateV)
                        voucher3ImageButton?.visibility = View.GONE
                        voucher3ImageButtonR?.visibility = View.VISIBLE
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Not enough coins.", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

//reset voucher

        view?.findViewById<Button>(R.id.voucher1ImageButtonR)?.setOnClickListener {
            databaseHelper.updateVChecker1(0)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            val voucher1ImageButton = view?.findViewById<Button>(R.id.voucher1ImageButton)
            val voucher1ImageButtonR = view?.findViewById<Button>(R.id.voucher1ImageButtonR)
            val voucher1Image1 = view?.findViewById<ImageView>(R.id.voucher1Image1)
            val voucher1Image2 = view?.findViewById<ImageView>(R.id.voucher1Image2)

            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to reset Voucher 1?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                voucher1ImageButtonR?.visibility = View.GONE
                voucher1ImageButton?.visibility = View.VISIBLE

                if (voucher1Image2?.visibility == View.VISIBLE) {
                    voucher1Image2.visibility = View.GONE
                    voucher1Image1?.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Voucher1 reset done", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        view?.findViewById<Button>(R.id.voucher2ImageButtonR)?.setOnClickListener {
            databaseHelper.updateVChecker2(0)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            val voucher2ImageButton = view?.findViewById<Button>(R.id.voucher2ImageButton)
            val voucher2ImageButtonR = view?.findViewById<Button>(R.id.voucher2ImageButtonR)
            val voucher2Image1 = view?.findViewById<ImageView>(R.id.voucher2Image1)
            val voucher2Image2 = view?.findViewById<ImageView>(R.id.voucher2Image2)

            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to reset Voucher 2?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                voucher2ImageButtonR?.visibility = View.GONE
                voucher2ImageButton?.visibility = View.VISIBLE

                if (voucher2Image2?.visibility == View.VISIBLE) {
                    voucher2Image2.visibility = View.GONE
                    voucher2Image1?.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Voucher2 reset done", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        view?.findViewById<Button>(R.id.voucher3ImageButtonR)?.setOnClickListener {
            databaseHelper.updateVChecker3(0)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            val voucher3ImageButton = view?.findViewById<Button>(R.id.voucher3ImageButton)
            val voucher3ImageButtonR = view?.findViewById<Button>(R.id.voucher3ImageButtonR)
            val voucher3Image1 = view?.findViewById<ImageView>(R.id.voucher3Image1)
            val voucher3Image2 = view?.findViewById<ImageView>(R.id.voucher3Image2)

            alertDialogBuilder.setTitle("Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to reset Voucher 3?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                voucher3ImageButtonR?.visibility = View.GONE
                voucher3ImageButton?.visibility = View.VISIBLE

                if (voucher3Image2?.visibility == View.VISIBLE) {
                    voucher3Image2.visibility = View.GONE
                    voucher3Image1?.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Voucher3 reset done", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun adsLoadedProgress() {
        ++adLoadProgress
        if (adLoadProgress == 4) {
            Toast.makeText(requireContext(), "All ads are loaded, Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bannerAd() {
        adView = requireView().findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "Ad loaded")
                adsLoadedProgress()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Code to be executed when an ad request fails.
                val errorCode = loadAdError.code
                val errorMessage = loadAdError.message
                Log.d(TAG, "Ad failed to load: errorCode=$errorCode, errorMessage=$errorMessage")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(TAG, "Ad opened")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d(TAG, "Ad clicked")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "Ad closed")
            }
        }
    }

    private var rewardedAdViewCount = 0
    private val MAX_REWARDED_ADS_VIEW_COUNT = 3 // set the maximum number of rewarded ads to be displayed here

    private lateinit var rewardedAd: RewardedAd

    private fun createAndLoadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(requireContext(), "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
                Log.d(TAG, "Rewarded ad loaded")
                // Perform any necessary actions when the rewarded ad is loaded
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.d(TAG, "Failed to load rewarded ad: ${error.message}")
                // Handle the error when the rewarded ad fails to load
            }
        })
    }

    private fun loadRewardedAd() {
        createAndLoadRewardedAd()

        // Set the click listener for the rewarded ad button
        view?.findViewById<Button>(R.id.btn_rewarded_ad)?.setOnClickListener {
            if (::rewardedAd.isInitialized) {
                val currentTimeMillis = System.currentTimeMillis()
                val lastViewTimestamp = databaseHelper.getLastViewTimestamp()
                val elapsedMillis = currentTimeMillis - lastViewTimestamp

                if (elapsedMillis >= 24 * 60 * 60 * 1000) {
                    rewardedAdViewCount = 0
                }

                if (rewardedAdViewCount < MAX_REWARDED_ADS_VIEW_COUNT) {
                    val activityContext = requireActivity()

                    rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            createAndLoadRewardedAd()
                            Log.d(TAG, "Rewarded ad dismissed")
                            databaseHelper.updateTotalCoins(totalCoins)
                            databaseHelper.updateLastViewTimestamp(currentTimeMillis)
                            rewardedAdViewCount++
                            resetRewardedAdCountAndTimestamp()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                            Log.d(TAG, "Failed to show rewarded ad: ${adError?.message}")
                        }

                        override fun onAdShowedFullScreenContent() {
                            Log.d(TAG, "Rewarded ad showed")
                        }
                    }

                    rewardedAd.show(activityContext) { rewardItem ->
                        val rewardAmount = rewardItem.amount
                        val rewardType = rewardItem.type
                        Log.d(TAG, "User earned reward: $rewardAmount $rewardType")
                        totalCoins += 3
                        view?.findViewById<TextView>(R.id.tv_total_coins)?.text = " $totalCoins"

                        // Update the timestamp of the last rewarded ad view
                        val currentTimeMillis = System.currentTimeMillis()
                        databaseHelper.updateLastViewTimestamp(currentTimeMillis)
                        resetRewardedAdCountAndTimestamp()

                        Toast.makeText(
                            requireContext(),
                            "3 coins successfully earned",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Update the timestamp of the last rewarded ad view
                        val editor = sharedPreferences.edit()
                        editor.putLong(LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY, currentTimeMillis)
                        editor.apply()
                        rewardedAdViewCount++
                        resetRewardedAdCountAndTimestamp()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "The rewarded ad wasn't loaded yet or the cooldown period hasn't elapsed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "Rewarded ad not loaded or cooldown period not elapsed")
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "The rewarded ad wasn't initialized yet.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Rewarded ad not initialized")
            }
        }
    }

    private fun resetRewardedAdCountAndTimestamp() {
        val currentTimeMillis = System.currentTimeMillis()
        val editor = sharedPreferences.edit()
        editor.putInt("rewarded_ad_view_count", rewardedAdViewCount)
        editor.putLong(LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY, currentTimeMillis)
        editor.apply()
    }

}