package com.dailyquest.feature.parent.questList.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.QuestListAdapter
import com.dailyquest.base.BaseActivity
import com.dailyquest.dialog.NewQuestDialog
import com.dailyquest.feature.parent.questList.QuestListPresenterContract
import com.dailyquest.feature.parent.questList.QuestListViewContract
import com.dailyquest.feature.parent.questList.presenter.QuestListPresenter
import com.dailyquest.model.ChildrenModel
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestListActivity : BaseActivity<QuestListPresenterContract>(), QuestListViewContract {

    private lateinit var adapter: QuestListAdapter
    private lateinit var pref: SessionManager
    private lateinit var children: ChildrenModel

    override fun layoutId() = R.layout.activity_quest_list

    override fun setupView() {
        beginWith { getExtra() }
            .then { setupActionBar() }
            .then { setupSession() }
            .then { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun showQuestList(list: List<QuestModel>) {
        adapter = QuestListAdapter(this, list, pref)
        rv_quest.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_quest.adapter = adapter
        dismissLoadingDialog()
    }

    override fun openNewQuestDialog(addListener: (QuestModel) -> Unit) {
        NewQuestDialog(this, addListener).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        presenter.getQuestList()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        title = children.fullName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter() {
        children.uid?.let { childrenUid ->
            presenter = QuestListPresenter(this, pref, childrenUid)
        }
    }

    private fun setupSession() {
        pref = SessionManager(this)
    }

    private fun setupOnClick() {
        fab_add_quest.setOnClickListener {
            presenter.addNewQuest()
        }
    }

    private fun getExtra() {
        children = intent.getSerializableExtra(Constants.ANAK) as ChildrenModel
    }
}
