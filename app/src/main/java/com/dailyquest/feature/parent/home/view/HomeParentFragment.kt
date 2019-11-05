package com.dailyquest.feature.parent.home.view


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.ChildrenListAdapter
import com.dailyquest.base.BaseFragment
import com.dailyquest.feature.parent.home.HomeParentPresenterContract
import com.dailyquest.feature.parent.home.HomeParentViewContract
import com.dailyquest.feature.parent.home.presenter.HomeParentPresenter
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.fragment_home_parent.view.*

class HomeParentFragment : BaseFragment<HomeParentPresenterContract>(), HomeParentViewContract {

    private lateinit var adapter: ChildrenListAdapter

    override fun layoutId(): Int = R.layout.fragment_home_parent

    override fun setupView() {
        beginWith { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun showChildrenList(list: List<Any>) {
        context?.let {
            adapter = ChildrenListAdapter(it, list)
            view.rv_children.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            view.rv_children.adapter = adapter
        }
    }

    override fun openNewChildrenDialog() {

    }

    override fun onResume() {
        super.onResume()
        presenter.getChildrenList()
    }

    private fun setupPresenter() {
        presenter = HomeParentPresenter(this)
    }

    private fun setupOnClick() {
        view.fab_add_children.setOnClickListener { presenter.addNewChildren() }
    }
}
