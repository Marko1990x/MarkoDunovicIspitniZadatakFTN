package firebaseapp.com.ispitmarkodunovic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.models.mainactivity.SearchAdapter;

public class ActivitySearchMovie extends AppCompatActivity {
    private String sentString;
    private String iWorkwithThisONe;

    private SearchAdapter adapter;
    private RecyclerView searchResult;

    public static String KEY2 = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        Intent intent = getIntent();
        sentString = intent.getStringExtra("movie");
        Toast.makeText(this, "Sent Ok: " + sentString, Toast.LENGTH_SHORT).show();

    }
}