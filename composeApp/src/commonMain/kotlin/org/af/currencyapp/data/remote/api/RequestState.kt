package org.af.currencyapp.data.remote.api

sealed class RequestState<out T> {

    data object Loading : RequestState<Nothing>()
    data object Idle : RequestState<Nothing>()
    data class Error(val message: String) : RequestState<Nothing>()
    data class Success<out T>(val data: T) : RequestState<T>()

    fun isIdle():Boolean = this is Idle
    fun isLoading():Boolean = this is Loading
    fun isError():Boolean = this is Error
    fun isSuccess():Boolean = this is Success

    fun getIdle() = this as Idle
    fun getSuccessData() = (this as Success).data
    fun getError() = this as Error
    fun getLoading() = this as Loading

}