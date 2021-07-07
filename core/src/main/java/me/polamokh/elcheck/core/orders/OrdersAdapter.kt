package me.polamokh.elcheck.core.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.core.databinding.ItemOrderBinding
import me.polamokh.elcheck.data.local.order.Order

class OrdersAdapter(private val onClickListener: (order: Order) -> Unit) :
    ListAdapter<Order, OrdersAdapter.OrdersViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener(item) }
        holder.bind(item)
    }

    class OrdersViewHolder(
        private val binding: ItemOrderBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.order = order
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrdersViewHolder {
                val binding = ItemOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return OrdersViewHolder(binding)
            }
        }
    }

    object OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) =
            oldItem.orderId == newItem.orderId

        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem == newItem
    }
}