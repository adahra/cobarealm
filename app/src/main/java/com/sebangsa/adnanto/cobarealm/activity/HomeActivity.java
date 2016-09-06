package com.sebangsa.adnanto.cobarealm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.sebangsa.adnanto.cobarealm.R;
import com.sebangsa.adnanto.cobarealm.adapter.AdapterArticle;
import com.sebangsa.adnanto.cobarealm.base.RealmHelper;
import com.sebangsa.adnanto.cobarealm.model.ArticleModel;

import java.util.ArrayList;

/**
 * Created by adnanto on 9/6/16.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = HomeActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private RealmHelper realmHelper;
    private ArrayList<ArticleModel> articleModelArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate() started");
        setContentView(R.layout.base_app_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Stetho.initializeWithDefaults(HomeActivity.this);

        articleModelArrayList = new ArrayList<>();
        realmHelper = new RealmHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_article);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        setRecyclerView();
    }

    public void setRecyclerView() {
        try {
            articleModelArrayList = realmHelper.findAllArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdapterArticle adapterArticle = new AdapterArticle(articleModelArrayList,
                new AdapterArticle.OnItemClickListener() {
                    @Override
                    public void onClick(ArticleModel articleModel) {
                        Intent intent = new Intent(HomeActivity.this, EditActivity.class);
                        intent.putExtra("id", articleModel.getDescription());
                        intent.putExtra("title", articleModel.getTitle());
                        intent.putExtra("description", articleModel.getDescription());
                        startActivity(intent);
                        finish();
                    }
                });

        recyclerView.setAdapter(adapterArticle);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(HomeActivity.this, TambahActivity.class));
        finish();
    }
}
