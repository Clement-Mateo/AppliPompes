package com.example.applipompes;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applipompes.notifications.NotificationReceiver;
import com.example.applipompes.sauvegarde.Data;
import com.example.applipompes.sauvegarde.SaveAndLoad;
import com.example.applipompes.success.SuccessPage;

import java.util.Calendar;
import java.util.Date;

public class Configuration extends AppCompatActivity {

    private Data data;
    private Boolean init;

    /* Données */

    private int nbPompesFaitesTotal;

    private String difficulte;
    private int ratio;

    private int hour;
    private int minutes;

    private boolean notification;

    /* Views and others */

    private String txt_difficulte_choisi;
    private Button btnNormal;
    private Button btnDifficile;
    private Button btnExtreme;
    private Button btnImpossible;
    private Button btn_difficulte_choisi;

    private String txt_infos_pompes_annee;
    private TextView txtInfoPompesAnnee;

    private TimePickerDialog timePicker;
    private EditText editTextDefinirHeure;
    private Switch switchNotification;
    private Button btnValiderHeure;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        editTextDefinirHeure.setOnClickListener(v -> {
            // time picker dialog
            timePicker = new TimePickerDialog(this,
                    (tp, sHour, sMinute) -> editTextDefinirHeure.setText(sHour + ":" + sMinute), hour, minutes, true);
            timePicker.show();
        });
        btnValiderHeure = findViewById(R.id.btnValiderHeure);
        btnValiderHeure.setOnClickListener(v -> {
            if (editTextDefinirHeure.getText().toString().length() > 0) {
                try {
                    String[] time = editTextDefinirHeure.getText().toString().split(":");
                    hour = Integer.parseInt(time[0]);
                    minutes = Integer.parseInt(time[1]);
                    notification = true;
                    saveData();
                    repeatNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                    editTextDefinirHeure.setText(hour + ":" + minutes);
                    Toast.makeText(getApplicationContext(), "Votre saisi n'est pas valide !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                notification = true;
                repeatNotification();
                Toast.makeText(getApplicationContext(), "La notification apparaitra tout les jours a " + hour + ":" + minutes, Toast.LENGTH_SHORT).show();
            } else {
                notification = false;
                /* on annule la notification programmée (pour cela on créer une nouvelle pendingIntent et on l'annule desuite après) */
                repeatNotification();
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "La notification journalière à bien été desactivée", Toast.LENGTH_SHORT).show();
            }
            saveData();
        });
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
            case R.id.succes:
                intent = new Intent(this, SuccessPage.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void normal(View view) {
        difficulte = btnNormal.getText().toString();
        ratio = 1;
        saveData();
    }

    public void difficile(View view) {
        difficulte = btnDifficile.getText().toString();
        ratio = 2;
        saveData();
    }

    public void extreme(View view) {
        difficulte = btnExtreme.getText().toString();
        ratio = 3;
        saveData();
    }

    public void impossible(View view) {
        difficulte = btnImpossible.getText().toString();
        ratio = 5;
        saveData();
    }

    public void clicRaz(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage(getString(R.string.txt_validation_réinitialisation));

        /* Si l'utilisateur valide la réinitialisation alors on remet tout les valeurs par defaut et on sauvegarde */
        builder.setPositiveButton("OK", (dialog, which) -> {
            /* on créé un nouveau jeu de donnée par defaut */
            data = new Data();
            /* et on le sauvegarde */
            majInfosfromData();
            saveData();
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> {
            /* Si l'utilisateur annule la réinitialisation alors on fait rien */
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void init() {

        init = true;
        setContentView(R.layout.configuration);

        btnNormal = findViewById(R.id.btnDifficulteNormal);
        btnDifficile = findViewById(R.id.btnDifficulteDifficile);
        btnExtreme = findViewById(R.id.btnDifficulteExtreme);
        btnImpossible = findViewById(R.id.btnDifficulteImpossible);
        txt_difficulte_choisi = getString(R.string.txt_btn_difficulte_choisi);
        btn_difficulte_choisi = findViewById(R.id.btnChoisirDifficulte);

        txtInfoPompesAnnee = findViewById(R.id.txtInfoPompesAnnee);
        txt_infos_pompes_annee = getString(R.string.txt_info_pompes_annee);

        editTextDefinirHeure = findViewById(R.id.setTimeEditText);
        editTextDefinirHeure.setInputType(InputType.TYPE_NULL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editTextDefinirHeure.setFocusedByDefault(true);
        }

        switchNotification = findViewById(R.id.switchNotification);

        loadData();
        init = false;
    }

    public void majAffichageInfos() {
        try {
            btn_difficulte_choisi.setText(String.format(txt_difficulte_choisi, difficulte));
            switch (difficulte) {
                case "normal":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btn_difficulte_choisi.setBackgroundDrawable(getDrawable(R.drawable.button_normal));
                    }
                    break;
                case "difficile":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btn_difficulte_choisi.setBackgroundDrawable(getDrawable(R.drawable.button_difficile));
                    }
                    break;
                case "extreme":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btn_difficulte_choisi.setBackgroundDrawable(getDrawable(R.drawable.button_extreme));
                    }
                    break;
                case "impossible":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btn_difficulte_choisi.setBackgroundDrawable(getDrawable(R.drawable.button_impossible));
                    }
                    break;
            }

            txtInfoPompesAnnee.setText(String.format(txt_infos_pompes_annee, getNbPompesPerYear()));

            String message = hour + ":" + minutes;
            editTextDefinirHeure.setText(message);

            if (switchNotification.isChecked() && notification && !init) { /* Si les notification étaient activées alors on affiche un toast indiquant la nouvelle heure*/
                Toast.makeText(getApplicationContext(), "La notification apparaitra tout les jours a " + hour + ":" + minutes, Toast.LENGTH_SHORT).show();
            } else {
                switchNotification.setChecked(notification);
            }
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
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

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
