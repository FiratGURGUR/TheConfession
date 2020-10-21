package frt.gurgur.theconfession.util;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;

public class ErrorUtils {


    public static ErrorParser showError(Throwable t){
        ErrorParser errorParser = null;
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<ErrorParser> adapter = gson.getAdapter
                    (ErrorParser
                            .class);
            try {
               errorParser  = adapter.fromJson(body.string());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return errorParser;
    }

}