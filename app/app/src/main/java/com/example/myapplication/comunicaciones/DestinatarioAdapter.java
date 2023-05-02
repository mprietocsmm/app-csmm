package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.util.List;

public class DestinatarioAdapter extends BaseAdapter implements ConfirmarListener {
    private String[] lista;
    private Context context;
    private CheckBox[] checkBoxArray;
    private static Boolean[] checked;
    private LinearLayout[] viewArray;
    private LayoutInflater inflater;

    public DestinatarioAdapter(Context context, String[] lista) {
        this.context = context;
        this.lista = lista;
        checked = new Boolean[lista.length];
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i=0; i<checked.length; i++)
            checked[i] = false;

        checkBoxArray = new CheckBox[checked.length];
        viewArray = new LinearLayout[checked.length];
    }

    @Override
    public int getCount() {
        return lista.length;
    }

    @Override
    public Object getItem(int position) {
        return lista[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Boolean[] checkedItems(int position) {
        return checked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) convertView;
        if (view == null) {
            view = (LinearLayout) inflater.inflate(R.layout.destinatario_list_view, parent, false);
        }

        TextView nombre = view.findViewById(R.id.nombreTextView);
        nombre.setText(getItem(position).toString());

        CheckBox cBox = view.findViewById(R.id.checkbox);
        cBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        cBox.setChecked(checked[position]); // set the status as we stored it
        cBox.setOnCheckedChangeListener(mListener); // set the listener
        return view;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checked[(Integer)buttonView.getTag()] = isChecked; // get the tag so we know the row and store the status
        }
    };

    @Override
    public Boolean[] confirmarListener() {
        return checked;
    }

    @Override
    public int checkedLenght() {
        return checked.length;
    }
}
