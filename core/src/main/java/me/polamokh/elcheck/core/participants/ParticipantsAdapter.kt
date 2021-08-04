package me.polamokh.elcheck.core.participants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.core.databinding.ItemParticipantBinding
import me.polamokh.elcheck.data.local.participantexpense.ParticipantWithExpenses

class ParticipantsAdapter :
    ListAdapter<ParticipantWithExpenses, ParticipantsAdapter.ParticipantsViewHolder>(
        ParticipantDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        return ParticipantsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ParticipantsViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(participantWithExpenses: ParticipantWithExpenses) {
            binding.participantWithExpenses = participantWithExpenses
            var expenses = 0.0
            for (expense in participantWithExpenses.expenses) {
                expenses += expense.value
            }
            binding.participantExpenses.text = "%.2f".format(expenses)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ParticipantsViewHolder {
                val binding = ItemParticipantBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ParticipantsViewHolder(binding)
            }
        }
    }

    object ParticipantDiffCallback : DiffUtil.ItemCallback<ParticipantWithExpenses>() {
        override fun areItemsTheSame(
            oldItem: ParticipantWithExpenses,
            newItem: ParticipantWithExpenses
        ) =
            oldItem.participant.participantId == newItem.participant.participantId

        override fun areContentsTheSame(
            oldItem: ParticipantWithExpenses,
            newItem: ParticipantWithExpenses
        ) =
            oldItem == newItem
    }
}