package com.example.applipompes.success;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.applipompes.R;

import java.util.ArrayList;

public class ThreadNotificationsSucces extends Thread {

    private Context context;
    private int nbPompesFaitesAvant;
    private int nbPompesFaitesApres;

    public ThreadNotificationsSucces(Context _context, int _nbPompesFaitesTotal, int _nbPompesEnPlus) {
        context = _context;
        nbPompesFaitesAvant = _nbPompesFaitesTotal - _nbPompesEnPlus;
        nbPompesFaitesApres = _nbPompesFaitesTotal;
    }

    @Override
    public void run() {
        super.run();

        /* On récupère la liste des succes */
        ArrayList<Success> listSuccess = Success.getSuccess();
        for (Success success : listSuccess) { // pour chaque succes
            if (nbPompesFaitesAvant < success.getNbPompesRequis()) { // s'il n'avait pas déja était atteint
                if (nbPompesFaitesApres >= success.getNbPompesRequis()) { // s'il a été atteint depuis

                    /* On créer une dialog qui notifiera que le succes soit atteint */
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.notification_nouveau_succes);
                    builder.setTitle("Vous avez obtenu le succes " + success.getNom() + " !");
                    AlertDialog dialog = builder.create();

                    /* On place la dialog en haut de l'écran */
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.TOP;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    /* et on l'affiche */
                    dialog.show();

                    /* Aprés 2 secondes on la masque */
                    new Handler().postDelayed(() -> {
                        dialog.dismiss();
                    }, 2000);
                }
            }
        }
    }
}
