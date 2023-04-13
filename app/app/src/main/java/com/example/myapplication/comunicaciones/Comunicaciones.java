package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Comunicaciones extends Fragment {
    private Context context = getActivity();

    public static Comunicaciones newInstance() {
        Comunicaciones fragment = new Comunicaciones();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("Fragment comunicaciones");
        Toast.makeText(context, "Fragment comunicaciones", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_comunicaciones, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Comunicaciones");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
