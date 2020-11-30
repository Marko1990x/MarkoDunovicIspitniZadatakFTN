package firebaseapp.com.ispitmarkodunovic.models.mainactivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.omdb.model.Search;

public class CustomAdapterForRecycler extends RecyclerView.Adapter<CustomAdapterForRecycler.myViewHolder> {
    private ArrayList<Item1> mItemsList; // data that adapter will use
    private OnItemClickListener mListener;

    // interface for the blasted listener
    public interface OnItemClickListener{
        void onItemClick(int position); // interface expects position
        void onItemYetted(int position); // the delete button
        void onNewIntent(int position);
    }

    // constructor for the listener
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
        public TextView mTextView2;
        public ImageView mYeetButton; // add to constructor
        public ImageView mIntentButton;

        //modified here for listener blargh
        public myViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            // bind to xml fields
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textViewt2);
            mYeetButton = itemView.findViewById(R.id.yeet);
            mIntentButton = itemView.findViewById(R.id.new_activity_button);
            // set listener here
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            // can have multiple here  for example if one part has a delete button
            mYeetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // every new listener needs a seperate interface method if it does stuff diferently
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemYetted(position);
                        }
                    }
                }
            });
            mIntentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onNewIntent(position);
                        }
                    }
                }
            });
        }
    }

    public CustomAdapterForRecycler(ArrayList<Item1> itemsList){
        mItemsList = itemsList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card,parent,false);
        myViewHolder mvh = new myViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        Item1 currentItem = mItemsList.get(position); // gets the current item in a list
        myViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        myViewHolder.mTextView.setText(currentItem.getmText1());
        myViewHolder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size(); // size of the array that fills the data to the adapter
    }



}
