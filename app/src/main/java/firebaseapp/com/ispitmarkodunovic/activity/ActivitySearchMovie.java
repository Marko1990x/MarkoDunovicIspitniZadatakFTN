package firebaseapp.com.ispitmarkodunovic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.models.mainactivity.SearchAdapter;
import firebaseapp.com.ispitmarkodunovic.omdb.MyDividerItemDecoration;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiService;
import firebaseapp.com.ispitmarkodunovic.omdb.RecyclerTouchListener;
import firebaseapp.com.ispitmarkodunovic.omdb.model.OMDBResponse;
import firebaseapp.com.ispitmarkodunovic.omdb.model.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitySearchMovie extends AppCompatActivity {

    private String sentString;
    private SearchAdapter adapter;
    private RecyclerView searchResult;

    public static String KEY  = "KEY";

    private void callService(String query){
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "c66bf420");
        queryParams.put("s", query);

        Call<OMDBResponse> call = OMDBApiService.apiInterface().searchOMDB(queryParams);
        call.enqueue(new Callback<OMDBResponse>() {
            @Override
            public void onResponse(Call<OMDBResponse> call, Response<OMDBResponse> response) {
                if (response.code() == 200){
                    OMDBResponse resp = response.body();
                    if(resp != null){
                        adapter.addItems(resp.getSearch());
                    }
                }
            }

            @Override
            public void onFailure(Call<OMDBResponse> call, Throwable t) {
                Toast.makeText(ActivitySearchMovie.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_search_layout);

        Intent intent = getIntent();
        sentString = intent.getStringExtra("movie");
        Toast.makeText(this, "This is the sent string: " + sentString, Toast.LENGTH_SHORT).show();

        adapter = new SearchAdapter();

        searchResult = findViewById(R.id.searchResult);
        searchResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchResult.setItemAnimator(new DefaultItemAnimator());
        searchResult.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        searchResult.setAdapter(adapter);
        searchResult.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), searchResult, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Search movie = adapter.get(position);
                Intent i = new Intent(ActivitySearchMovie.this, SecondActivity.class);
                i.putExtra(KEY, movie.getImdbID());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final EditText query = findViewById(R.id.searchText);

        Button search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(query.getText().toString().trim());
            }
        });

    }
}