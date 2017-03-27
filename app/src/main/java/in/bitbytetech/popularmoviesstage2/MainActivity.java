package in.bitbytetech.popularmoviesstage2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import in.bitbytetech.popularmoviesstage2.adapter.MovieAdapter;
import in.bitbytetech.popularmoviesstage2.model.Movie;
import in.bitbytetech.popularmoviesstage2.model.MoviesInfo;
import in.bitbytetech.popularmoviesstage2.utility.ApiUtility;
import in.bitbytetech.popularmoviesstage2.utility.MovieDBEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "#PopularMovies: ";

    private String tmdb_end_point;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessage;

    private String apiKey;
    private String language;
    private String posterEndPoint;
    private String posterEndPointSize;

    //private Retrofit retrofit;

    private ApiUtility apiUtility;

    private MovieDBEndpointInterface movieDBEndpointInterface;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tmdb_end_point = getResources().getString(R.string.moviedb_endpoint);

        apiKey = getResources().getString(R.string.moviedb_api_key);

        language = getResources().getString(R.string.moviedb_language);

        posterEndPoint = getResources().getString(R.string.moviedb_poster_endpoint);

        posterEndPointSize = getResources().getString(R.string.moviedb_poster_size);

        mErrorMessage = (TextView) findViewById(R.id.error_message);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }*/



        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        /*retrofit = new Retrofit.Builder()
                .baseUrl(tmdb_end_point)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDBEndpointInterface = retrofit.create(MovieDBEndpointInterface.class);*/

        ApiUtility.setMovieDbApiValues(tmdb_end_point, apiKey, language, posterEndPoint, posterEndPointSize);

        movieDBEndpointInterface = ApiUtility.MovieDbUtility.getMovieDbEndpointInterface();

        fetchPopularMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mStaggeredGridLayoutManager.setSpanCount(2);
        } else {
            mStaggeredGridLayoutManager.setSpanCount(4);
        }
    }

    private void fetchPopularMovies() {
        Call<MoviesInfo> popularMovies = movieDBEndpointInterface.getPopularMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTopRatedMovies() {
        Call<MoviesInfo> popularMovies = movieDBEndpointInterface.getTopRatedMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchFavoritesMovies() {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        this.startActivity(intent);
    }

    private void updateMovieAdapter(List<Movie> movies) {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        movieAdapter.setMovieList(movies);
    }

    private void showErrorMessage() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_popular_movies) {
            return true;
        }*/

        switch (id) {
            case R.id.action_popular_movies: {
                fetchPopularMovies();
                break;
            }
            case R.id.action_toprated_movies: {
                fetchTopRatedMovies();
                break;
            }
            case R.id.action_fav_movies: {
                fetchFavoritesMovies();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
