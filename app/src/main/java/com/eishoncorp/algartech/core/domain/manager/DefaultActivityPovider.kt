package com.eishoncorp.algartech.core.domain.manager

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.eishoncorp.algartech.core.domain.manager.ActivityProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultActivityProvider @Inject constructor() : ActivityProvider, DefaultLifecycleObserver {

    private var currentActivity: ComponentActivity? = null

    override fun getActivity(): ComponentActivity? {
        return currentActivity
    }

    override fun onCreate(owner: LifecycleOwner) {
        if (owner is ComponentActivity) {
            currentActivity = owner
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (owner == currentActivity) {
            currentActivity = null
        }
    }
}
