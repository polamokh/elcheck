package me.polamokh.elcheck.ui.participants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.databinding.ItemParticipantBinding
import me.polamokh.elcheck.model.ParticipantWithTotalExpenses

class ParticipantsAdapter :
    ListAdapter<ParticipantWithTotalExpenses, ParticipantsAdapter.ParticipantsViewHolder>(
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

        fun bind(participantWithTotalExpenses: ParticipantWithTotalExpenses) {
            binding.participantWithTotalExpenses = participantWithTotalExpenses
            binding.participantExpenses.text =
                "%.2f".format(participantWithTotalExpenses.totalExpenses)
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

    object ParticipantDiffCallback : DiffUtil.ItemCallback<ParticipantWithTotalExpenses>() {
        override fun areItemsTheSame(
            oldItem: ParticipantWithTotalExpenses,
            newItem: ParticipantWithTotalExpenses
        ) =
            oldItem.participant.participantId == newItem.participant.participantId

        override fun areContentsTheSame(
            oldItem: ParticipantWithTotalExpenses,
            newItem: ParticipantWithTotalExpenses
        ) =
            oldItem == newItem
    }
}