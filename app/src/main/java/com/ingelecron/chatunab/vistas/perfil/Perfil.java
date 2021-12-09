package com.ingelecron.chatunab.vistas.perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ingelecron.chatunab.R;

public class Perfil extends AppCompatActivity {

    ImageButton ib_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ib_perfil= findViewById(R.id.ib_perfil);
        ib_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_perfil, new PerfilFragment(), PerfilFragment.class.getSimpleName())
                .commit();
    }
}