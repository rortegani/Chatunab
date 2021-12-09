package com.ingelecron.chatunab.adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdaptadorFragment extends FragmentPagerAdapter {

    private Context contexto;
    private ArrayList<Fragment> fragmentArrayList;
    private int[] tituloTab;

    public AdaptadorFragment(@NonNull FragmentManager fm, int behavior, Context contexto, ArrayList<Fragment> fragmentArrayList, int[] tituloTab) {
        super(fm, behavior);
        this.contexto=contexto;
        this.fragmentArrayList=fragmentArrayList;
        this.tituloTab=tituloTab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return contexto.getResources().getString(tituloTab[position]);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
