package com.enti.app_companion

import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

object HeaderUtils {

    // WeakReference : Ensures that the button can be garbage-collected
    private var activeButtonRef: WeakReference<Button>? = null

    fun setupHeader(activity: AppCompatActivity, buttonToActivityMap: Map<Button, Class<*>>, defaultActiveButton: Button) {
        activeButtonRef = WeakReference(defaultActiveButton)

        updateButtonSizes(buttonToActivityMap)

        buttonToActivityMap.forEach { (button, targetActivity) ->
            button.setOnClickListener {
                activeButtonRef = WeakReference(button)
                updateButtonSizes(buttonToActivityMap)

                val intent = Intent(activity, targetActivity)
                activity.startActivity(intent)
            }
        }
    }

    private fun updateButtonSizes(buttonToActivityMap: Map<Button, Class<*>>) {
        val activeButton = activeButtonRef?.get()
        buttonToActivityMap.keys.forEach { button ->
            if (button == activeButton) {
                button.layoutParams = (button.layoutParams as ViewGroup.LayoutParams).apply {
                    width = button.context.resources.getDimension(R.dimen.button_size_large).toInt()
                    height = button.context.resources.getDimension(R.dimen.button_size_large).toInt()
                }
            } else {
                button.layoutParams = (button.layoutParams as ViewGroup.LayoutParams).apply {
                    width = button.context.resources.getDimension(R.dimen.button_size_small).toInt()
                    height = button.context.resources.getDimension(R.dimen.button_size_small).toInt()
                }
            }
        }
    }
}
