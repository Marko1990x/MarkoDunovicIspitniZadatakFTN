package firebaseapp.com.ispitmarkodunovic.activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.api.JsonPlaceHolderApi;
import firebaseapp.com.ispitmarkodunovic.api.Post;
import firebaseapp.com.ispitmarkodunovic.dialogs.DialogAboutMeToolbar;
import firebaseapp.com.ispitmarkodunovic.dialogs.DialogAddToolBarButton;
import firebaseapp.com.ispitmarkodunovic.dialogs.DialogApiSearch;
import firebaseapp.com.ispitmarkodunovic.dialogs.DialogCustom1ToolBar;
import firebaseapp.com.ispitmarkodunovic.dialogs.SplashScreenDialog;
import firebaseapp.com.ispitmarkodunovic.models.mainactivity.CustomAdapterForRecycler;
import firebaseapp.com.ispitmarkodunovic.models.mainactivity.Item1;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiEndpoint;
import firebaseapp.com.ispitmarkodunovic.omdb.OMDBApiService;
import firebaseapp.com.ispitmarkodunovic.omdb.model.OMDBResponse;
import firebaseapp.com.ispitmarkodunovic.omdb.model.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static firebaseapp.com.ispitmarkodunovic.App.CHANNEL_1_ID;
import static firebaseapp.com.ispitmarkodunovic.App.CHANNEL_2_ID;

public class MwainActivity extends AppCompatActivity implements DialogCustom1ToolBar.dialogPositionListener, DialogAddToolBarButton.dialogPositionListener2, SplashScreenDialog.dialogPositionListener3, DialogApiSearch.dialogPositionListener4 {
    private static final String CHANNEL_ID = "Splash Screen Notification";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "keyShared";
    private RecyclerView mRecyclerView;
    // IMPORTANT --- i should use my custom adapter class here for recycler not the default one
    // because the default one doesnt have my listener in it !!!!
    private CustomAdapterForRecycler mAdapter; // this is needed because it doesnt load all the files at once for performance
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item1> myItem1List;
    private static final String TAG = "Test Position";
    private String changedString1;
    private boolean isChangedString = false;
    public static String KEY2 ="KEY";

    private TextView textViewResult;

    private MediaPlayer player;
    private int count = 0;

    private DrawerLayout drawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private Button searchApiButton;
    private Button favoritesButton;
    private Button splashScreenBSettings;
    private Button deleteButtonAll;

    private FirebaseDatabase rootNode;
    private DatabaseReference referenceText1;
    private DatabaseReference referenceText2;

    private NotificationManagerCompat notificationManager;

    //region implements custom toolbar with listeners

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // i already dissabled the default toolbar so nothing else is needed it works fine
        // except to add listeners
        // add this to all activities that are created
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_custom_toolbar, menu);
        return true;
    }

    @Override
    public void applyPosition(String position) {
        //Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        // beautifull it works fine now i can use the position to delete by it my array list items
        int positionx = Integer.parseInt(position); // so i can compare the numbers of array size and sent position
        if (positionx > myItem1List.size()) {
            Toast.makeText(this, "You Sent A Larger Number Then The Array No Cookie For You" + " " + positionx, Toast.LENGTH_SHORT).show();
        } else if (positionx <= myItem1List.size()) {  // todo check this latter there is a small error here
            myItem1List.remove(positionx);
            mAdapter.notifyItemRemoved(positionx); // so the adapter actually removes it from the view
        }
    }

    @Override
    public void addToList(String position) {
        //Toast.makeText(this, "Yey", Toast.LENGTH_SHORT).show();
        // yeah i can reuse the same basic shape of the dialog however make it a different class name and
        // change the interface name so when its implemented the main class will ask for a new interface
        // to be implemented and its all good from there :)
        int positionx2 = Integer.parseInt(position);
        if (positionx2 > myItem1List.size()) {
            Toast.makeText(this, "Number Larger Then Array No Cookie For You" + " " + position, Toast.LENGTH_SHORT).show();
        }
        if (positionx2 <= myItem1List.size()) {
            myItem1List.add(positionx2, new Item1(R.drawable.ic_hourglass, "Added", "With Toolbar Add Method"));
            mAdapter.notifyItemInserted(positionx2);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // this part is for the drawer

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            //Toast.makeText(this, "Open/Closed", Toast.LENGTH_SHORT).show();
            switch (item.getItemId()) {
                case R.id.contact:
                    openAboutMeDialog();
                    Toast.makeText(MwainActivity.this, "toast", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.home2:
                    Toast.makeText(MwainActivity.this, "home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.homeSetting:
                    Toast.makeText(MwainActivity.this, "home setting", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.first:
                    Toast.makeText(MwainActivity.this, "first", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return true;
        }

        // ends here

        // switch is for the dialogs from toolbar

        switch (item.getItemId()) {

            case R.id.toolbar_delete_button:
                Toast.makeText(this, "You Clicked The Yeet Button", Toast.LENGTH_SHORT).show();
                openDialogToolBarDelete();
                // toolbar dialog position i want to return a number so i know where in the list to
                // delete
                // dialog basicly has a xml file that is the look of it and a java class that is the model
                return true;

            case R.id.toolbar_add_button:
                Toast.makeText(this, "You Clicked the Add Button", Toast.LENGTH_SHORT).show();
                openDialogToolBarUpdate();
                return true;

            case R.id.dotted_line_1_item:
                Toast.makeText(this, "You Clicked The Dotted 1 Item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dotted_line_2_item:
                Toast.makeText(this, "You Clicked The Dotted 2 Item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dotted_line_3_item:
                Toast.makeText(this, "Music Sub Meny Clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.sub_toolbar_item1:
                Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
                playSong();
                return true;
            case R.id.sub_toolbar_item2:
                Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
                killSong();
                return true;
            case R.id.about_toolbar_bwutton:
                Toast.makeText(this, "You Clicked the About Button", Toast.LENGTH_SHORT).show();
                openAboutMeDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void openDialogToolBarDelete() {
        // initalize the dialog class
        DialogCustom1ToolBar dialogCustom1ToolBar = new DialogCustom1ToolBar();
        dialogCustom1ToolBar.setCancelable(false);
        dialogCustom1ToolBar.show(getSupportFragmentManager(), "Delete from list by position");
    }

    private void openSearchApi(){

    }

    private void openDialogToolBarUpdate() {
        DialogAddToolBarButton addToolBarButton = new DialogAddToolBarButton();
        addToolBarButton.setCancelable(false);
        addToolBarButton.show(getSupportFragmentManager(), "Add to list by possition");
    }



    private void openAboutMeDialog() {
        DialogAboutMeToolbar dialogAboutMeToolbar = new DialogAboutMeToolbar();
        dialogAboutMeToolbar.setCancelable(false);
        dialogAboutMeToolbar.show(getSupportFragmentManager(), "Show About me info");
    }

    private void openSplashScreenDialog() {
        SplashScreenDialog splashScreenDialog = new SplashScreenDialog();
        splashScreenDialog.setCancelable(false);
        splashScreenDialog.show(getSupportFragmentManager(), "splash screen settings");
    }


    //endregion implements custom toolbar with listeners

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        notificationManager = NotificationManagerCompat.from(this);

        createInitList();
        buildRecyclerView(); // recycler 1
        setUpDrawer();
        setUpNavMenu(); // sadly i cant figure the error here i tried 2 times with different layouts and the code should be fine
        // but i cant get the listeners to function at all in navigation drawer
        setupListeners();
        loadDataSharedPrefs();
        // reminder custom toolbar is oncreateoptionsmenu overide

        testRestrofit();

        database();

    }

    private void database() {
        //test
        rootNode = FirebaseDatabase.getInstance();
        referenceText1 = rootNode.getReference("First_Data");

       // Item1 item1 = new Item1(111,"asdsd", "adsada");
         // if i pass the position to this whacky things are possible
        referenceText1.setValue("item1");
        referenceText1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
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
                        //adapter.addItems(resp.getSearch());
                        Log.d(TAG, "onResponse: "+resp.getSearch());
                        Toast.makeText(MwainActivity.this, ""+resp, Toast.LENGTH_SHORT).show();
                        // adapter treba da se preradi ili doda drugi
                    }
                }
            }

            @Override
            public void onFailure(Call<OMDBResponse> call, Throwable t) {
                Toast.makeText(MwainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }

    private void Retrofit2(String query){
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("apikey", "c66bf420");
        queryParams.put("s", query);
        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDBApiEndpoint omdbApiEndpoint = retrofit.create(OMDBApiEndpoint.class);
        Call<OMDBResponse> call = OMDBApiService.apiInterface().searchOMDB(queryParams);
        call.enqueue(new Callback<OMDBResponse>() {
            @Override
            public void onResponse(Call<OMDBResponse> call, Response<OMDBResponse> response) {
                textViewResult.setText("Code: " + response.code());
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

            }

            @Override
            public void onFailure(Call<OMDBResponse> call, Throwable t) {

            }
        });

    }

    private void testRestrofit() {
        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // bind intercafe to retrofit
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                // yey
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID" + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // nay
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private void loadDataSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int mySavedValue = sharedPreferences.getInt(KEY, 1);
        Toast.makeText(this, "my saved splash screen time  = " + mySavedValue, Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        searchApiButton = findViewById(R.id.searchApiButton);
        searchApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MwainActivity.this, "From Api", Toast.LENGTH_SHORT).show();
                DialogApiSearch dialogApiSearch = new DialogApiSearch();
                dialogApiSearch.setCancelable(false);
                dialogApiSearch.show(getSupportFragmentManager(), "splash screen settings");

            }
        });
        favoritesButton = findViewById(R.id.favoritesButtonAA);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MwainActivity.this, "From Fav", Toast.LENGTH_SHORT).show();
            }
        });
        splashScreenBSettings = findViewById(R.id.splash_screen_settings_new);
        splashScreenBSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MwainActivity.this, "from splash settings", Toast.LENGTH_SHORT).show();
                openSplashScreenDialog();
            }
        });
        deleteButtonAll = findViewById(R.id.deleteAllButtonAA);  // despite the name it deletes only the item 1 current list
        deleteButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myItem1List.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(MwainActivity.this, "List Cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void splashScreen(String position) {
        int my_time = Integer.parseInt(position);
        Toast.makeText(this, "sent " + my_time, Toast.LENGTH_SHORT).show();

        String title = "Splash Screen";
        String message = "Sent: " + my_time;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

        saveData(my_time);
    }

    @Override
    public void searchApi(String position) {
        String search = position;
        Toast.makeText(this, "Search: "+search, Toast.LENGTH_SHORT).show();
        //todo implement the api search here // search is the passed value from the user
        //myItem1List.clear();
        //mAdapter.notifyDataSetChanged();
        // exapand api here
        callService(search.trim());


    }



    private void saveData(int mytime) {
        Toast.makeText(this, "from save data: " + mytime, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, mytime);
        editor.commit();
        loadData();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int mySavedValue = sharedPreferences.getInt(KEY, 1);
        Toast.makeText(this, "my saved value = " + mySavedValue, Toast.LENGTH_SHORT).show();
    }

    private void setUpNavMenu() {
        drawerLayout = findViewById(R.id.DrawerLayout);
        mNavigationView = findViewById(R.id.Navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact:
                        openAboutMeDialog();
                        Toast.makeText(MwainActivity.this, "toast", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.home2:
                        Toast.makeText(MwainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.homeSetting:
                        Toast.makeText(MwainActivity.this, "home setting", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.first:
                        Toast.makeText(MwainActivity.this, "first", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }


        });
    }


    public void changeItem(int position, String text) {

        // this is a check in case someone click the field outside the arrow and changed the text of field 1
        // so i save it before i change it then i pass that to stringExtra

        isChangedString = true;
        if (isChangedString == true) {
            changedString1 = myItem1List.get(position).getmText1(); // saved here
            // ----------------------------------------------------------------------
            //myItem1List.get(position).changeText1(text);
            //mAdapter.notifyItemChanged(position);
            // killed this method because it causes issues with sending the text to other activity when its clicked
            Toast.makeText(this, "CLick The Arrow " + "⤜(⚆ᗜ⚆)⤏", Toast.LENGTH_SHORT).show();
            // not really sure why but this needs to be inside the if statement
            // should work fine outside
        }

    }

    public void yeetItem(int position) {
        myItem1List.remove(position);
        mAdapter.notifyItemRemoved(position); // so the list updates
    }

    public void newIntent(int position) {
        Intent intent = new Intent(MwainActivity.this, SecondActivity.class);
        // kljuc vrednost ne zaboravi
        // da ovde je bila greska
        // to get the actual value and not blasted null i have to send position on my array of items and extract
        // the data i want
        intent.putExtra("position", position);
        //todo figure a way to fix this ;(
        String text1 = myItem1List.get(position).getmText1(); // i just copy the string in case someone clicks where he shoudn't
        String text2 = myItem1List.get(position).getmText2();
        intent.putExtra("text1", text1);
        intent.putExtra("text2", text2);
        startActivity(intent);
    }

    public void createInitList() {
        myItem1List = new ArrayList<>();
        myItem1List.add(new Item1(R.drawable.ic_android, "My Line 1", "My Line 1"));
        myItem1List.add(new Item1(R.drawable.ic_audio, "My Line 2", "My Line 2"));
        myItem1List.add(new Item1(R.drawable.ic_sunny, "My Line 3", "My Line 3"));
    }


    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        Context context;
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CustomAdapterForRecycler(myItem1List); // data for the adapter
        mRecyclerView.setLayoutManager(mLayoutManager);//pass the adapter to the manager
        mRecyclerView.setAdapter(mAdapter);

        //listener for recycler adapter
        // EVERY INTERFACE METHOD NEEDS TO BE IMPLEMENTED
        mAdapter.setOnItemClickListener(new CustomAdapterForRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // finaly
                // make the methods for the data of the model in the model class
                changeItem(position, "Click The Arrow :)");
                // mAdapter.notifyItemChanged(position); // dont forget this or the list wont udpate
                //moved notify to method but it doesnt mather where its called if its after the operation is done
            }

            @Override
            public void onItemYetted(int position) {
                yeetItem(position);
                Log.d(TAG, "onItemYetted: item deleted with possition: " + position); // mkay
            }

            @Override  // pokrece drugu aktivnost (zapravo trecu ali ko ce da se buni hahaha)
            public void onNewIntent(int position) {
                newIntent(position);
            }
        });
    }

    //region implements music
    public void playSong() {
        if (count == 1) {
            player.stop();
            player.release();
            count = 0;
            player = MediaPlayer.create(getApplicationContext(), R.raw.judas_priest_painkiller);
            player.start();
            count = 1;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.stop();
                    player.release();
                    player = null;
                    player = MediaPlayer.create(getApplicationContext(), R.raw.judas_priest_painkiller);
                    // Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
                    //player.release();
                    // Toast.makeText(MainActivity.this, "Music Released", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            player = MediaPlayer.create(getApplicationContext(), R.raw.judas_priest_painkiller);
            player.start();
            count = 1;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.stop();
                    player.release();
                    player = MediaPlayer.create(getApplicationContext(), R.raw.judas_priest_painkiller);
                    //Toast.makeText(MainActivity.this, "stopped", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void killSong() {
        if (count == 1) {
            player.stop();
            player.release();
            count = 0;
        }

    }

    //endregion implements music

    private void setUpDrawer() {
        drawerLayout = findViewById(R.id.DrawerLayout); // instance of my xml file
        // pass it to drawer toggle class
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void sendOnChannel1(View v) {

        // can call it from splash screen

        String title = "my message";
        String message = "my message 2";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {

        String title = "my message";
        String message = "my message 2";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);

    }



}
