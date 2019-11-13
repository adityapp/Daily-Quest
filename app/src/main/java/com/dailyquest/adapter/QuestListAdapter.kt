package com.dailyquest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.dialog.DetailQuestDialog
import com.dailyquest.utils.SessionManager
import kotlinx.android.synthetic.main.card_quest_list.view.*

class QuestListAdapter(
    private val context: Context,
    private val dataSet: List<Any>,
    private val pref: SessionManager
) :
    RecyclerView.Adapter<QuestListAdapter.ViewHolder>() {

    private val dialog: DetailQuestDialog by lazy { DetailQuestDialog(context, pref) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_quest_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            cv_quest.setOnClickListener {
                dialog.show()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}