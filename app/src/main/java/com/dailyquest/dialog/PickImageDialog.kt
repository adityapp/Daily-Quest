package com.dailyquest.dialog

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.utils.Constants
import com.dailyquest.utils.beginWith
import kotlinx.android.synthetic.main.dialog_pick_image.*

class PickImageDialog(private val activity: Activity) : BaseDialog(activity) {
    override fun layoutId() = R.layout.dialog_pick_image

    override fun setupView() {
        beginWith { setupOnClick() }
    }

    private fun setupOnClick() {
        ll_pick_from_camera.setOnClickListener {
            pickImageFromCamera()
        }

        ll_pick_from_gallery.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromCamera() {
        activity.startActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE),
            0
        )
    }

    private fun pickImageFromGallery() {
        activity.startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), 1
        )
    }

}