package me.polamokh.elcheck.ui.valuesadded

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.data.local.valueadded.ValueAdded
import me.polamokh.elcheck.databinding.ItemValueAddedBinding

class ValuesAddedAdapter(private val onDeleteClickListener: (valueAdded: ValueAdded) -> Unit) :
    ListAdapter<ValueAdded, ValuesAddedAdapter.ValuesAddedViewHolder>(
        ValueAddedDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuesAddedViewHolder {
        return ValuesAddedViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ValuesAddedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteClickListener)
    }

    class ValuesAddedViewHolder(private val binding: ItemValueAddedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            valueAdded: ValueAdded,
            onDeleteClickListener: (valueAdded: ValueAdded) -> Unit
        ) {
            binding.valueAdded = valueAdded
            binding.deleteValueAdded.setOnClickListener { onDeleteClickListener(valueAdded) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ValuesAddedViewHolder {
                val binding = ItemValueAddedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ValuesAddedViewHolder(binding)
            }
        }
    }

    object ValueAddedDiffCallback : DiffUtil.ItemCallback<ValueAdded>() {
        override fun areItemsTheSame(oldItem: ValueAdded, newItem: ValueAdded) =
            oldItem.valueAddedId == newItem.valueAddedId

        override fun areContentsTheSame(oldItem: ValueAdded, newItem: ValueAdded) =
            oldItem == newItem
    }
}