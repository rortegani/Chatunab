package com.ingelecron.chatunab.controladores;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ingelecron.chatunab.data.firebase.ConstantesFirebase;

import java.util.HashMap;

public class PerfilControlador {

    public static void actualizarDatos(Context contexto,
                                  String key,
                                  String valor){

        HashMap<String, Object> map= new HashMap<>();
        map.put(key, valor);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore.getInstance()
                .collection(ConstantesFirebase.USERS)
                .document(firebaseUser.getUid())
                .set(map, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(contexto, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(contexto, "Error al intentar actualizar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static void actualizarImagen(Context contexto, Uri imagen){

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        StorageReference archivoPath= FirebaseStorage.getInstance().getReference()
                .child(ConstantesFirebase.USERS_ALMACENAMIENTO_IMG_PERFIL).child(firebaseUser.getUid()+".jpg");

        archivoPath.putFile(imagen).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //obtenmos la url de la imagen
                archivoPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        actualizarImagenDB(contexto, uri.toString(), firebaseUser.getUid());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contexto, "Error al intentar subir la imagen", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progreso= (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                Toast.makeText(contexto, "Subiendo imagen: "+(int) progreso+"%", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private static void actualizarImagenDB(Context contexto, String imgUrl, String idUsuario) {

        HashMap<String, Object> map= new HashMap<>();
        map.put("imagen", imgUrl);

        FirebaseFirestore.getInstance()
                .collection(ConstantesFirebase.USERS)
                .document(idUsuario)
                .update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
//                            Picasso.get().load(imgUrl).into(iv_foto);
                            Toast.makeText(contexto, "Imagen guardada", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(contexto, "Error al intentar guardar la imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void eliminarFoto(Context contexto, String urlImagen) {

        StorageReference refImg= FirebaseStorage.getInstance().getReferenceFromUrl(urlImagen);

        refImg.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                           eliminarImagenDB(contexto);
                        }else {
                            Toast.makeText(contexto, "Error al intentar eliminar la foto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static void eliminarImagenDB(Context contexto) {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String, Object> map= new HashMap<>();
        map.put("imagen", "");

        FirebaseFirestore.getInstance()
                .collection(ConstantesFirebase.USERS)
                .document(firebaseUser.getUid())
                .set(map, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(contexto, "Imagen eliminada", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(contexto, "Error al intentar eliminar la imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
