/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mohammadamarneh.harritaskkotlin.repo.utils

import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mohammadamarneh.harritaskkotlin.network.ApiEmptyResponse
import com.example.mohammadamarneh.harritaskkotlin.network.ApiErrorResponse
import com.example.mohammadamarneh.harritaskkotlin.network.ApiResponse
import com.example.mohammadamarneh.harritaskkotlin.network.ApiSuccessResponse
import com.example.mohammadamarneh.harritaskkotlin.ui.utils.Resource
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * A generic class that adapts a LiveData<Resource<T>> from Single<ApiResponse<T>>
 */

abstract class NetworkResourceAdapter<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        fetchFromNetwork()
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun processSuccessResponses(response : ApiSuccessResponse<RequestType>) : ResultType = response.body as ResultType

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        apiResponse
                .subscribeOn(Schedulers.io())
                .subscribe(object : DisposableSingleObserver<ApiResponse<RequestType>>(){
                    override fun onSuccess(response: ApiResponse<RequestType>) {
                        when (response) {
                            is ApiSuccessResponse -> {
                                Single.just(processSuccessResponses(response))
                                        .subscribe(object : DisposableSingleObserver<ResultType>(){
                                            override fun onError(e: Throwable) {
                                                setValue(Resource.error(e.message ?: "not known error", null))
                                            }

                                            override fun onSuccess(t: ResultType) {
                                                setValue(Resource.success(t))
                                            }

                                        })
                            }
                            is ApiEmptyResponse -> {
                                setValue(Resource.success(null))
                            }
                            is ApiErrorResponse -> {
                                onFetchFailed()
                                setValue(Resource.error(response.errorMessage, null))
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        setValue(Resource.error(e.message ?: "not known error", null))
                    }

                })
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun createCall(): Single<ApiResponse<RequestType>>
}
