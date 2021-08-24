package me.polamokh.elcheck.ui.expenses.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentAddExpenseBinding
import me.polamokh.elcheck.model.ExpenseParticipant
import me.polamokh.elcheck.ui.BaseFragment
import me.polamokh.elcheck.utils.showSoftKeyboard

@AndroidEntryPoint
class AddExpenseFragment : BaseFragment<FragmentAddExpenseBinding>() {

    private val viewModel: AddExpenseViewModel by viewModels()

    private lateinit var participantsAdapter: ExpenseParticipantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        participantsAdapter =
            ExpenseParticipantsAdapter(requireContext()) { isChecked, expenseParticipant ->
                viewModel.recalculateParticipantExpenses(isChecked, expenseParticipant)
            }

        binding.expenseAmountText.showSoftKeyboard()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.expenseParticipantsRecyclerView.apply {
            setHasFixedSize(true)
            this.adapter = participantsAdapter
        }

        binding.saveExpense.isEnabled = isSaveExpenseEnabled(null, null)

        binding.saveExpense.setOnClickListener {
            onSaveExpenseClick()
        }

        binding.expenseAmountText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && s.toString().toDoubleOrNull() != null)
                    viewModel.setExpenseAmount(s.toString().toDouble())
                else viewModel.setExpenseAmount(0.0)
                binding.saveExpense.isEnabled =
                    isSaveExpenseEnabled(viewModel.expenseParticipants.value, s)
            }
        })

        binding.shareWith.setOnCheckedChangeListener { _, isChecked ->
            viewModel.recalculateParticipantExpenses(isChecked)
        }

        viewModel.expenseParticipants.observe(viewLifecycleOwner) {
            participantsAdapter.submitList(it)
        }

        viewModel.onNotifyItemChanged.observe(viewLifecycleOwner) { position ->
            if (position != -1) {
                participantsAdapter.notifyItemChanged(position)
                viewModel.onNotifyItemChangedComplete()
            }

            binding.saveExpense.isEnabled =
                isSaveExpenseEnabled(
                    viewModel.expenseParticipants.value,
                    binding.expenseAmountText.text
                )
        }
    }

    private fun onSaveExpenseClick() {
        viewModel.saveExpense(binding.expenseNameText.text.toString())
        findNavController().navigateUp()
    }

    private fun isSaveExpenseEnabled(
        participants: List<*>?,
        expenseAmount: CharSequence?
    ): Boolean {
        if (participants == null)
            return false
        if (expenseAmount == null)
            return false
        return participants.any { participant ->
            (participant as ExpenseParticipant).isChecked
        } && !expenseAmount.isNullOrEmpty()
    }
}