package com.dailyquest.feature.parent.children.view


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.adapter.ChildrenListAdapter
import com.dailyquest.base.BaseFragment
import com.dailyquest.dialog.BarcodeDialog
import com.dailyquest.feature.parent.children.ChildrenPresenterContract
import com.dailyquest.feature.parent.children.ChildrenViewContract
import com.dailyquest.feature.parent.children.presenter.ChildrenPresenter
import com.dailyquest.model.ChildrenModel
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import kotlinx.android.synthetic.main.fragment_children.view.*

class ChildrenFragment : BaseFragment<ChildrenPresenterContract>(), ChildrenViewContract {
    private lateinit var adapter: ChildrenListAdapter
    private lateinit var pref: SessionManager

    override fun layoutId() = R.layout.fragment_children

    override fun setupView() {
        beginWith { setupSession() }
            .then { setupPresenter() }
            .then { setupOnClick() }
    }

    override fun showChildrenList(list: List<ChildrenModel>) {
        context?.let {
            adapter = ChildrenListAdapter(it, list)
            view.rv_children.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            view.rv_children.adapter = adapter
        }
        dismissLoadingDialog()
    }

    override fun showFailedMessage(message: String) {
        showToast(message)
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        presenter.getChildrenList()
    }

    private fun setupPresenter() {
        presenter = ChildrenPresenter(this, pref)
    }

    private fun setupSession() {
        context?.let {
            pref = SessionManager(it)
        }
    }

    private fun setupOnClick() {
        view.fab_add_children.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        context?.let {
            BarcodeDialog(it, pref).show()
        }
    }
}
