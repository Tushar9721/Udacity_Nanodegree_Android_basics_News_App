package com.example.abcnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author tusha
 */

public class SettingNewsDataAdapter extends RecyclerView.Adapter<SettingNewsDataAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DataClassNews> newData = new ArrayList<>();

    public SettingNewsDataAdapter(Context context, ArrayList<DataClassNews> newData) {
        this.context = context;
        this.newData = newData;
    }

    @NonNull
    @Override
    public SettingNewsDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View newsView = layoutInflater.inflate(R.layout.news_recycler_view, parent, false);
        return new ViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingNewsDataAdapter.ViewHolder holder, final int position) {

        holder.AbcNewsTagLine.setText(newData.get(position).getAbcNewsHeading());
        holder.AbcNewsTypeName.setText(newData.get(position).getAbcNewsType());
        holder.AbcNewsDate.setText(newData.get(position).getAbcNewsDate());
        holder.AbcNewsTime.setText(newData.get(position).getAbcNewsTime());
        holder.AbcNewsAuthor.setText(newData.get(position).getAbcAuthorName());

        holder.cardViewParticularNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri openNewsInBrowser = Uri.parse(newData.get(position).getAbcNewsURL());
                Intent browser = new Intent(Intent.ACTION_VIEW, openNewsInBrowser);
                context.startActivity(browser);
            }
        });

    }


    @Override
    public int getItemCount() {
        return newData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView AbcNewsTagLine;
        TextView AbcNewsTypeName;
        TextView AbcNewsDate;
        CardView cardViewParticularNews;
        TextView AbcNewsAuthor;
        TextView AbcNewsTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            AbcNewsTagLine = itemView.findViewById(R.id.AbcNewsTagLine);
            AbcNewsTypeName = itemView.findViewById(R.id.AbcNewsTypeName);
            AbcNewsDate = itemView.findViewById(R.id.AbcNewsDate);
            cardViewParticularNews = itemView.findViewById(R.id.cardViewParticularNews);
            AbcNewsTime = itemView.findViewById(R.id.AbcNewsTime);
            AbcNewsAuthor = itemView.findViewById(R.id.AbcNewsAuthor);

        }
    }

}
