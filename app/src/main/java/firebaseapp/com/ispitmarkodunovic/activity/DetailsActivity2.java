package firebaseapp.com.ispitmarkodunovic.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.HashMap;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiService;
import firebaseapp.com.ispitmarkodunovic.omdb.model.MovieInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity2 extends AppCompatActivity {

    private void getDetail(String imdbKey) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "c66bf420");
        queryParams.put("i", imdbKey);

        Log.d("REZ", imdbKey);

        Call<MovieInfo> call = OMDBApiService.apiInterface().getMovieData(queryParams);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "200");

                    MovieInfo resp = response.body();
                    if (resp != null) {
                        TextView title = DetailsActivity2.this.findViewById(R.id.dtitle2);
                        title.setText(resp.getTitle());

                        TextView genre = DetailsActivity2.this.findViewById(R.id.dgenre2);
                        genre.setText(resp.getDirector());

                        TextView rated = DetailsActivity2.this.findViewById(R.id.drated2);
                        rated.setText(resp.getWriter());

                        TextView plotOfTheMovie = DetailsActivity2.this.findViewById(R.id.plotOfTheMovie2);
                        plotOfTheMovie.setText(resp.getActors());

                        TextView godinaNastanka = DetailsActivity2.this.findViewById(R.id.godinaNastanka2);
                        godinaNastanka.setText("1/12/2020");



                        ImageView poster = DetailsActivity2.this.findViewById(R.id.dposter2);
                        Picasso.get().load(resp.getPoster()).into(poster);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Toast.makeText(DetailsActivity2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String imdbKey = getIntent().getStringExtra(ActivitySearchMovie.KEY);
        getDetail(imdbKey);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_correct2);
    }

}
