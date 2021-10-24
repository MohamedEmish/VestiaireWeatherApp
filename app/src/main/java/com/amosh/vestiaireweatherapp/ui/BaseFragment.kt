package com.amosh.vestiaireweatherapp.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.amosh.vestiaireweatherapp.R
import com.amosh.vestiaireweatherapp.models.appModels.AppMessage
import com.andrognito.flashbar.Flashbar

abstract class BaseFragment : Fragment() {

    var fragmentContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentContainer = container
        return getLayoutRoot(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterCreation(savedInstanceState)
    }

    abstract fun getLayoutRoot(inflater: LayoutInflater): View

    abstract fun onBindingDestroy()

    protected abstract fun afterCreation(bundle: Bundle?)

    fun showMessage(message: AppMessage) {
        if (isVisible)
            Flashbar.Builder(requireActivity())
                .gravity(Flashbar.Gravity.TOP)
                .duration(3000L)
                .backgroundColorRes(message.getMessageColor())
                .messageColorRes(R.color.white)
                .messageTypeface(Typeface.DEFAULT)
                .message(message.message)
                .build()
                .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        onBindingDestroy()
    }
}