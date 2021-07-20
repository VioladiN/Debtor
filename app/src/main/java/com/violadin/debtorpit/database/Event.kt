package com.violadin.debtorpit.database

data class Event<out T>(val status: Status, val data: T?, val error: Error?) {

    companion object {
        fun <T> loading(): Event<T> =
            Event(Status.LOADING, null, null)
        fun <T> success(data: T?): Event<T> =
            Event(Status.SUCCESS, data, null)
        fun <T> error(error: Error?): Event<T> =
            Event(Status.ERROR, null, error)
    }

}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}