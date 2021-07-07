package me.polamokh.elcheck.core.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    private lateinit var viewModel: OrdersViewModel

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

        viewModel = ViewModelProvider(this)
            .get(OrdersViewModel::class.java)

        val ordersAdapter = OrdersAdapter {
            findNavController().navigate(
                OrdersFragmentDirections.actionOrdersFragmentToParticipantsListFragment(
                    it.orderId
                )
            )
        }
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