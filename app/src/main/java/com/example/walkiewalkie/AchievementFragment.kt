package com.example.walkiewalkie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private val LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY = "last_rewarded_ad_view_timestamp"
    private val REWARDED_AD_VIEW_COUNT_KEY = "rewarded_ad_view_count"

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
        view.findViewById<TextView>(R.id.tv_total_coins).text = "Total coins: $totalCoins"

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("com.example.walkiewalkie", Context.MODE_PRIVATE)
        rewardedAdViewCount = sharedPreferences.getInt("rewarded_ad_view_count", 0)
        rewardedAdViewCount = sharedPreferences.getInt(REWARDED_AD_VIEW_COUNT_KEY, 0)
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
                Log.d(TAG, "Ad opened")
            }

            override fun onAdClicked() {
                Log.d(TAG, "Ad clicked")
            }

            override fun onAdClosed() {
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
                val lastViewTimestamp = sharedPreferences.getLong(LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY, 0L)
                val elapsedMillis = currentTimeMillis - lastViewTimestamp

                if(elapsedMillis >= 24 * 60 * 60 * 1000){
                    rewardedAdViewCount=0
                }

                if (rewardedAdViewCount < MAX_REWARDED_ADS_VIEW_COUNT ) {
                    val activityContext = requireActivity()

                    rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            createAndLoadRewardedAd() // Load a new rewarded ad
                            Log.d(TAG, "Rewarded ad dismissed")
                            databaseHelper.updateTotalCoins(totalCoins)
                            resetRewardedAdCountAndTimestamp() // Reset the count and timestamp
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                            Log.d(TAG, "Failed to show rewarded ad: ${adError?.message}")
                            // Handle the error when the rewarded ad fails to show
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
                        view?.findViewById<TextView>(R.id.tv_total_coins)?.text = "Total coins: $totalCoins"
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
        editor.putInt("rewarded_ad_view_count", 0)
        editor.putLong(LAST_REWARDED_AD_VIEW_TIMESTAMP_KEY, currentTimeMillis)
        editor.apply()
    }
}


