package com.example.newstimes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {
    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList=articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article=articleList.get(position);
        holder.titletextview.setText(article.getTitle().toString());
        holder.sourcetextview.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.baseline_hide_image_24)
                .placeholder(R.drawable.baseline_hide_image_24)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(v.getContext(),NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    void updatedata(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titletextview,sourcetextview;
        ImageView imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titletextview=itemView.findViewById(R.id.title);
            sourcetextview=itemView.findViewById(R.id.source);
            imageView=itemView.findViewById(R.id.newsimage);

        }
    }
}
