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
import firebaseapp.com.ispitmarkodunovic.dialogs.DialogApiSearch;
import firebaseapp.com.ispitmarkodunovic.models.mainactivity.SearchAdapter;
import firebaseapp.com.ispitmarkodunovic.omdb.MyDividerItemDecoration;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiService;
import firebaseapp.com.ispitmarkodunovic.omdb.RecyclerTouchListener;
import firebaseapp.com.ispitmarkodunovic.omdb.model.OMDBResponse;
import firebaseapp.com.ispitmarkodunovic.omdb.model.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitySearchMovie extends AppCompatActivity implements DialogApiSearch.dialogPositionListener4 {

    private String sentString;
    private SearchAdapter adapter;
    private RecyclerView searchResult;
    private Intent intent;

    public static String KEY = "KEY";

    private void callService(String query) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "c66bf420");
        queryParams.put("s", query);

        Call<OMDBResponse> call = OMDBApiService.apiInterface().searchOMDB(queryParams);
        call.enqueue(new Callback<OMDBResponse>() {
            @Override
            public void onResponse(Call<OMDBResponse> call, Response<OMDBResponse> response) {
                if (response.code() == 200) {
                    OMDBResponse resp = response.body();
                    if (resp != null) {
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
        String sessionId = getIntent().getStringExtra("movie");
        //Log.d("THIS IS THE ONE", "onCreate: " + sessionId);
        //Toast.makeText(this, "it works now ?"+sessionId, Toast.LENGTH_SHORT).show();
        makeBatmanMethod();
        //sentString = intent.getStringExtra("movie");
        //  Toast.makeText(this, "This is the correct one: "+sentString, Toast.LENGTH_LONG).show();
    }

    private void makeBatmanMethod() {
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
                Intent i = new Intent(ActivitySearchMovie.this, DetailsActivity.class);
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
                DialogApiSearch dialogApiSearch = new DialogApiSearch();
                dialogApiSearch.setCancelable(false);
                dialogApiSearch.show(getSupportFragmentManager(), "splash screen settings");
                //callService(query.getText().toString().trim());
            }
        });

    }

    @Override
    public void searchApi(String position) {
        String search = position;
        callService(search);
    }
}