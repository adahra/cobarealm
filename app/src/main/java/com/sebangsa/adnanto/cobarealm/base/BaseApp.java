package com.sebangsa.adnanto.cobarealm.base;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by adnanto on 9/6/16.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(BaseApp.this)
                .schemaVersion(0)
                .migration(new DataMigration())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema realmSchema = realm.getSchema();

            if (oldVersion == 0) {
                realmSchema.create("Article")
                        .addField("id", int.class)
                        .addField("title", String.class)
                        .addField("description", String.class);
                oldVersion++;
            }
        }
    }
}
