package com.karta.api;

import android.content.Context;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ErrorAPIConverter {
    public static ErrorResponse getItemErrorBody(
            ResponseBody errorBody,
            Context context) {
        ErrorResponse errorResponse;
        try {
            Converter<ResponseBody, ErrorResponse> errorConverter = ApiClient.getClient(context)
                    .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
            errorResponse = errorConverter.convert(errorBody);
        } catch (Exception e) {
            errorResponse = null;
        }
        return errorResponse;
    }
}
