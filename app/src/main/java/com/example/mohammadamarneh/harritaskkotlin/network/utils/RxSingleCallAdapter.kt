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

package com.example.mohammadamarneh.harritaskkotlin.network.utils


import com.example.mohammadamarneh.harritaskkotlin.network.ApiResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a Single of ApiResponse.
 * @param <R>
</R> */
class RxSingleCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, Single<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Single<ApiResponse<R>> =
            Single.just(1)
                    .subscribeOn(Schedulers.io())
                    .map { ApiResponse.create(call.execute()) }

}
