package me.polamokh.elcheck.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import me.polamokh.elcheck.R
import me.polamokh.elcheck.utils.showSoftKeyboard

class AddOrderParticipantDialog(
    private val title: Int,
    private val hint: Int,
    private val onPositiveButtonClickListener: (text: String) -> Unit
) : DialogFragment() {

    private lateinit var inputText: TextInputLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_name, null)

            inputText = view.findViewById<TextInputLayout>(R.id.input_text).apply {
                setHint(this@AddOrderParticipantDialog.hint)
            }

            inputText.editText?.apply {
                addTextChangedListener { text ->
                    invalidatePositiveButton(text)
                }
            }

            builder
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.save) { _, _ ->
                    onPositiveButtonClickListener(inputText.editText?.text.toString())
                }
                .setNeutralButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()

        invalidatePositiveButton(null)

        inputText.editText?.post {
            inputText.editText?.showSoftKeyboard()
        }
    }

    private fun invalidatePositiveButton(text: Editable?) {
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            .isEnabled = !TextUtils.isEmpty(text)
    }
}