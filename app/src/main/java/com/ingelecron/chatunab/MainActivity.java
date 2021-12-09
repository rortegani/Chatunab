package com.ingelecron.chatunab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.ingelecron.chatunab.adaptadores.AdaptadorFragment;
import com.ingelecron.chatunab.vistas.tab1.Tab1Fragment;
import com.ingelecron.chatunab.vistas.Tab2Fragment;
import com.ingelecron.chatunab.vistas.cuenta.LoginActivity;
import com.ingelecron.chatunab.vistas.perfil.Perfil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=1;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SearchView searchView;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        appBarLayout= findViewById(R.id.appBarLayout);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tabLayout= findViewById(R.id.tabLayout);
//        searchView= findViewById(R.id.searchView);
        viewPager= findViewById(R.id.viewPager);
//        floatingActionButton= findViewById(R.id.fab_main);

        int[] tituloTab= new int[]{R.string.tab1, R.string.tab2};

        viewPager.setAdapter(new AdaptadorFragment(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                this, arrayFragment(), tituloTab));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_mas);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_persona);

        comprobarPermisos();

    }

    private ArrayList<Fragment> arrayFragment() {

        ArrayList<Fragment> fragmentArrayList= new ArrayList<>();
        fragmentArrayList.add(new Tab1Fragment());
        fragmentArrayList.add(new Tab2Fragment());

        return fragmentArrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){

            case R.id.perfil:
                startActivity(new Intent(MainActivity.this, Perfil.class));
                return true;
            case R.id.saludo:
                Toast.makeText(this, "Saludo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notificacion:
                Toast.makeText(this, "Notificacion", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mapa:
                Toast.makeText(this, "mapa", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.archivo:
                Toast.makeText(this, "archivo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.configuracion:
                Toast.makeText(this, "configuracion", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cerrar:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void comprobarPermisos() {

        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>1 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(this, "Permisos otorgados", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Necesita permisos para usar la camara y  el almacenamiento", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}