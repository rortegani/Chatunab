package com.ingelecron.chatunab.vistas.cuenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.controladores.RegistroControlador;
import com.ingelecron.chatunab.utils.ValidarCorreo;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText tie_nombre, tie_correo, tie_contraseña, tie_confirmarcontra;
    private Button b_registrar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        view=findViewById(R.id.cl_registro);

        tie_nombre=findViewById(R.id.tie_nombre);
        tie_correo=findViewById(R.id.tie_correo);
        tie_contraseña=findViewById(R.id.tie_contraseña);
        tie_confirmarcontra=findViewById(R.id.tie_confirmarcontra);
        b_registrar=findViewById(R.id.b_registrar);

        tie_nombre.addTextChangedListener(textWatcher);
        tie_correo.addTextChangedListener(textWatcher);
        tie_contraseña.addTextChangedListener(textWatcher);
        tie_confirmarcontra.addTextChangedListener(textWatcher);

        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistroControlador.usuarioAutenticacion(RegistroActivity.this, getNombre(), getCorreo(), getContraseña());
            }
        });

        tie_confirmarcontra.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_DONE){
                    if(habilitar()){
                        RegistroControlador.usuarioAutenticacion(RegistroActivity.this, getNombre(), getCorreo(), getContraseña());
                    }
                }

                return false;
            }
        });

    }


    private TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //cuando se escriba texto se comprueba y habilita el boton
            habilitar();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean habilitar() {

        String nombre= getNombre().trim();
        String correo= getCorreo().trim();
        String contraseña= getContraseña().trim();
        String confirmarcontra= getConfirmarcontra().trim();

        if((nombre.length()>2) && ValidarCorreo.validar(correo) && contraseña.length()>5 && confirmarcontra.equals(contraseña)){
            b_registrar.setEnabled(true);
            return true;
        }else {
            b_registrar.setEnabled(false);
            return false;
        }
    }

    private String getNombre(){
        return tie_nombre.getText().toString();
    }

    private String getCorreo(){
        return tie_correo.getText().toString();
    }

    private String getContraseña(){
        return tie_contraseña.getText().toString();
    }

    private String getConfirmarcontra(){
        return tie_confirmarcontra.getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ocultarTeclado(RegistroActivity.this, view);
        tie_nombre.clearFocus();
        tie_correo.clearFocus();
        tie_contraseña.clearFocus();
        tie_confirmarcontra.clearFocus();
        return true;
    }

    public static void ocultarTeclado(Context context, View view){

        InputMethodManager imm=(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}