package com.dailyquest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dailyquest.R
import com.dailyquest.feature.common.detailQuest.view.DetailQuestActivity
import com.dailyquest.model.QuestModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.remove
import com.dailyquest.utils.setStatusIndicator
import com.dailyquest.utils.timestampToDate
import kotlinx.android.synthetic.main.card_quest_list.view.*

class QuestListAdapter(
    private val context: Context,
    private val dataSet: List<QuestModel>
) :
    RecyclerView.Adapter<QuestListAdapter.ViewHolder>() {

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
                when {
                    quest.fullName.isNullOrBlank() -> tv_quest_list_name.remove()
                    else -> tv_quest_list_name.text = quest.fullName
                }

                tv_quest_list_title.text = quest.title
                tv_quest_list_description.text = quest.description
                tv_quest_list_time.text = quest.createdAt.timestampToDate()
                cv_status_indicator.setStatusIndicator(context, quest.status)

                cv_quest.setOnClickListener {
                    val intent = Intent(context, DetailQuestActivity::class.java)
                    intent.putExtra(Constants.DATABASE_QUEST, quest)
                    context.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}