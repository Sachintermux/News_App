package com.sna.newsapp.adapters;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sna.newsapp.NewsTab_DataModel;
import com.sna.newsapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NewTabs_RecyclerViewAdapter extends RecyclerView.Adapter<NewTabs_RecyclerViewAdapter.viewHolder> {
    ArrayList<NewsTab_DataModel> arrayList = new ArrayList<>();
    public NewTabs_RecyclerViewAdapter(ArrayList<NewsTab_DataModel> arrayList) {
this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NewTabs_RecyclerViewAdapter.viewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_tabs_recyclerview_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull NewTabs_RecyclerViewAdapter.viewHolder holder, int position ) {

        holder.title.setText(arrayList.get(position).getTitle());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.time.setText(arrayList.get(position).getTime());
        if(arrayList.get(position).getBitmap() != null) holder.imageView.setImageBitmap(arrayList.get(position).getBitmap());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView time,title,description;
        ImageView imageView;
        public viewHolder( @NonNull View itemView ) {
            super(itemView);
            time = itemView.findViewById(R.id.timeAndPublisherTxtView_newsTabRecV);
            title = itemView.findViewById(R.id.titleTxtView_newsTabRecV);
            description = itemView.findViewById(R.id.descriptionTxtView_newsTabRecV);
            imageView = itemView.findViewById(R.id.imageView_newsTabsRecV);
        }
    }
}
