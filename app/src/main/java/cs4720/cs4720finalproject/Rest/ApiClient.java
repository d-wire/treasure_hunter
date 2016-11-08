package cs4720.cs4720finalproject.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public class ApiClient {

    //The base URL to get quiz questions from
    public static final String BASE_URL = "https://opentdb.com/";
    // Create a new retrofit object to connect to URL
    private static Retrofit retrofit = null;

    // Built in method to retrofit to connect to URL
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
