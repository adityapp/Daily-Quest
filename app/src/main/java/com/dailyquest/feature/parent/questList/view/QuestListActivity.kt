package com.dailyquest.feature.parent.questList.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.QuestListAdapter
import com.dailyquest.base.BaseActivity
import com.dailyquest.feature.parent.questList.QuestListPresenterContract
import com.dailyquest.feature.parent.questList.QuestListViewContract
import com.dailyquest.feature.parent.questList.presenter.QuestListPresenter
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestListActivity : BaseActivity<QuestListPresenterContract>(), QuestListViewContract {

    private lateinit var adapter: QuestListAdapter
    private lateinit var pref: SessionManager

    override fun layoutId() = R.layout.activity_quest_list

    override fun setupView() {
        beginWith { setupActionBar() }
            .then { setupPresenter() }
            .then { setupSession() }
            .then { setupOnClick() }
    }

    private fun setupOnClick() {
        fab_add_quest.setOnClickListener {
            presenter.addNewQuest()
        }
    }

    override fun showQuestList(list: List<Any>) {
        adapter = QuestListAdapter(this, list, pref)
        rv_quest.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_quest.adapter = adapter
    }

    override fun openNewChildrenDialog() {
    }

    override fun onResume() {
        super.onResume()
        presenter.getQuestList()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPresenter() {
        presenter = QuestListPresenter(this)
    }

    private fun setupSession() {
        pref = SessionManager(this)
    }
}
