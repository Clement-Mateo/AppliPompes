package com.example.applipompes.success;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.applipompes.R;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    ArrayList<Success> listSuccess;

    public CustomAdapter(Context applicationContext, ArrayList<Success> listSuccess) {
        this.context = applicationContext;
        this.listSuccess = listSuccess;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listSuccess.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Success success = listSuccess.get(i);
        view = inflter.inflate(R.layout.success_element, null); // inflate the layout

        RichPathView vector = view.findViewById(R.id.vector);
        RichPath path = vector.findRichPathByName("successPath");
        path.setFillColor(success.getColor());

        TextView nom = view.findViewById(R.id.nom);
        nom.setText(listSuccess.get(i).getNom());
        nom.setTextColor(success.getColor());

        TextView description = view.findViewById(R.id.description);
        description.setText("Vous avez fait " + success.getNbPompesRequis() + " pompes dans l'année.");
        description.setTextColor(success.getColor());

        return view;
    }
}