package com.ingelecron.chatunab.vistas.cuenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.controladores.RecuperarContraControlador;
import com.ingelecron.chatunab.utils.ValidarCorreo;

public class RecuperarContraActivity extends AppCompatActivity {

    private TextView tie_correo;
    private Button b_enviarcorreo;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        view=findViewById(R.id.cl_recuperarcontra);

        tie_correo=findViewById(R.id.tie_correo);
        b_enviarcorreo=findViewById(R.id.b_enviarcorreo);

        tie_correo.addTextChangedListener(textWatcher);

        b_enviarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperarContraControlador.recuperarContra(RecuperarContraActivity.this, getCorreo());
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

        if(ValidarCorreo.validar(correo)){
            b_enviarcorreo.setEnabled(true);
            return true;
        }else {
            b_enviarcorreo.setEnabled(false);
            return false;
        }
    }

    public String getCorreo() {
        return tie_correo.getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ocultarTeclado(RecuperarContraActivity.this, view);
        tie_correo.clearFocus();
        return true;
    }

    public static void ocultarTeclado(Context context, View view){

        InputMethodManager imm=(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}