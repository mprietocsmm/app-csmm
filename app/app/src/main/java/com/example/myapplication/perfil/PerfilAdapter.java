package com.example.myapplication.perfil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;


import java.util.List;

public class PerfilAdapter extends ArrayAdapter {
    private List<Boolean> checked;
    private Context context;
    private List<String> items;
    public PerfilAdapter(@NonNull Context context, @NonNull List objects, List<Boolean> checked) {
        super(context, R.layout.lista, objects);

        this.context = context;
        this.items = objects;

        for (int i=0; i<items.size(); i++)
            items.get(i);
        this.checked = checked;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.lista, parent, false);
        TextView textView = convertView.findViewById(R.id.textView);
        Switch switchAjustes = convertView.findViewById(R.id.switchAjustes);

        switchAjustes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked.set(position, isChecked);
            }
        });
        textView.setText(items.get(position));
        switchAjustes.setChecked(checked.get(position));
        return convertView;
    }

    @Nullable
    @Override
    public Boolean getItem(int position) {
        return checked.get(position);
    }


}
