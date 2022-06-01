package com.sna.newsapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sna.newsapp.adapters.NewTabs_RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private MainActivity_ViewModel viewModel;
    private RecyclerView recyclerView;
private ProgressBar progressBar;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        observers();
    }

    private void observers() {
        viewModel.newsArrayListMutableLiveData.observe(MainActivity.this, data -> {
            if (data.size() != 0)
                setDataToRecyclerView(data);

        });

    }

    public void setDataToRecyclerView( ArrayList<NewsTab_DataModel> data ) {
        progressBar.setVisibility(View.GONE);
        NewTabs_RecyclerViewAdapter adapter = new NewTabs_RecyclerViewAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView_mainActivity);
        progressBar = findViewById(R.id.progressBar_mainActivity);
        viewModel = new ViewModelProvider(MainActivity.this).get(MainActivity_ViewModel.class);
        getDataFromInternet();
    }

    private void getDataFromInternet() {
        URL url;
        ArrayList<NewsTab_DataModel> newsTab_dataModelsList = new ArrayList<>();
        try {
            url = new URL("https://newsapi.org/v2/top-headlines?country=in&apiKey=7d10b108d4134e6aa0f2678af525d599");
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure( @NonNull Call call, @NonNull IOException e ) {

                }

                @Override
                public void onResponse( @NonNull Call call, @NonNull Response response ) throws IOException {
                    try {
                        JSONObject Jobject = new JSONObject(response.body().string());
                        JSONArray jsonArray = Jobject.getJSONArray("articles");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String title = jsonArray.getJSONObject(i).getString("title");
                            String description = jsonArray.getJSONObject(i).getString("description");
                            String time = jsonArray.getJSONObject(i).getString("publishedAt");
                            String imageUrl = jsonArray.getJSONObject(i).getString("urlToImage");
                            String sourceName = jsonArray.getJSONObject(i).getJSONObject("source").getString("name");
                            NewsTab_DataModel newsTab_dataModel = new NewsTab_DataModel(time + " " + sourceName, title, description, imageUrl);
                            if(imageUrl.length()!=0) {
                                URL url1 = new URL(imageUrl);
                                InputStream inputStream = (InputStream) url1.getContent();
                                newsTab_dataModel.setBitmap(BitmapFactory.decodeStream(inputStream));
                            }
                            newsTab_dataModelsList.add(newsTab_dataModel);
                        }
                        viewModel.setNewsArrayListMutableLiveData(newsTab_dataModelsList);
//                        }
                    } catch (JSONException e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }
}