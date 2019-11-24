package com.dailyquest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.feature.parent.questList.view.QuestListActivity
import com.dailyquest.model.ChildrenModel
import com.dailyquest.utils.Constants
import kotlinx.android.synthetic.main.card_children_list.view.*

class ChildrenListAdapter(private val context: Context, private val dataSet: List<ChildrenModel>) :
    RecyclerView.Adapter<ChildrenListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_children_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataSet[position].let { children ->
            holder.itemView.apply {
                tv_fullname.text = children.fullName
                cv_children.setOnClickListener {
                    val intent = Intent(context, QuestListActivity::class.java)
                    intent.putExtra(Constants.ANAK, children)
                    context.startActivity(intent)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}