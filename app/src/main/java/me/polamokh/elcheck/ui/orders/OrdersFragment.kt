package me.polamokh.elcheck.ui.orders

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.data.local.order.Order
import me.polamokh.elcheck.databinding.FragmentOrdersBinding
import me.polamokh.elcheck.ui.AddOrderParticipantDialog
import me.polamokh.elcheck.ui.BaseFragment
import me.polamokh.elcheck.ui.SharedViewModel

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        val ordersAdapter = OrdersAdapter({ onOrderClick(it) }, { onOrderDelete(it) })

        binding.ordersRecyclerView.apply {
            setHasFixedSize(true)
            this.adapter = ordersAdapter
        }

        binding.addOrder.setOnClickListener {
            onAddOrderClick()
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            ordersAdapter.submitList(it)
            binding.emptyLayout.emptyMsg.isVisible = it.isEmpty()
            if (it.isEmpty()) {
                binding.emptyLayout.emptyMsg.text = getString(
                    R.string.format_empty_list,
                    getString(R.string.title_checks).lowercase(),
                    getString(R.string.check).lowercase()
                )
            }
        }
    }

    private fun onAddOrderClick() {
        AddOrderParticipantDialog(R.string.add_check, R.string.hint_title) { title ->
            viewModel.addOrder(title)
        }
            .show(parentFragmentManager, null)
    }

    private fun onOrderDelete(order: Order) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_delete_check_dialog)
            .setIcon(R.drawable.ic_trash)
            .setMessage(getString(R.string.msg_delete_check_dialog, order.name))
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.removeOrder(order)
            }
            .setNeutralButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .show()
            .apply {
                getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(Color.rgb(255, 68, 68))
            }
    }

    private fun onOrderClick(order: Order) {
        sharedViewModel.setOrder(order)
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                order.orderId
            )
        )
    }
}