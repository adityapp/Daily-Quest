package com.dailyquest.feature.common.questList.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.QuestListAdapter
import com.dailyquest.feature.common.questList.QuestListPresenterContract
import com.dailyquest.feature.common.questList.QuestListViewContract
import com.dailyquest.feature.common.questList.presenter.QuestListPresenter
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestListActivity : AppCompatActivity(), View.OnClickListener, QuestListViewContract {
    private lateinit var presenter: QuestListPresenterContract
    private lateinit var adapter: QuestListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = QuestListPresenter(this)

        fab_add_quest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fab_add_quest -> presenter.addNewQuest()
        }
    }

    override fun showQuestList(list: List<Any>) {
        adapter = QuestListAdapter(this, list)
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
}
