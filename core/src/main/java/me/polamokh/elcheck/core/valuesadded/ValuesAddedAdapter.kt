package me.polamokh.elcheck.core.valuesadded

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.core.databinding.ItemValueAddedBinding
import me.polamokh.elcheck.data.local.valueadded.ValueAdded

class ValuesAddedAdapter(private val onLongClickListener: (valueAdded: ValueAdded) -> Boolean) :
    ListAdapter<ValueAdded, ValuesAddedAdapter.ValuesAddedViewHolder>(
        ValueAddedDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuesAddedViewHolder {
        return ValuesAddedViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ValuesAddedViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnLongClickListener { onLongClickListener(item) }
        holder.bind(item)
    }

    class ValuesAddedViewHolder(private val binding: ItemValueAddedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(valueAdded: ValueAdded) {
            binding.valueAdded = valueAdded
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