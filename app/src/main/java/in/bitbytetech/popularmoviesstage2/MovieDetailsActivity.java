package in.bitbytetech.popularmoviesstage2;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    float voteAverageFloat = 7.7f;

    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;

    TextView voteAverage;
    TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbarLayout);
        toolbarLayout.setTitle("Logan");

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        backdrop = (ImageView) findViewById(R.id.backdrop);
        /*title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        voteAverage = (TextView) findViewById(R.id.vote_average);
        releaseDate = (TextView) findViewById(R.id.release_date);

        title.setText("Logan - Movie Title");
        description.setText("Logan - Movie Description goes here....");
        voteAverage.setText(getResources().getString(R.string.average_vote) + (Float.toString(voteAverageFloat)) + "/10.0");
        releaseDate.setText(getResources().getString(R.string.releaste_date) + "2017-03-01");

        Picasso.with(this)
                .load(R.drawable.logan_poster)
                .into(poster);*/
        Picasso.with(this)
                .load(R.drawable.logan_backdrop)
                .into(backdrop);
    }
}
