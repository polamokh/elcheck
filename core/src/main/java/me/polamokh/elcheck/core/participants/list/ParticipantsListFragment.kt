package me.polamokh.elcheck.core.participants.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentParticipantsListBinding

@AndroidEntryPoint
class ParticipantsListFragment : Fragment() {

    private lateinit var binding: FragmentParticipantsListBinding

    private lateinit var viewModel: ParticipantsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ParticipantsListViewModel::class.java)

        val participantsListAdapter = ParticipantsListAdapter()
        binding.participantsRecyclerView.apply {
            adapter = participantsListAdapter
        }

        viewModel.participants.observe(viewLifecycleOwner) {
            participantsListAdapter.submitList(it)
        }

        binding.addParticipant.setOnClickListener {
            viewModel.addParticipant()
        }
    }

    companion object {
        fun newInstance(orderId: Long) =
            ParticipantsListFragment().apply {
                arguments = Bundle().apply {
                    putLong("orderId", orderId)
                }
            }
    }
}