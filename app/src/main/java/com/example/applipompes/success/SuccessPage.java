package com.example.applipompes.success;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applipompes.Accueil;
import com.example.applipompes.Configuration;
import com.example.applipompes.R;
import com.example.applipompes.sauvegarde.Data;
import com.example.applipompes.sauvegarde.SaveAndLoad;

import java.util.ArrayList;

public class SuccessPage extends AppCompatActivity {

    private Data data;

    /* Données */

    private int nbPompesFaitesTotal;

    private String difficulte;
    private int ratio;

    private int hour;
    private int minutes;

    private boolean notification;

    private ArrayList<Success> listSuccess;

    /* Views and others */

    private ListView listSuccessView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.accueil:
                intent = new Intent(this, Accueil.class);
                startActivity(intent);
                return true;
            case R.id.parametres:
                intent = new Intent(this, Configuration.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        setContentView(R.layout.listview_success);

        loadData();

        listSuccess = Success.getSuccess();

        listSuccessView = findViewById(R.id.listeSucces);
        for (Success success : listSuccess) {
            if (nbPompesFaitesTotal < success.getNbPompesRequis()) {
                success.setColor(Color.GRAY);
            }
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), listSuccess);
        listSuccessView.setAdapter(customAdapter);
    }

    public void loadData() {
        data = SaveAndLoad.loadData(this);
        majInfosfromData();
        majAffichageInfos();
    }

    public void majInfosfromData() {
        nbPompesFaitesTotal = data.getNbPompesFaitesTotal();
        difficulte = data.getDifficulte();
        ratio = data.getRatio();
        hour = data.getHour();
        minutes = data.getMinutes();
        notification = data.getNotification();
    }

    public void majAffichageInfos() {

    }
}