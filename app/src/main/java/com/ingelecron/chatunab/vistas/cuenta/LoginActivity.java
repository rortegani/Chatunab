package com.ingelecron.chatunab.vistas.cuenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.controladores.LoginControlador;
import com.ingelecron.chatunab.utils.ValidarCorreo;

public class LoginActivity extends AppCompatActivity {

    private TextView tie_correo, tie_contraseña, tv_recuperarcontra;
    private Button b_login, b_registro;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        view=findViewById(R.id.cl_login);

        tie_correo=findViewById(R.id.tie_correo);
        tie_contraseña=findViewById(R.id.tie_contraseña);
        tv_recuperarcontra=findViewById(R.id.tv_recuperarcontra);
        b_login=findViewById(R.id.b_login);
        b_registro=findViewById(R.id.b_registro);

        tie_correo.addTextChangedListener(textWatcher);
        tie_contraseña.addTextChangedListener(textWatcher);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginControlador.login(LoginActivity.this, getCorreo(), getContraseña());
            }
        });

        b_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));

            }
        });

        tv_recuperarcontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecuperarContraActivity.class));
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

        String correo= getCorreo().trim();
        String contraseña= getContraseña().trim();

        if(ValidarCorreo.validar(correo) && contraseña.length()>5){
            b_login.setEnabled(true);
            return true;
        }else {
            b_login.setEnabled(false);
            return false;
        }
    }

    public String getCorreo() {
        return tie_correo.getText().toString();
    }

    public String getContraseña() {
        return tie_contraseña.getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ocultarTeclado(LoginActivity.this, view);
        tie_correo.clearFocus();
        tie_contraseña.clearFocus();
        return true;
    }

    public static void ocultarTeclado(Context context, View view){

        InputMethodManager imm=(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}