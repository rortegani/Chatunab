package com.ingelecron.chatunab.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public class DBRVContactos extends SQLiteOpenHelper {

    private Context contexto;

    public DBRVContactos(@Nullable Context context) {
        super(context, ConstantesDBRVContactos.NOMBRE_DB, null, ConstantesDBRVContactos.VERSION_DB);
        this.contexto= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCrearTablaContacto="CREATE TABLE " +ConstantesDBRVContactos.T_CONTACTO+ "(" +
                ConstantesDBRVContactos.T_CONTACTO_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesDBRVContactos.T_CONTACTO_FOTO+ " TEXT, " +
                ConstantesDBRVContactos.T_CONTACTO_NOMBRE+ " TEXT, " +
                ConstantesDBRVContactos.T_CONTACTO_ESPECIE+ " TEXT " +
//                ConstantesDBRVContactos.T_CONTACTO_CORREO+ " TEXT " +
                ")";

        db.execSQL(sqlCrearTablaContacto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int nuevaVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +ConstantesDBRVContactos.T_CONTACTO);
        onCreate(db);
    }

    public ArrayList<PojoContacto> leerContacto(){

        ArrayList<PojoContacto> pojoContactoArrayList =new ArrayList<>();

        String sqlLeerContactos="SELECT * FROM " +ConstantesDBRVContactos.T_CONTACTO;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor recorridoContacto=db.rawQuery(sqlLeerContactos, null);

        try{
            while (recorridoContacto.moveToNext()){

                PojoContacto pojoContacto=new PojoContacto();
                pojoContacto.setId(String.valueOf(recorridoContacto.getInt(0)));
                pojoContacto.setFoto(recorridoContacto.getString(1));
                pojoContacto.setNombre(recorridoContacto.getString(2));
                pojoContacto.setEspecie(recorridoContacto.getString(3));
//                pojoContacto.setCorreo(recorridoContacto.getString(4));

                pojoContactoArrayList.add(pojoContacto);
            }

            recorridoContacto.close();
            db.close();

        }catch(SQLException e){
            recorridoContacto.close();
            db.close();
        }

        return pojoContactoArrayList;
    }

    public void crearContacto(ContentValues contentValues){
        SQLiteDatabase bd=this.getWritableDatabase();
        bd.insert(ConstantesDBRVContactos.T_CONTACTO, null, contentValues);
        bd.close();
    }

    public void eliminarContactos(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(ConstantesDBRVContactos.T_CONTACTO, null, null);
        sqLiteDatabase.close();
    }

}
