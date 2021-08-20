package me.polamokh.elcheck.ui.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentParticipantsBinding
import me.polamokh.elcheck.ui.SharedViewModel

@AndroidEntryPoint
class ParticipantsFragment : Fragment() {

    private lateinit var binding: FragmentParticipantsBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        val participantsAdapter = ParticipantsAdapter(requireContext()) {
            viewModel.deleteParticipant(it.participant)
        }
        binding.participantsRecyclerView.apply {
            adapter = participantsAdapter
        }

        viewModel.participantWithTotalExpensesList.observe(viewLifecycleOwner) {
            participantsAdapter.submitList(it)
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