package firebaseapp.com.ispitmarkodunovic.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiService;
import firebaseapp.com.ispitmarkodunovic.omdb.model.MovieInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

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
                        TextView title = DetailsActivity.this.findViewById(R.id.dtitle);
                        title.setText(resp.getTitle());

                        TextView genre = DetailsActivity.this.findViewById(R.id.dgenre);
                        genre.setText(resp.getGenre());

                        TextView rated = DetailsActivity.this.findViewById(R.id.drated);
                        rated.setText(resp.getRated());

                        ImageView poster = DetailsActivity.this.findViewById(R.id.dposter);
                        Picasso.get().load(resp.getPoster()).into(poster);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.details_activity_correct);
    }

}
