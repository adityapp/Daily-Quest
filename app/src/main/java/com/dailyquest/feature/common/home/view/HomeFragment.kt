package com.dailyquest.feature.common.home.view


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.QuestListAdapter
import com.dailyquest.base.BaseFragment
import com.dailyquest.feature.common.home.HomePresenterContract
import com.dailyquest.feature.common.home.HomeViewContract
import com.dailyquest.feature.common.home.presenter.HomePresenter
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<HomePresenterContract>(), HomeViewContract {

    private lateinit var adapter: QuestListAdapter

    override fun layoutId(): Int = R.layout.fragment_home

    override fun setupView() {
        beginWith { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun showAllQuestList(list: List<Any>) {
        context?.let {
            adapter = QuestListAdapter(it, list)
            view.rv_quest.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            view.rv_quest.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllQuestList()
    }

    private fun setupPresenter() {
        presenter = HomePresenter(this)
    }

    private fun setupOnClick() {

    }
}
