package me.polamokh.elcheck.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentOrdersBinding
import me.polamokh.elcheck.ui.AddOrderParticipantDialog
import me.polamokh.elcheck.ui.SharedViewModel

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ordersAdapter = OrdersAdapter({
            sharedViewModel.setOrderId(it.orderId)
            findNavController().navigate(
                OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                    it.orderId
                )
            )
        }, {
            viewModel.removeOrder(it)
            true
        })
        binding.ordersRecyclerView.apply {
            this.adapter = ordersAdapter
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            ordersAdapter.submitList(it)
        }

        binding.addOrder.setOnClickListener {
            AddOrderParticipantDialog(R.string.add_order, R.string.title_hint) {
                viewModel.addOrder(it)
            }
                .show(parentFragmentManager, null)
        }
    }
}