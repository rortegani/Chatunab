package com.ingelecron.chatunab.adaptadores;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.data.db.model.PojoContacto;
import com.ingelecron.chatunab.vistas.Detalle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorRVContactos extends RecyclerView.Adapter<AdaptadorRVContactos.InicioRvViewHolder> {

    private Activity contexto;
    private ArrayList<PojoContacto> pojoContactoArrayList;

    public AdaptadorRVContactos(Activity contexto, ArrayList<PojoContacto> pojoContactoArrayList) {
        this.contexto = contexto;
        this.pojoContactoArrayList = pojoContactoArrayList;
    }

    @NonNull
    @Override
    public InicioRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_iniciorv, parent, false);

        InicioRvViewHolder inicioRvViewHolder= new InicioRvViewHolder(itemView);

        return inicioRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InicioRvViewHolder holder, int position) {

        final PojoContacto pojoContacto =  pojoContactoArrayList.get(position);

        Picasso.get().load(pojoContacto.getFoto()).placeholder(R.drawable.ic_mas).error(R.drawable.ic_logo).into(holder.iv_foto);

//        holder.iv_foto.setImageResource(Integer.parseInt(pojoContacto.getFoto()));

        holder.iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(contexto, "enviar nombre", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(contexto, Detalle.class);
                intent.putExtra("nombre", pojoContacto.getNombre());

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

                    Explode explode=new Explode();
                    explode.setDuration(1000);
                    contexto.getWindow().setExitTransition(explode);
                    contexto.startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(contexto, view, "animacion").toBundle());

                }else {
                    contexto.startActivity(intent);
                }
            }
        });

        holder.tv_nombre.setText(pojoContacto.getNombre());
        holder.tv_especie.setText(pojoContacto.getEspecie());

    }

    @Override
    public int getItemCount() {
        return pojoContactoArrayList.size();
    }

    public class InicioRvViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_foto;
        private TextView tv_nombre, tv_especie;

        public InicioRvViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_foto= itemView.findViewById(R.id.iv_foto);
            tv_nombre= itemView.findViewById(R.id.tv_nombre);
            tv_especie= itemView.findViewById(R.id.tv_especie);
        }
    }
}
