package com.example.onboarding.demo

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.onboarding.R
import kotlinx.android.synthetic.main.fragment_onboarding_step.*

private const val ARG_TITLE_RES = "onboarding:title_res"
private const val ARG_MESSAGE_RES = "onboarding:message_res"
private const val ARG_IMAGE_RES = "onboarding:image_res"

class DemoOnBoardingStepFragment : Fragment() {

    private val ctxt: Context by lazy { requireContext() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_step, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            val title = ctxt.getString(it.getInt(ARG_TITLE_RES))
            val message =  ctxt.getString(it.getInt(ARG_MESSAGE_RES))
            val image = ContextCompat.getDrawable( ctxt,  it.getInt(ARG_IMAGE_RES))
            setupUI(title, message, image)
        }
    }

    private fun setupUI(title: String?, message: String?, image: Drawable?) {
        onboarding_title.text = title
        onboarding_message.text = message
        onboarding_image.setImageDrawable(image)
    }

    companion object {
        @JvmStatic
        fun newInstance(screen: DemoOnBoardingScreen) =
            DemoOnBoardingStepFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TITLE_RES, screen.titleRes)
                    putInt(ARG_MESSAGE_RES, screen.messageRes)
                    putInt(ARG_IMAGE_RES, screen.imageRes)
                }
            }
    }
}