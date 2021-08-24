package me.polamokh.elcheck.ui.orders.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentOrderDetailsBinding
import me.polamokh.elcheck.ui.AddOrderParticipantDialog
import me.polamokh.elcheck.ui.BaseFragment
import me.polamokh.elcheck.ui.SharedViewModel
import me.polamokh.elcheck.ui.expenses.ExpensesFragment
import me.polamokh.elcheck.ui.participants.ParticipantsFragment
import me.polamokh.elcheck.ui.valuesadded.ValuesAddedFragment
import java.util.*

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    private val args: OrderDetailsFragmentArgs by navArgs()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        setupViewPager()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        sharedViewModel.orderName.observe(viewLifecycleOwner) {
            binding.toolbar.title = it
        }

        binding.addParticipant.setOnClickListener {
            onAddParticipantClick()
        }
        binding.addExpense.setOnClickListener {
            onAddExpenseClick()
        }
        binding.addValueAdded.setOnClickListener {
            onAddValueAddedClick()
        }

        sharedViewModel.orderTotalExpensesWithVA.observe(viewLifecycleOwner) {
            binding.orderTotalExpenses.text = getString(
                R.string.format_order_total_price,
                Currency.getInstance(Locale.getDefault()).symbol,
                it ?: 0.0
            )
        }
    }

    private fun setupViewPager() {
        val fragments = listOf(
            ParticipantsFragment.newInstance(args.orderId),
            ExpensesFragment.newInstance(args.orderId),
            ValuesAddedFragment.newInstance(args.orderId)
        )

        binding.viewPager.adapter = FragmentAdapter(this, fragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Participants"
                1 -> "Expenses"
                else -> "Values Added"
            }
        }.attach()
    }

    private fun onAddValueAddedClick() {
        findNavController().navigate(
            OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddValueAddedFragment(
                args.orderId
            )
        )
    }

    private fun onAddExpenseClick() {
        findNavController().navigate(
            OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddExpenseFragment(
                args.orderId
            )
        )
    }

    private fun onAddParticipantClick() {
        AddOrderParticipantDialog(R.string.add_participant, R.string.hint_name) {
            sharedViewModel.addParticipant(it)
        }
            .show(parentFragmentManager, null)
    }
}