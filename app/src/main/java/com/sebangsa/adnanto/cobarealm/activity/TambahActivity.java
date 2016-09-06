package com.sebangsa.adnanto.cobarealm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sebangsa.adnanto.cobarealm.R;
import com.sebangsa.adnanto.cobarealm.base.RealmHelper;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TambahActivity.class.getSimpleName();

    private RealmHelper realmHelper;
    private EditText etInputDescription;
    private EditText etInputTitle;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        realmHelper = new RealmHelper(this);

        etInputTitle = (EditText) findViewById(R.id.et_input_title);
        etInputDescription = (EditText) findViewById(R.id.et_input_description);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSave.getId()) {
            String title;
            String description;

            title = etInputTitle.getText().toString();
            description = etInputDescription.getText().toString();

            realmHelper.addArticle(title, description);
            finish();
            Intent intent = new Intent(TambahActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
