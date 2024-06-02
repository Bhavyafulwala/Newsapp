package com.example.newstimes;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Article> articleList=new ArrayList<>();
    NewsRecyclerAdapter newsRecyclerAdapter;
    LinearProgressIndicator progressIndicator;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.recyclerview);
        progressIndicator=findViewById(R.id.progressbar);
        searchView=findViewById(R.id.Searchnews);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getnews("GENERAL",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        setuprecyclerview();
        getnews("GENERAL",null);
    }

    void setuprecyclerview(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerAdapter=new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(newsRecyclerAdapter);
    }
    void showprogress(Boolean show){
        if(show){
            progressIndicator.setVisibility(View.VISIBLE);
        }else {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }
    void getnews(String category,String query){
        showprogress(true);
        NewsApiClient newsApiClient=new NewsApiClient("7bbaf948fdf2446b8d30f6b0c1bb783e");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback(){

                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(()->{
                            showprogress(false);
                            articleList=response.getArticles();
                            newsRecyclerAdapter.updatedata(articleList);
                            newsRecyclerAdapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Got Failure",throwable.getMessage());

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        Button btn=(Button) v;
        String category=btn.getText().toString();
        getnews(category,null);
    }

}