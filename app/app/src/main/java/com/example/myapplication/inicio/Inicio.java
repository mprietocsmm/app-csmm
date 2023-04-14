package com.example.myapplication.inicio;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.comunicaciones.Comunicaciones;
import com.example.myapplication.rest.Rest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Rest rest = Rest.getInstance(this);
    private int tipoUsuario = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        try {
            peticionInicio();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.blue)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle.syncState();

        // Configuración del elemento default para que aparezca al iniciarse la activity y salga check
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_item_comunicaciones).setChecked(true));
    }

    private void peticionInicio() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("usuario", "ivangt");
        body.put("tipoUsuario", tipoUsuario);

        rest.inicio(
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView nombre = findViewById(R.id.nav_header_nombre);
                        switch (tipoUsuario) {
                            case 2:
                                try {
                                    Toast.makeText(Inicio.this, response.getString("nombre") + ", " + response.getString("curso"), Toast.LENGTH_SHORT).show();
                                    nombre.setText(response.getString("nombre") + ", " + response.getString("curso"));
                                } catch (JSONException e) {}
                                break;

                            case 4:
                                try {
                                    nombre.setText(response.getString("nombre"));
                                } catch (JSONException e) {}
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },
                body);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_item_inicio:
                Toast.makeText(this, "Clicaste Inicio", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_item_asignaturas:
                Toast.makeText(this, "Clicaste Asignaturas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_comunicaciones:
                fragment = Comunicaciones.newInstance();
                break;
                
        }

        if (fragment != null) {
            setFragment(fragment);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
