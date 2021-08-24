package me.polamokh.elcheck.ui.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentParticipantsBinding
import me.polamokh.elcheck.model.ParticipantWithTotalExpenses
import me.polamokh.elcheck.ui.BaseFragment

@AndroidEntryPoint
class ParticipantsFragment : BaseFragment<FragmentParticipantsBinding>() {

    private val viewModel: ParticipantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        val participantsAdapter = ParticipantsAdapter(requireContext()) {
            viewModel.deleteParticipant(it.participant)
        }
        binding.participantsRecyclerView.apply {
            setHasFixedSize(true)
            adapter = participantsAdapter
        }

        viewModel.participantWithTotalExpensesList.observe(viewLifecycleOwner) {
            participantsAdapter.submitList(it)
            handleEmptyList(it)
        }
    }

    private fun handleEmptyList(it: List<ParticipantWithTotalExpenses>) {
        binding.emptyLayout.emptyMsg.isVisible = it.isEmpty()
        if (it.isEmpty()) {
            binding.emptyLayout.emptyMsg.text = getString(
                R.string.format_empty_list,
                getString(R.string.participants).lowercase(),
                getString(R.string.participant).lowercase()
            )
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