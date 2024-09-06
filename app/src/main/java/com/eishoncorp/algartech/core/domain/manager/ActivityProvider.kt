// core/domain/manager/ActivityProvider.kt
package com.eishoncorp.algartech.core.domain.manager

import androidx.activity.ComponentActivity

interface ActivityProvider {
    fun getActivity(): ComponentActivity?
}