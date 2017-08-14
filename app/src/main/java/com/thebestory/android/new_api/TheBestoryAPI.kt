package com.thebestory.android.new_api

import com.thebestory.android.model.Account
import com.thebestory.android.model.AuthModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by oktai on 07.08.17.
 */

interface NoTokenTheBestoryAPI {

    @POST("/sessions")
    fun postAuth(@Body body : HashMap<String, String>): Single<AuthModel>

    @POST("/users")
    fun postRegister(@Body body : HashMap<String, String>): Single<Account>


}