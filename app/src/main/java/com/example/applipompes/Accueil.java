package com.example.applipompes;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applipompes.notifications.NotificationReceiver;
import com.example.applipompes.sauvegarde.Data;
import com.example.applipompes.sauvegarde.SaveAndLoad;
import com.example.applipompes.success.SuccessPage;
import com.example.applipompes.success.ThreadNotificationsSucces;

import java.util.Calendar;
import java.util.Date;

public class Accueil extends AppCompatActivity {

    private Data data;

    /* Données */

    private int nbPompesFaitesTotal;

    private String difficulte;
    private int ratio;

    private int hour;
    private int minutes;

    private boolean notification;

    /* Views and others */

    private String txt_infos_journalieres;
    private TextView txtInfosJournalieres;

    private EditText editTextNbPompesfaites;

    private String txt_infos_pompes_manquantes_annee;
    private TextView txtInfosPompesManquantesAnnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        repeatNotification();

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
            case R.id.parametres:
                intent = new Intent(this, Configuration.class);
                startActivity(intent);
                return true;
            case R.id.succes:
                intent = new Intent(this, SuccessPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clicValider(View view) {
        boolean ok = true;

        int nbPompesEnPlus = 0;
        try {
            nbPompesEnPlus = Integer.parseInt(editTextNbPompesfaites.getText().toString());
            nbPompesFaitesTotal += nbPompesEnPlus;
            if (nbPompesEnPlus < 5) {
                Toast.makeText(getApplicationContext(), "C'est tout ?", Toast.LENGTH_SHORT).show();
            } else if (nbPompesEnPlus < 50) {
                Toast.makeText(getApplicationContext(), "Bien joué !", Toast.LENGTH_SHORT).show();
            } else if (nbPompesEnPlus < 500) {
                Toast.makeText(getApplicationContext(), "T'es monstrueux !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Je te crois pas !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // si autre chose qu'un entier a été entré
            Toast.makeText(getApplicationContext(), "Saisie incorrecte", Toast.LENGTH_SHORT).show();
            ok = false;
        }

        if (ok) {
            /* on fait apparaitre une notification si un succes a été atteint */
            Thread threadNotificationsSucces = new ThreadNotificationsSucces(this, nbPompesFaitesTotal, nbPompesEnPlus);
            threadNotificationsSucces.run();
            editTextNbPompesfaites.setText("");
            saveData();
        }
    }

    public void showExplication(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.explication_challenge);
        builder.setCancelable(true);
        builder.setTitle("Explication du Challenge");
        builder.setMessage(getString(R.string.txt_explication_challenge));
        builder.setPositiveButton("OK", (dialog, which) -> { /* a la validation on fait rien */ });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void init() {

        setContentView(R.layout.accueil);

        txtInfosJournalieres = findViewById(R.id.txtInfosJournalieres);
        editTextNbPompesfaites = findViewById(R.id.editTextNbPompesFaites);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editTextNbPompesfaites.setFocusedByDefault(true);
        }

        txt_infos_pompes_manquantes_annee = getString(R.string.txt_infos_pompes_manquantes_annee);
        txtInfosPompesManquantesAnnee = findViewById(R.id.txtInfosPompesManquantesAnnee);

        loadData();
    }

    public void majAffichageInfos() {
        try {
            if (getNbPompesManquantes() > 0) {
                txt_infos_journalieres = getString(R.string.txt_infos_journalieres_retard);
                txtInfosJournalieres.setText(String.format(txt_infos_journalieres, getNbPompesManquantes(), getDayOfYear()));
            } else {
                txt_infos_journalieres = getString(R.string.txt_infos_journalieres_avance);
                txtInfosJournalieres.setText(String.format(txt_infos_journalieres, -getNbPompesManquantes(), getDayOfYear()));
            }
            txtInfosPompesManquantesAnnee.setText(String.format(txt_infos_pompes_manquantes_annee, nbPompesFaitesTotal, getNbPompesPerYear()));
        } catch (Exception e) {
            e.printStackTrace();
            // les valeurs n'ont pas encore étés initialisées
        }
    }

    /*** NOTIFICATIONS ***/

    public void repeatNotification() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    /*** METHODES COMMUNES POUR LES DONNEES ***/

    public boolean saveData() {
        majData();
        majAffichageInfos();
        return SaveAndLoad.saveData(this, data);
    }

    public void loadData() {
        data = SaveAndLoad.loadData(this);
        majInfosfromData();
        majAffichageInfos();
    }

    public void majData() {
        data.setNbPompesFaitesTotal(nbPompesFaitesTotal);
        data.setDifficulte(difficulte);
        data.setRatio(ratio);
        data.setHour(hour);
        data.setMinutes(minutes);
        data.setNotification(notification);
    }

    public void majInfosfromData() {
        nbPompesFaitesTotal = data.getNbPompesFaitesTotal();
        difficulte = data.getDifficulte();
        ratio = data.getRatio();
        hour = data.getHour();
        minutes = data.getMinutes();
        notification = data.getNotification();
    }

    /****************
     ***  CALCULS ***
     ****************/

    public int getNbPompesManquantes() {
        return getNbPompesUntilToday() - nbPompesFaitesTotal;
    }

    public int getNbPompesUntilToday() {
        int nbPompes = 0;
        for (int i = 0; i < getDayOfYear(); i++) {
            nbPompes += i * ratio;
        }
        return nbPompes;
    }

    public int getNbPompesPerYear() {
        int nbPompes = 0;
        for (int i = 0; i < 365; i++) {
            nbPompes += i * ratio;
        }
        return nbPompes;
    }

    public static int getDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
}