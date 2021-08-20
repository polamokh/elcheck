package me.polamokh.elcheck.ui.participants

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.ItemParticipantBinding
import me.polamokh.elcheck.model.ParticipantWithTotalExpenses
import java.util.*

class ParticipantsAdapter(
    private val context: Context,
    private val onDeleteClickListener: (participantWithTotalExpenses: ParticipantWithTotalExpenses) -> Unit
) :
    ListAdapter<ParticipantWithTotalExpenses, ParticipantsAdapter.ParticipantsViewHolder>(
        ParticipantDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        return ParticipantsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context, onDeleteClickListener)
    }

    class ParticipantsViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            participantWithTotalExpenses: ParticipantWithTotalExpenses,
            context: Context,
            onDeleteClickListener: (participantWithTotalExpenses: ParticipantWithTotalExpenses) -> Unit
        ) {
            binding.participantWithTotalExpenses = participantWithTotalExpenses
            binding.participantTotalExpenses.text =
                context.getString(
                    R.string.price_format,
                    Currency.getInstance(Locale.getDefault()).symbol,
                    participantWithTotalExpenses.totalExpenses
                )
            binding.deleteParticipant.setOnClickListener {
                onDeleteClickListener(
                    participantWithTotalExpenses
                )
            }
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