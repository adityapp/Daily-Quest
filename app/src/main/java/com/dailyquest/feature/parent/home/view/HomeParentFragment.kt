package com.dailyquest.feature.parent.homeParent.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.ChildrenListAdapter
import com.dailyquest.feature.parent.homeParent.HomeParentPresenterContract
import com.dailyquest.feature.parent.homeParent.HomeParentViewContract
import com.dailyquest.feature.parent.homeParent.presenter.HomeParentPresenter
import kotlinx.android.synthetic.main.fragment_home_parent.*
import kotlinx.android.synthetic.main.fragment_home_parent.view.*

class HomeParentFragment : Fragment(), HomeParentViewContract, View.OnClickListener {
    private lateinit var presenter: HomeParentPresenterContract
    private lateinit var view: RelativeLayout
    private lateinit var adapter: ChildrenListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home_parent, container, false) as RelativeLayout
        presenter = HomeParentPresenter(this)

        view.fab_add_children.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v) {
            fab_add_children -> presenter.addNewChildren()
        }
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
}
