package com.dailyquest.homeParent.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.homeParent.HomeParentPresenterContract
import com.dailyquest.homeParent.HomeParentViewContract
import com.dailyquest.homeParent.adapter.QuestListAdapter
import com.dailyquest.homeParent.presenter.HomeParentPresenter
import kotlinx.android.synthetic.main.fragment_home_parent.*
import kotlinx.android.synthetic.main.fragment_home_parent.view.*

class HomeParentFragment : Fragment(), HomeParentViewContract, View.OnClickListener {
    private lateinit var presenter: HomeParentPresenterContract
    private lateinit var view: RelativeLayout
    private lateinit var adapter: QuestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home_parent, container, false) as RelativeLayout
        presenter = HomeParentPresenter(this)

        view.fab_add_quest.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v) {
            fab_add_quest -> presenter.addNewQuest()
        }
    }

    override fun showQuestList(list: List<Any>) {
        context?.let {
            adapter = QuestListAdapter(it, list)
        }
        view.rv_quest.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rv_quest.adapter = adapter
    }

    override fun openNewQuestDialog() {

    }

    override fun onResume() {
        super.onResume()
        presenter.getQuestList()
    }
}
