package com.dailyquest.feature.common.detailQuest.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.dailyquest.R
import com.dailyquest.base.BaseActivity
import com.dailyquest.dialog.PickImageDialog
import com.dailyquest.feature.common.detailQuest.DetailQuestPresenterContract
import com.dailyquest.feature.common.detailQuest.DetailQuestViewContract
import com.dailyquest.feature.common.detailQuest.presenter.DetailQuestPresenter
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.*
import kotlinx.android.synthetic.main.activity_detail_quest.*


class DetailQuestActivity : BaseActivity<DetailQuestPresenterContract>(), DetailQuestViewContract {
    private lateinit var pref: SessionManager
    private lateinit var quest: QuestModel
    private var selectedImage: Uri? = null
    private lateinit var imagePicker: PickImageDialog

    override fun layoutId() = R.layout.activity_detail_quest

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { getExtra() }
            .then { setupSession() }
            .then { setupPresenter() }
            .then { setupQuest() }
            .then { setupImagePickerDialog() }
            .then { setupOnClick() }
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun setupContent(newQuest: QuestModel) {
        pref.getRole()?.let {
            if (it == Constants.ORANG_TUA) b_status.remove() else changeStatusState(newQuest.status)
        }
        tv_create_time.text = newQuest.createdAt.timestampToDate()
        tv_title.text = newQuest.title
        setStatus(newQuest.status)
        tv_description.text = newQuest.description
        tv_start_time.text = newQuest.startTime.timestampToDate()
        tv_end_time.text = newQuest.endTime.timestampToDate()
        quest = newQuest
        rl_image.remove()
        dismissLoadingDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.IMAGE_PICKER_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = data?.data
                    tv_file_upload.text = "Ubah Foto"
                    showImage(selectedImage)
                    imagePicker.dismiss()
                }
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        title = "Daily Quest"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getExtra() {
        quest = intent.getSerializableExtra(Constants.DATABASE_QUEST) as QuestModel
    }

    private fun setupSession() {
        pref = SessionManager(this)
    }

    private fun setupPresenter() {
        presenter = DetailQuestPresenter(this, pref)
    }

    private fun setupQuest() {
        quest.id?.let { id ->
            presenter.getQuest(id, quest.childrenUid.toString())
        }
    }

    private fun setupOnClick() {
        b_status.setOnClickListener {
            presenter.updateQuest(quest, selectedImage)
        }

        rl_upload_image.setOnClickListener { imagePicker.show() }

        iv_delete_image.setOnClickListener {
            selectedImage = null
            rl_image.remove()
            tv_file_upload.text = "Unggah Bukti Foto"
            changeStateStatusButton()
        }
    }

    private fun setupImagePickerDialog() {
        imagePicker = PickImageDialog(this)
    }

    private fun showImage(image: Uri?) {
        image?.let {
            iv_image.setImageURI(it)
            rl_image.show()
            changeStateStatusButton()
        }
    }

    private fun setStatus(status: String) {
        tv_status.text = status

        rl_status.setBackgroundResource(
            when (status) {
                Constants.STATUS_OPEN -> R.drawable.rounded_background_blue
                Constants.STATUS_ONGOING -> R.drawable.rounded_background_green
                Constants.STATUS_CLOSE -> R.drawable.rounded_background_red
                else -> R.drawable.rounded_background_gray
            }
        )
    }

    private fun changeStatusState(status: String) {
        when (status) {
            Constants.STATUS_OPEN -> b_status.text = "Jalankan tugas"
            Constants.STATUS_ONGOING -> {
                changeStateStatusButton()
                b_status.text = "Selesaikan tugas"
                rl_upload_image.show()
            }
            else -> {
                b_status.remove()
                rl_upload_image.remove()
            }
        }
    }

    private fun changeStateStatusButton() {
        if (selectedImage == null) {
            b_status.isClickable = false
            b_status.setBackgroundResource(R.drawable.rounded_background_gray)
        } else {
            b_status.isClickable = true
            b_status.setBackgroundResource(R.drawable.button_background)
        }
    }
}
