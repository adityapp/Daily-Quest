package com.dailyquest.feature.common.home.view


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.QuestListAdapter
import com.dailyquest.base.BaseFragment
import com.dailyquest.feature.common.home.HomePresenterContract
import com.dailyquest.feature.common.home.HomeViewContract
import com.dailyquest.feature.common.home.presenter.HomePresenter
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<HomePresenterContract>(), HomeViewContract {

    private lateinit var adapterQuest: QuestListAdapter
    private lateinit var pref: SessionManager

    override fun layoutId(): Int = R.layout.fragment_home

    override fun setupView() {
        beginWith { setupSession() }
            .then { setupPresenter() }
    }

    override fun showQuestList(list: List<QuestModel>) {
        context?.let {
            adapterQuest = QuestListAdapter(it, list, pref)
            view.rv_quest.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            view.rv_quest.adapter = adapterQuest
        }
        dismissLoadingDialog()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        if (pref.getRole() == Constants.ANAK) presenter.getQuestList() else presenter.getAllQuestList()
    }

    private fun setupPresenter() {
        presenter = HomePresenter(this, pref)
    }

    private fun setupSession() {
        context?.let {
            pref = SessionManager(it)
        }
    }
}
