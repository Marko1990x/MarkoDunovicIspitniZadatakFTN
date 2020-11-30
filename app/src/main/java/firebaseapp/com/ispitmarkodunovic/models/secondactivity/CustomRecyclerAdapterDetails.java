package firebaseapp.com.ispitmarkodunovic.models.secondactivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import firebaseapp.com.ispitmarkodunovic.R;

public class CustomRecyclerAdapterDetails extends RecyclerView.Adapter<CustomRecyclerAdapterDetails.myViewHolder> {

    private ArrayList<Model_Item1_Details> listDetails;

    public CustomRecyclerAdapterDetails(ArrayList<Model_Item1_Details> listDetails) {
        this.listDetails = listDetails;
    }

    @NonNull
    @Override  // this changes based on if its generic or custom adapter in RecyclerView.adapter<>
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_second_activity_details,parent,false);
        myViewHolder mvh = new myViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        Model_Item1_Details currentItem = listDetails.get(position); // gets the current object of the type of my custom class
        // and i set the data on fields
        myViewHolder.mImageDetails.setImageResource(currentItem.getmImageDetails());
        myViewHolder.mDetailsDate1.setText(currentItem.getmTheBlastedDate1());
        myViewHolder.mDetailsDate2.setText(currentItem.getmTheBlastedDate2());
        myViewHolder.mDetailsFull.setText(currentItem.getmTheFullDescription());
        
    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageDetails;  // my image
        public TextView mDetailsDate1;  // for dates
        public TextView mDetailsDate2;
        public TextView mDetailsFull;  // for details full

        public myViewHolder(@NonNull View itemView) {  // a bit confusing not gonna lie
            super(itemView);
            mImageDetails = itemView.findViewById(R.id.imageView_SecondActivity);
            mDetailsDate1 = itemView.findViewById(R.id.textView_second_date1);
            mDetailsDate2 = itemView.findViewById(R.id.textView_second_date2);
            mDetailsFull = itemView.findViewById(R.id.textView_fullDescription);
        }
    }


}
