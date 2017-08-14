package com.thebestory.android.new_api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.thebestory.android.model.Account
import com.thebestory.android.model.AuthModel
import java.lang.reflect.Type

/**
 * Created by oktai on 07.08.17.
 */

class AuthModelDeserializer : JsonDeserializer<AuthModel> {
    override fun deserialize(json: JsonElement?,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): AuthModel {
        val authModel = AuthModel()
        val authJsonObject = json?.asJsonObject
        authModel.jwt = authJsonObject?.get("data")?.asJsonObject?.get("jwt")?.asString
        val userJsonElement : JsonElement? = authJsonObject?.get("data")?.asJsonObject?.get("user")
        authModel.user = userJsonElement?.asJsonObject?.parseToAccount()
        return authModel
    }
}

fun JsonObject.parseToAccount() : Account {
    return Account(this.get("id").asInt,
                   this.get("username").asString,
                   this.get("story_likes_count").asInt,
                   this.get("stories_count").asInt)
}

