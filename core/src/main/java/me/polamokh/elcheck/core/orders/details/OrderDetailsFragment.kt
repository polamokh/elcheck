package me.polamokh.elcheck.core.orders.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import me.polamokh.elcheck.core.databinding.FragmentOrderDetailsBinding
import me.polamokh.elcheck.core.expenses.ExpensesFragment
import me.polamokh.elcheck.core.participants.ParticipantsFragment

class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding

    private val args: OrderDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf<Fragment>(
            ParticipantsFragment.newInstance(args.orderId),
            ExpensesFragment.newInstance(args.orderId)
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
}