package in.bitbytetech.popularmoviesstage2.utility;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import in.bitbytetech.popularmoviesstage2.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vishalsaxena on 23/03/17.
 */

public class ApiUtility {

    public static final String TAG = "#ApiUtility: ";


    public static String movieDbEndPoint;
    public static String movieApiKey;
    public static String movieLanguage;
    public static String moviePhotoBaseUrl;
    public static String moviePhotoSizeUrl;


    public ApiUtility(String movieDbEndPoint, String movieApiKey, String movieLanguage, String moviePhotoBaseUrl, String moviePhotoSizeUrl) {
        this.movieDbEndPoint = movieDbEndPoint;
        this.movieApiKey = movieApiKey;
        this.movieLanguage = movieLanguage;
        this.moviePhotoBaseUrl = moviePhotoBaseUrl;
        this.moviePhotoSizeUrl = moviePhotoSizeUrl;
    }

    public static MovieDBEndpointInterface getMovieDbEndpointInterface() {
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDbUtility.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(MovieDBEndpointInterface.class);
    }

    public static class MovieDbUtility {

        private static final String API_BASE_URL = ApiUtility.movieDbEndPoint;
        public static final String API_KEY = ApiUtility.movieApiKey;

        public static final String APPEND_TO_RESPONSE_KEY = "append_to_response";
        public static final String REVIEWS_VIDEOS_PARAM = "reviews,videos";
        public static final String APPEND_TO_RESPONSE_QUERY = APPEND_TO_RESPONSE_KEY + "=" + REVIEWS_VIDEOS_PARAM;

        private static final String PHOTOS_BASE_URL = ApiUtility.moviePhotoBaseUrl;
        private static final String PHOTOS_SIZE_URL = ApiUtility.moviePhotoSizeUrl;

        /**
         * takes the poster URL provided by the API response and builds the entire valid URL
         */
        public static String getCompletePhotoUrl(String photoUrl) {
            String completeUrl = PHOTOS_BASE_URL +
                    PHOTOS_SIZE_URL +
                    photoUrl;
            return completeUrl;
        }
    }

}
