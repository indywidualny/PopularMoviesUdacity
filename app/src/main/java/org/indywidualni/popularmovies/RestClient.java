package org.indywidualni.popularmovies;

import org.indywidualni.popularmovies.model.Movies;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RestClient {

    // TODO: API key goes here
    public static final String API_KEY   = "#############################";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static volatile ApiEndpointInterface apiInterface;
    private static final Object LOCK = new Object();

    private RestClient() {}

    public static ApiEndpointInterface getClient() {
        if (apiInterface == null) {
            synchronized (LOCK) {
                if (apiInterface == null) {
                    Retrofit client = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    apiInterface = client.create(ApiEndpointInterface.class);
                }
            }
        }
        return apiInterface;
    }

    public interface ApiEndpointInterface {

        @GET("movie/popular")
        Call<Movies> getPopularMovies(@Query("api_key") String key);

        @GET("movie/top_rated")
        Call<Movies> getTopRatedMovies(@Query("api_key") String key);

    }

}