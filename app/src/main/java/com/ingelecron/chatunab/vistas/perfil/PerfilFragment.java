package com.ingelecron.chatunab.vistas.perfil;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.controladores.PerfilControlador;
import com.ingelecron.chatunab.data.firebase.model.Usuario;
import com.ingelecron.chatunab.data.firebase.ConstantesFirebase;
import com.squareup.picasso.Picasso;


public class PerfilFragment extends Fragment {

    View view;
    private ShapeableImageView iv_foto;
    private ListenerRegistration listenerRegistration;
    private TextInputEditText tie_nombre, tie_email;
    private Button b_actualizar;
    String urlImagen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_perfil, container, false);

        iv_foto= view.findViewById(R.id.iv_foto);
        tie_nombre= view.findViewById(R.id.tie_nombre);
        tie_email= view.findViewById(R.id.tie_email);
        b_actualizar= view.findViewById(R.id.b_actualizar);

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent();

                PopupMenu popupMenu=new PopupMenu(getActivity(), v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_img_perfil, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.galeria:
//                                obtenerImagen.launch("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                obtenerImagen.launch(intent);
                                break;

                            case R.id.camara:

//                                File archivo= CamaraOrientacion.getArchivo();

                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                                Uri uriFoto=Uri.fromFile(new File(
//                                        Environment.getExternalStorageDirectory() + File.separator + "img_"+(System.currentTimeMillis()/1000)+".jpg")
//                                );
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                                obtenerImagen.launch(intent);

                                break;
                            case R.id.eliminar:
                                if(urlImagen!=null && urlImagen.length()>0){
                                    PerfilControlador.eliminarFoto(getActivity(), urlImagen);
                                }else {
                                    Toast.makeText(getActivity(), "No hay foto para eliminar", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        b_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getNombre().trim().length()>2){
                    PerfilControlador.actualizarDatos(getActivity(),"nombre", getNombre());
                }else {
                    Toast.makeText(getActivity(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });



        obtenerInformacion();

        return view;
    }

    private void obtenerInformacion() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection(ConstantesFirebase.USERS)
                .document(firebaseUser.getUid());

        //nos escucha los datos y se actualiza la interfaz en tiempo real
        listenerRegistration = documentReference.addSnapshotListener(informacionUsuario);
    }

    private EventListener<DocumentSnapshot> informacionUsuario = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            try {

                if (value!=null){

                    //obtenemos los datos del usuario
                    Usuario usuario= value.toObject(Usuario.class);

                    if (usuario!=null){
                        urlImagen= usuario.getImagen();
                        String nombre= usuario.getNombre();
                        String correo= usuario.getCorreo();

                        if(urlImagen!=null && urlImagen.length()>0){
                            Picasso.get().load(urlImagen).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(iv_foto);
                        }else {
                            iv_foto.setImageResource(R.drawable.ic_logo);
                        }
                        tie_nombre.setText(nombre);
                        tie_email.setText(correo);

                        //si la actividad no se esta finalizando
                        if(!requireActivity().isFinishing()){

                        }

                    }
                }

            }catch (NullPointerException | IllegalStateException e){
                e.getCause();
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        //deja de escuchar los datos
        if(listenerRegistration!=null){
            listenerRegistration.remove();
            listenerRegistration=null;
        }
    }

    public String getNombre() {
        return tie_nombre.getText().toString();
    }

    ActivityResultLauncher<Intent> obtenerImagen = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        try {
                            Uri uri= result.getData().getData();
                            if(uri!=null){
                                PerfilControlador.actualizarImagen(getActivity(), uri);
                            }
                        }catch (NullPointerException e){
                            e.getCause();
                        }
                    }
                }
            });

}

