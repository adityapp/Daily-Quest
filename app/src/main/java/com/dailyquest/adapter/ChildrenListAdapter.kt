package com.dailyquest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.questList.view.QuestListActivity
import kotlinx.android.synthetic.main.card_children_list.view.*

class ChildrenListAdapter(private val context: Context, private val dataSet: List<Any>) :
    RecyclerView.Adapter<ChildrenListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_children_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cv_children.setOnClickListener {
            context.startActivity(Intent(context, QuestListActivity::class.java))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}