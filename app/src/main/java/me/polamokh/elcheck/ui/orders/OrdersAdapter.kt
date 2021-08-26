package me.polamokh.elcheck.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.elcheck.data.local.order.Order
import me.polamokh.elcheck.databinding.ItemOrderBinding
import me.polamokh.elcheck.utils.getLocaleFormattedDateTime
import java.util.*

class OrdersAdapter(
    private val onClickListener: (order: Order) -> Unit,
    private val onDeleteClickListener: (order: Order) -> Unit
) :
    ListAdapter<Order, OrdersAdapter.OrdersViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener, onDeleteClickListener)
    }

    class OrdersViewHolder(
        private val binding: ItemOrderBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            order: Order,
            onClickListener: (order: Order) -> Unit,
            onDeleteClickListener: (order: Order) -> Unit
        ) {
            binding.order = order
            binding.orderDateTime.text =
                order.orderTime.getLocaleFormattedDateTime(Locale.getDefault())
            binding.root.setOnClickListener { onClickListener(order) }
            binding.deleteOrder.setOnClickListener { onDeleteClickListener(order) }
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