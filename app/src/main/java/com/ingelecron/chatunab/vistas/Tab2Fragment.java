package com.ingelecron.chatunab.vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ingelecron.chatunab.R;

import java.io.File;

public class Tab2Fragment extends Fragment {

    View view;

    private static final int RESULTADO_GALERIA = 1;
    private static final int RESULTADO_FOTO = 2;
    private String nombre="", telefono="", email="", descripcion="";
    private SwipeRefreshLayout srl_contactos;
    private ListView lv_contactos;
    private String[] contactos;
    private ImageView iv_foto;
    private FloatingActionButton fab_acciones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_tab2, container, false);

        Bundle bundle= getActivity().getIntent().getExtras();
        if(bundle!=null){
            nombre=bundle.getString("nombre");
            telefono=bundle.getString("telefono");
            email=bundle.getString("email");
            descripcion=bundle.getString("descripcion");
        }

        srl_contactos= view.findViewById(R.id.srl_contactos);
        lv_contactos= view.findViewById(R.id.lv_contactos);
        iv_foto= view.findViewById(R.id.iv_foto);
        fab_acciones= view.findViewById(R.id.fab_acciones);

        contactos= getResources().getStringArray(R.array.listacontactos);
        lv_contactos.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contactos));
        lv_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PopupMenu popupMenu=new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.ver:

                                Toast.makeText(getActivity(), "Ver", Toast.LENGTH_LONG).show();

                                Intent intent= new Intent(getActivity(), Detalle.class);
                                intent.putExtra("nombre", contactos[i]);
                                intent.putExtra("telefono", telefono);
                                intent.putExtra("email", email);
                                intent.putExtra("descripcion", descripcion);
                                startActivity(intent);

                                break;
                            case R.id.eliminar:
                                Toast.makeText(getActivity(), "Eliminar", Toast.LENGTH_LONG).show();
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        srl_contactos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refrescarLista();
            }
        });

        registerForContextMenu(iv_foto);


        return view;
    }

    //otros metodos
    private void refrescarLista() {
        contactos= getResources().getStringArray(R.array.listacontactos);
        lv_contactos.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contactos));
        srl_contactos.setRefreshing(false);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.ver:
                Toast.makeText(getActivity(), "Ver", Toast.LENGTH_LONG).show();
                return true;
            case R.id.quitar:
                Toast.makeText(getActivity(), "Quitar", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void botonFlotante(View view) {

        CharSequence[] opciones={"Ver pagina", "Ver mapa", "Llamar", "Correo", "Mensaje", "Subir foto", "Tomar foto"};
        AlertDialog.Builder menuAcciones=new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        menuAcciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int opcion) {

                switch (opcion){
                    case 0:
                        Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unab.edu.co/"));
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:7.116560, -73.105490"));
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:3121234567"));
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4=new Intent(Intent.ACTION_SENDTO);
                        intent4.setData(Uri.parse("mailto:"));
                        intent4.putExtra(Intent.EXTRA_EMAIL, "rodolfo.ortega@o365.unab.edu.co");
                        intent4.putExtra(Intent.EXTRA_SUBJECT, "saludo");
                        intent4.putExtra(Intent.EXTRA_TEXT, "Hola soy un mensaje de android");
                        if(intent4.resolveActivity(getActivity().getPackageManager())!=null){
                            startActivity(intent4);
                        }
                        break;
                    case 4:
                        Intent intent5=new Intent(Intent.ACTION_SEND);
                        intent5.setType("text/plain");
                        intent5.putExtra(Intent.EXTRA_TEXT, "Hola soy un mensaje de android");
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent6, "Seleccionar foto"), RESULTADO_GALERIA);
                        break;
                    case 6:
                        Intent intent7=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uriFoto=Uri.fromFile(new File(
                                Environment.getExternalStorageDirectory() + File.separator + "img_"+(System.currentTimeMillis()/1000)+".jpg")
                        );
                        intent7.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                        startActivityForResult(intent7, RESULTADO_FOTO);
                        if(intent7.resolveActivity(getActivity().getPackageManager())!=null){
                            startActivityForResult(intent7, RESULTADO_FOTO);
                        }
                        break;
                }
            }
        });
        menuAcciones.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if(requestCode==RESULTADO_GALERIA && resultCode== Activity.RESULT_OK){

                String uriData= data.getDataString();

                if(uriData!=null){
                    iv_foto.setImageURI(Uri.parse(uriData));

                }else {
                    iv_foto.setImageURI(null);
                }

            } else if(requestCode==RESULTADO_FOTO && resultCode== Activity.RESULT_OK){

                if(data!=null && data.getExtras()!=null){

                    Bitmap imagenBitMap=(Bitmap) data.getExtras().get("data");

                    iv_foto.setImageBitmap(imagenBitMap);
                }
            }

        }catch (Exception e){
            Log.e("Error de captura", "Error de seleccion de archivo", e);
        }
    }
}