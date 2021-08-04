package me.polamokh.elcheck.core.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentParticipantsBinding

@AndroidEntryPoint
class ParticipantsFragment : Fragment() {

    private lateinit var binding: FragmentParticipantsBinding

    private val viewModel: ParticipantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val participantsAdapter = ParticipantsAdapter()
        binding.participantsRecyclerView.apply {
            adapter = participantsAdapter
        }

        viewModel.participants.observe(viewLifecycleOwner) {
            viewModel.getParticipantExpenses()
        }

        viewModel.expenses.observe(viewLifecycleOwner) {
            viewModel.getParticipantExpenses()
        }

        viewModel.participantExpenses.observe(viewLifecycleOwner) {
            participantsAdapter.submitList(it)
        }

        binding.addParticipant.setOnClickListener {
            viewModel.addParticipant()
        }
    }

    companion object {
        fun newInstance(orderId: Long) =
            ParticipantsFragment().apply {
                arguments = Bundle().apply {
                    putLong("orderId", orderId)
                }
            }
    }
}