package firebaseapp.com.ispitmarkodunovic.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.models.secondactivity.CustomRecyclerAdapterDetails;
import firebaseapp.com.ispitmarkodunovic.models.secondactivity.Model_Item1_Details;

public class SecondActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView2;
    private CustomRecyclerAdapterDetails mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;

    private int position;
    private int defaultPosition = 0;
    private String mText1; // this is fine now it basicly send the text 1 and text 2 from activity 1 for further use
    private String mText2;
    private static final String TAG = "Sent Text 1 ";

    // dont forget to declare a global variable for array of items
    private ArrayList<Model_Item1_Details> myDetailsList;


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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_delete_button:
                Toast.makeText(this, "You Clicked The Yeet Button", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.toolbar_add_button:
                Toast.makeText(this, "You Clicked the Add Button", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.dotted_line_1_item:
                Toast.makeText(this, "You Clicked The Dotted 1 Item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dotted_line_2_item:
                Toast.makeText(this, "You Clicked The Dotted 2 Item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dotted_line_3_item:
                Toast.makeText(this, "You Clicked The Dotted 3 Item", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.sub_toolbar_item1:
                Toast.makeText(this, "You Clicked The Sub 1 Item", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sub_toolbar_item2:
                Toast.makeText(this, "You Clicked The Sub 5 Item", Toast.LENGTH_SHORT).show();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    //endregion implements custom toolbar with listeners

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        getExtras(); // gets the passed vallues from act 1

        createInitList(); // sets the data

        buildRecyclerView(); // sets the adapter

        // im being real smart now and decided to use a recycler adapter here just for 1 item.
        // genious i know haha.
    }



    public void createInitList(){
        myDetailsList = new ArrayList<>();
        myDetailsList.add(new Model_Item1_Details(R.drawable.ic_emoji,mText1,mText2,"Lorem Ipsum"));
        //myDetailsList.add(new Model_Item1_Details(R.drawable.ic_emoji,mText1,mText2,"Lorem Ipsum 2"));
        // yup works just fine my idea was correct
    }

    public void buildRecyclerView(){
       /* mRecyclerView = findViewById(R.id.recyclerViewForDetails);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CustomAdapterForRecycler(myDetailsList); // data for the adapter
        mRecyclerView.setLayoutManager(mLayoutManager);//pass the adapter to the manager
        mRecyclerView.setAdapter(mAdapter);*/
        mRecyclerView2 = findViewById(R.id.recyclerViewForDetails);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager2 = new LinearLayoutManager(this);
        mAdapter2 = new CustomRecyclerAdapterDetails(myDetailsList);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setAdapter(mAdapter2);
    }


    public void getExtras(){
        position = getIntent().getIntExtra("position", defaultPosition);
        mText1 = getIntent().getStringExtra("text1");
        mText2 = getIntent().getStringExtra("text2");
        Toast.makeText(this, "position "+position, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "sent data: " +mText1 +" "+ mText2, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "getExtras: " + mText1 + " " + mText2);

    }
}
