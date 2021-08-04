package me.polamokh.elcheck.core.expenses.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.core.databinding.ItemExpenseParticipantBinding
import me.polamokh.elcheck.core.model.ExpenseParticipant

class ExpenseParticipantsAdapter(
    private val onCheckedParticipant: (isChecked: Boolean, expenseParticipant: ExpenseParticipant) -> Unit
) :
    ListAdapter<ExpenseParticipant, ExpenseParticipantsAdapter.ExpenseParticipantsViewHolder>(
        ExpenseParticipantDiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseParticipantsViewHolder {
        return ExpenseParticipantsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExpenseParticipantsViewHolder, position: Int) {
        holder.bind(currentList, onCheckedParticipant)
    }

    class ExpenseParticipantsViewHolder(val binding: ItemExpenseParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            expenseParticipants: List<ExpenseParticipant>,
            onCheckedParticipant: (isChecked: Boolean, expenseParticipant: ExpenseParticipant) -> Unit
        ) {
            binding.participantCheck.isChecked = expenseParticipants[adapterPosition].isChecked
            binding.participantCheck.text = expenseParticipants[adapterPosition].participant.name
            binding.participantValue.text =
                "%.2f".format(expenseParticipants[adapterPosition].value)

            binding.participantCheck.setOnClickListener {
                onCheckedParticipant(
                    binding.participantCheck.isChecked,
                    expenseParticipants[adapterPosition]
                )
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ExpenseParticipantsViewHolder {
                val binding = ItemExpenseParticipantBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ExpenseParticipantsViewHolder(binding)
            }
        }
    }

    object ExpenseParticipantDiffCallback : DiffUtil.ItemCallback<ExpenseParticipant>() {
        override fun areItemsTheSame(
            oldItem: ExpenseParticipant,
            newItem: ExpenseParticipant
        ) = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ExpenseParticipant,
            newItem: ExpenseParticipant
        ) = oldItem == newItem
    }
}