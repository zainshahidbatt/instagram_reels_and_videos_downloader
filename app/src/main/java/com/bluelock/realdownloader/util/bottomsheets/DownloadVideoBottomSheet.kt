package com.bluelock.realdownloader.util.bottomsheets

import androidx.fragment.app.Fragment
import com.bluelock.realdownloader.R
import com.bluelock.realdownloader.databinding.DialogBottomVideoNotFoundBinding
import com.example.ads.GoogleManager
import com.example.ads.newStrategy.types.GoogleInterstitialType
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.showDownloadVideoBottomSheet(
    googleManager: GoogleManager,
    onClick: () -> Unit
) {

    fun showInterstitialAd(callback: () -> Unit) {
        val ad: InterstitialAd? =
            googleManager.createInterstitialAd(GoogleInterstitialType.MEDIUM)

        if (ad == null) {
            callback.invoke()
            return
        } else {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    callback.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    callback.invoke()
                }
            }
            ad.show(requireActivity())
        }
    }

    BottomSheetDialog(
        requireContext(),
        R.style.SheetDialog
    ).also { dialog ->
        val binding = DialogBottomVideoNotFoundBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.behavior.isDraggable = false
        dialog.setCanceledOnTouchOutside(false)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.apply {
            btnClear.setOnClickListener {
                showInterstitialAd {
                    dialog.dismiss()
                    onClick.invoke()
                }
            }
            ivCross.setOnClickListener {
                showInterstitialAd {
                    dialog.dismiss()
                    onClick.invoke()
                }
            }


//            val nativeAd: NativeAd? = googleManager.createNativeAdSmall()
//            nativeAd?.let {
//                val nativeAdLayoutBinding = NativeAdBannerLayoutBinding.inflate(layoutInflater)
//                nativeAdLayoutBinding.nativeAdView.loadNativeAd(ad = it)
//                binding.nativeView.removeAllViews()
//                binding.nativeView.addView(nativeAdLayoutBinding.root)
//                binding.nativeView.visibility = View.VISIBLE
//            }


        }
        dialog.show()
    }
}