package frt.gurgur.theconfession.util;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorUtils {


    public static APIError showError(Throwable t){
        APIError apiError = null;
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<APIError> adapter = gson.getAdapter
                    (APIError
                            .class);
            try {
                apiError = adapter.fromJson(body.string());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return apiError;
    }

}