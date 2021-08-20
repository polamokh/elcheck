package me.polamokh.elcheck.ui.orders.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentOrderDetailsBinding
import me.polamokh.elcheck.ui.AddOrderParticipantDialog
import me.polamokh.elcheck.ui.SharedViewModel
import me.polamokh.elcheck.ui.expenses.ExpensesFragment
import me.polamokh.elcheck.ui.participants.ParticipantsFragment
import me.polamokh.elcheck.ui.valuesadded.ValuesAddedFragment
import java.util.*

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding

    private val args: OrderDetailsFragmentArgs by navArgs()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        sharedViewModel.orderTotalExpensesWithVA.observe(viewLifecycleOwner) {
            binding.orderTotalExpenses.text = getString(
                R.string.order_total_price_format,
                Currency.getInstance(Locale.getDefault()).symbol,
                it ?: 0.0
            )
        }

        binding.addParticipant.setOnClickListener {
            AddOrderParticipantDialog(R.string.add_participant, R.string.name_hint) {
                sharedViewModel.addParticipant(it)
            }
                .show(parentFragmentManager, null)
        }
        binding.addExpense.setOnClickListener {
            findNavController().navigate(
                OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddExpenseFragment(
                    args.orderId
                )
            )
        }
        binding.addValueAdded.setOnClickListener {
            findNavController().navigate(
                OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddValueAddedFragment(
                    args.orderId
                )
            )
        }
    }
}