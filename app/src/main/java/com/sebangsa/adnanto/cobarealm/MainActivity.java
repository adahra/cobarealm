package com.sebangsa.adnanto.cobarealm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.sebangsa.adnanto.cobarealm.model.Cat;
import com.sebangsa.adnanto.cobarealm.model.Dog;
import com.sebangsa.adnanto.cobarealm.model.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout linearLayout = null;
    private Realm realm;
    private RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(MainActivity.this);
        linearLayout = (LinearLayout) findViewById(R.id.container);
        linearLayout.removeAllViews();

        realmConfiguration = new RealmConfiguration.Builder(MainActivity.this).build();
        realm = Realm.getInstance(realmConfiguration);

        basicCRUD(realm);
        basicQuery(realm);
        basicLinkgQuery(realm);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String info = complexReadWrite();
                info = info + complexQuery();
                return info;
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showStatus(String string) {
        Log.i(TAG, string);
        TextView textView = new TextView(MainActivity.this);
        textView.setText(string);
        linearLayout.addView(textView);
    }

    private String complexQuery() {
        String status = "\n\nPerforming complex Query operation...";

        Realm realm = Realm.getInstance(realmConfiguration);
        status = status + "\nNumber of persons: " + realm.where(Person.class).count();

        RealmResults<Person> results = realm.where(Person.class).between("age", 7, 9)
                .beginsWith("name", "Person").findAll();
        status = status + "\nSize of result set: " + results.size();

        realm.close();
        return status;
    }

    private String complexReadWrite() {
        String status = "\nPerforming complex Read/Write operation...";

        Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog fidoDog = realm.createObject(Dog.class);
                fidoDog.setName("fido");
                for (int i = 0; i < 10; i++) {
                    Person person = realm.createObject(Person.class);
                    person.setId(i);
                    person.setName("Person no. " + i);
                    person.setAge(i);
                    person.setDog(fidoDog);
                    person.setTempReference(42);
                    for (int j = 0; j < i; j++) {
                        Cat cat = realm.createObject(Cat.class);
                        cat.setName("Cat_" + j);
                        person.getCats().add(cat);
                    }
                }
            }
        });

        status = status + "\nNumber of persons: " + realm.where(Person.class).count();
        for (Person personPerson : realm.where(Person.class).findAll()) {
            String dogName;
            if (personPerson.getDog() == null) {
                dogName = "None";
            } else {
                dogName = personPerson.getDog().getName();
            }

            status = status + "\n" + personPerson.getName() + ":" + personPerson.getAge() + " : "
                    + dogName + personPerson.getCats().size();
        }

        RealmResults<Person> sortedPerson = realm.where(Person.class)
                .findAllSorted("age", Sort.DESCENDING);
        status = status + "\nSorting " + sortedPerson.last().getName() + " == "
                + realm.where(Person.class).findFirst().getName();

        realm.close();
        return status;
    }

    private void basicLinkgQuery(Realm realm) {
        showStatus("\nPerforming basic Link Query operation...");
        showStatus("Number of persons: " + realm.where(Person.class).count());

        RealmResults<Person> realmResultPerson = realm.where(Person.class)
                .equalTo("cats.name", "Tiger").findAll();

        showStatus("Size of result set: " + realmResultPerson.size());

    }

    private void basicQuery(Realm realm) {
        showStatus("\nPerforming basic Query operation...");
        showStatus("Number of persons: " + realm.where(Person.class).count());

        RealmResults<Person> realmResultPersons = realm.where(Person.class).equalTo("age", 99)
                .findAll();

        showStatus("Size of result set: " + realmResultPersons.size());
    }

    private void basicCRUD(Realm realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        showStatus(person.getName() + ":" + person.getAge());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("Senior Person");
                person.setAge(99);
                showStatus(person.getName() + " got older: " + person.getAge());
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }
}
