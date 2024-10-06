package com.team930.allianceselectionapp.presentation

import com.team930.allianceselectionapp.core.util.Resources

fun <T> Resources<T>.toUnitResource(): Resources<Unit> {
    return when (this) {
        is Resources.Success -> Resources.Success(Unit)
        is Resources.Error -> Resources.Error(this.message ?: "An error occurred")
        is Resources.Loading -> Resources.Loading()
    }
}