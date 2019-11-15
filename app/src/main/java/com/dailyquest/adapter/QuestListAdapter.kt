package com.dailyquest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.dialog.DetailQuestDialog
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.timestampToDate
import kotlinx.android.synthetic.main.card_quest_list.view.*

class QuestListAdapter(
    private val context: Context,
    private val dataSet: List<QuestModel>,
    private val pref: SessionManager
) :
    RecyclerView.Adapter<QuestListAdapter.ViewHolder>() {

    private val dialog: DetailQuestDialog by lazy { DetailQuestDialog(context, pref) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_quest_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataSet[position].let { quest ->
            holder.itemView.apply {
                tv_quest_list_title.text = quest.title
                tv_quest_list_description.text = quest.description
                tv_quest_list_time.text = quest.createdAt.timestampToDate()

                cv_quest.setOnClickListener {
                    dialog.quest = quest
                    dialog.show()
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}