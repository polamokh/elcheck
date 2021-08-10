package me.polamokh.elcheck.ui.expenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.databinding.ItemExpenseBinding

class ExpensesAdapter(private val onLongClickListener: (expense: Expense) -> Boolean) :
    ListAdapter<Expense, ExpensesAdapter.ExpensesViewHolder>(
        ExpenseDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        return ExpensesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnLongClickListener { onLongClickListener(item) }
        holder.bind(item)
    }

    class ExpensesViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.expense = expense
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ExpensesViewHolder {
                val binding = ItemExpenseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ExpensesViewHolder(binding)
            }
        }
    }

    object ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense) =
            oldItem.expenseId == newItem.expenseId

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense) =
            oldItem == newItem
    }
}