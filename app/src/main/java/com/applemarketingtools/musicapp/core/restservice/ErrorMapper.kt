package com.applemarketingtools.musicapp.core.restservice

import com.applemarketingtools.musicapp.core.mapper.Mapper
import org.json.JSONObject
import retrofit2.HttpException

object ErrorMapper : Mapper<Throwable, String?> {

    private const val FIRST_ERRORS_KEY = "first_errors"

    override fun map(s: Throwable): String {
        return mapError(s)
    }

    private fun mapError(exception: Throwable): String {

        return if (exception is HttpException) {
            exception.response()?.errorBody()?.string()?.let { errorBody ->
                val errorResponse = JSONObject(errorBody)
                val firstErrors = errorResponse.getJSONObject(FIRST_ERRORS_KEY)
                val firstErrorsKeys = errorResponse.getJSONObject(FIRST_ERRORS_KEY).keys()
                val messageBuilder = StringBuilder()
                firstErrorsKeys.forEach { key ->
                    messageBuilder.append(firstErrors.getString(key))
                }
                messageBuilder.toString()
            } ?: kotlin.run {
                "Something went wrong"
            }
        } else {
            "Something went wrong"
        }
    }
}