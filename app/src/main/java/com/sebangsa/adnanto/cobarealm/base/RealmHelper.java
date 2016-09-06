package com.sebangsa.adnanto.cobarealm.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sebangsa.adnanto.cobarealm.R;
import com.sebangsa.adnanto.cobarealm.model.Article;
import com.sebangsa.adnanto.cobarealm.model.ArticleModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by adnanto on 9/6/16.
 */
public class RealmHelper {
    private static final String TAG = RealmHelper.class.getSimpleName();

    private Realm realm;
    private RealmResults<Article> articleRealmResults;
    private Context context;

    public RealmHelper(Context context) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .build();
        realm = Realm.getInstance(realmConfiguration);
        this.context = context;
    }

    public void addArticle(String title, String description) {
        Article article = new Article();
        article.setId((int) (System.currentTimeMillis() / 1000));
        article.setTitle(title);
        article.setDescription(description);

        realm.beginTransaction();
        realm.copyToRealm(article);
        realm.commitTransaction();

        showLog("Added : " + title);
        showToast(title + " berhasil disimpan.");
    }

    public ArrayList<ArticleModel> findAllArticle() {
        ArrayList<ArticleModel> articleModels = new ArrayList<>();

        articleRealmResults = realm.where(Article.class).findAll();
        articleRealmResults.sort("id", Sort.DESCENDING);

        if (articleRealmResults.size() > 0) {
            showLog("Size : " + articleRealmResults.size());

            for (int i = 0; i < articleRealmResults.size(); i++) {
                String title;
                String description;
                int id = articleRealmResults.get(i).getId();
                title = articleRealmResults.get(i).getTitle();
                description = articleRealmResults.get(i).getDescription();
                articleModels.add(new ArticleModel(id, title, description));
            }
        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return articleModels;
    }

    public void updateArticle(int id, String title, String description) {
        realm.beginTransaction();
        Article article = realm.where(Article.class).equalTo("id", id).findFirst();
        article.setTitle(title);
        article.setDescription(description);
        realm.commitTransaction();

        showLog("Update : " + title);
        showToast(title + " berhasil diupdate.");
    }

    public void deleteData(int id) {
        RealmResults<Article> articleRealmResults = realm.where(Article.class)
                .equalTo("id", id).findAll();

        realm.beginTransaction();
        articleRealmResults.remove(0);
        // articleRealmResults.removeLast();
        articleRealmResults.clear();
        articleRealmResults.deleteFromRealm(id);
        realm.commitTransaction();
    }

    private void showLog(String string) {
        Log.d(TAG, string);
    }

    private void showToast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }
}
