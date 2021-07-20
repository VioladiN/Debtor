package com.violadin.debtorpit.database

import java.lang.Error

class ResponseWrapper<T> {
    val data: T? = null
    val error: Error? = null
}