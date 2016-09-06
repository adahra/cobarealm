package com.sebangsa.adnanto.cobarealm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sebangsa.adnanto.cobarealm.R;
import com.sebangsa.adnanto.cobarealm.base.RealmHelper;
import com.sebangsa.adnanto.cobarealm.model.ArticleModel;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private int position;
    private Button btnDelete;
    private Button btnSave;
    private EditText etInputTitle;
    private EditText etInputDescription;
    private RealmHelper realmHelper;
    private String title;
    private String description;
    private String intentTitle;
    private String intentDescription;
    private ArrayList<ArticleModel> articleModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        realmHelper = new RealmHelper(this);
        articleModelArrayList = new ArrayList<>();
        position = getIntent().getIntExtra("id", 0);
        intentTitle = getIntent().getStringExtra("title");
        intentDescription = getIntent().getStringExtra("description");

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        btnDelete.setVisibility(View.VISIBLE);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        etInputTitle = (EditText) findViewById(R.id.et_input_title);
        etInputTitle.setText(intentTitle);
        etInputDescription = (EditText) findViewById(R.id.et_input_description);
        etInputDescription.setText(intentDescription);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnDelete.getId()) {
            realmHelper.deleteData(position);

            startActivity(new Intent(EditActivity.this, HomeActivity.class));
            finish();
        } else if (v.getId() == btnSave.getId()) {
            title = etInputTitle.getText().toString();
            description = etInputDescription.getText().toString();

            realmHelper.updateArticle(position, title, description);

            startActivity(new Intent(EditActivity.this, HomeActivity.class));
            finish();
        }
    }
}
