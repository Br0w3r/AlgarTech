package com.eishoncorp.algartech.core.domain.usecases.AppEntry

import com.eishoncorp.algartech.core.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManger: LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}