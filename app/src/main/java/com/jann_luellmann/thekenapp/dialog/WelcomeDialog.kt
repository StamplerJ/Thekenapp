package com.jann_luellmann.thekenapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.databinding.DialogWelcomeBinding
import com.jann_luellmann.thekenapp.util.Util.getStatusBarHeight
import com.jann_luellmann.thekenapp.util.Util.isTablet

class WelcomeDialog : DialogFragment(R.layout.dialog_fragment_add_drink) {

    private lateinit var binding: DialogWelcomeBinding

    private var continueCounter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWelcomeBinding.inflate(layoutInflater)

        binding.continueButton.setOnClickListener {
            when (continueCounter) {
                0 -> {
                    binding.message.text = getString(R.string.create_event_message)
                    binding.continueButton.text = getString(R.string.create_event)

                    // Weird hack to keep the height - Deadline! TODO: Do this correctly
                    context?.let {
                        if (it.isTablet()) {
                            val height = binding.root.height
                            dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, height)
                        }
                    }
                }
                1 -> {
                    CreateEntryDialogFragment(Event()).show(
                        parentFragmentManager,
                        getString(R.string.event_tag)
                    )
                    dismiss()
                }
            }
            continueCounter++
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            if (showsDialog) {
                if (!it.isTablet()) {
                    val metrics = resources.displayMetrics
                    val width = metrics.widthPixels
                    val height = metrics.heightPixels - it.getStatusBarHeight()

                    dialog?.window?.setLayout(width, height)
                }
            }
        }
    }
}