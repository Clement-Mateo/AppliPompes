package com.example.applipompes.sauvegarde;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveAndLoad {

    public static boolean saveData(Context context, Data data) {
        try {
            FileOutputStream fileOut = context.openFileOutput("sauvegarde", context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Data loadData(Context context) {

        Data data = new Data();
        try {
            FileInputStream fileIn = context.openFileInput("sauvegarde");
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            data = (Data) ois.readObject();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }
}
