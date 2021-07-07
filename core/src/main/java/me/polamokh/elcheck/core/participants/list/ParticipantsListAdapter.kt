package me.polamokh.elcheck.core.participants.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.core.databinding.ItemParticipantBinding
import me.polamokh.elcheck.data.local.participant.Participant

class ParticipantsListAdapter :
    ListAdapter<Participant, ParticipantsListAdapter.ParticipantsListViewHolder>(
        ParticipantDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsListViewHolder {
        return ParticipantsListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ParticipantsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ParticipantsListViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(participant: Participant) {
            binding.participant = participant
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ParticipantsListViewHolder {
                val binding = ItemParticipantBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ParticipantsListViewHolder(binding)
            }
        }
    }

    object ParticipantDiffCallback : DiffUtil.ItemCallback<Participant>() {
        override fun areItemsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem.participantId == newItem.participantId

        override fun areContentsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem == newItem
    }
}