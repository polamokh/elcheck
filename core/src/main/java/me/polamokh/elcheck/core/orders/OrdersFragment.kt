package me.polamokh.elcheck.core.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

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
            viewModel.addOrder("Title")
        }
    }
}