package firebaseapp.com.ispitmarkodunovic.omdb;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import firebaseapp.com.ispitmarkodunovic.R;
import firebaseapp.com.ispitmarkodunovic.omdb.model.Search;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<Search> moviesList;

    public SearchAdapter() {
        this.moviesList = new ArrayList<>();
    }

    public void addItems(List<Search> data){
        moviesList.clear();
        moviesList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Search movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.get().load(moviesList.get(position).getPoster()).into(holder.poster);
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public Search get(int position) {
        return moviesList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;
        public ImageView poster;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            poster = (ImageView) view.findViewById(R.id.poster);
            year = (TextView) view.findViewById(R.id.year);
        }
    }
}
