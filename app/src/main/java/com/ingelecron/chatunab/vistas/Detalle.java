package com.ingelecron.chatunab.vistas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ingelecron.chatunab.R;

public class Detalle extends AppCompatActivity {

    private String nombre="", telefono="", email="", descripcion="";
    private TextInputEditText  tie_nombre, tie_telefono, tie_email, tie_descripcion;
    private MaterialButton b_editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            nombre=bundle.getString("nombre");
            telefono=bundle.getString("telefono");
            email=bundle.getString("email");
            descripcion=bundle.getString("descripcion");
        }

        tie_nombre=findViewById(R.id.tie_nombre);
        tie_telefono=findViewById(R.id.tie_telefono);
        tie_email=findViewById(R.id.tie_email);
        tie_descripcion=findViewById(R.id.tie_descripcion);
        b_editar=findViewById(R.id.b_editar);

        tie_nombre.setText(nombre);
        tie_telefono.setText(telefono);
        tie_email.setText(email);
        tie_descripcion.setText(descripcion);

        b_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, getResources().getString(R.string.app_name), Snackbar.LENGTH_LONG)
                        .setAction("¿Seguro desea editar?", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Toast.makeText(Detalle.this, "Editando", Toast.LENGTH_LONG).show();

                                nombre= tie_nombre.getText().toString();
                                telefono= tie_telefono.getText().toString();
                                email= tie_email.getText().toString();
                                descripcion= tie_descripcion.getText().toString();

                                Intent intent=new Intent(Detalle.this, Editar.class);
                                intent.putExtra("nombre", nombre);
                                intent.putExtra("telefono", telefono);
                                intent.putExtra("email", email);
                                intent.putExtra("descripcion", descripcion);
                                startActivity(intent);
                            }
                        })
                        .setActionTextColor(ContextCompat.getColor(Detalle.this, R.color.white))
                        .show();

            }
        });


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {


            Slide slide=new Slide(Gravity.BOTTOM);
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);

            getWindow().setReturnTransition(new Fade());
        }
    }
}