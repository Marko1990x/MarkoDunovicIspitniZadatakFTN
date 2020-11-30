package firebaseapp.com.ispitmarkodunovic.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.activity.MwainActivity;

import static firebaseapp.com.ispitmarkodunovic.activity.MwainActivity.SHARED_PREFS;

public class SplashScreen extends AppCompatActivity {
    private int time = 0;
    public static final String KEY = "keyShared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        // kotlin
        // supportActionBar?.hide()
        //java
        if (getSupportActionBar()!= null){
            getSupportActionBar().hide();
        }

        loadDataSharedPrefs();


    }

    private void loadDataSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int mySavedValue = sharedPreferences.getInt(KEY, 1);
        Toast.makeText(this, "Splash Screen Time = " + mySavedValue+ " Seconds", Toast.LENGTH_SHORT).show();
        int transformed = mySavedValue*1000;
        Toast.makeText(this, ""+transformed, Toast.LENGTH_SHORT).show();
        // this is basicly a delay button
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent();
                finish();
            }
        },transformed);
    }

    // start my actual main activity
    private void intent(){
        Intent intent = new Intent(this, MwainActivity.class);
        startActivity(intent);
    }
}