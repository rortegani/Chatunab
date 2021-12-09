package com.ingelecron.chatunab.controladores;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ingelecron.chatunab.MainActivity;

public class LoginControlador {

    public static void login(Activity activity, String correo, String contraseña){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //si la tarea fue exitosa eliminar el pantalla de registro y login
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        }else {
                            Toast.makeText(activity, "Error al intentar iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
