package com.example.acer.srikestra;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_Place extends Fragment {
    private ArrayList<Integer> images = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_main_fragment, null);
        initImageBitmap();
        RecyclerView recyclerView  = view.findViewById(R.id.place_recycler);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), images, names);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        return view;

    }

    private void initImageBitmap() {


        images.add(R.mipmap.a);
        names.add("Set Paing \n Myo");
        images.add(R.mipmap.b);
        names.add("hi");
        images.add(R.mipmap.c);
        names.add("Myo \n Set\n Paing");
        images.add(R.mipmap.d);
        names.add("Myo");
        images.add(R.mipmap.e);
        names.add("Paing \n Myo");
        images.add(R.mipmap.f);
        names.add("Myo \n Set\n Paing");
        images.add(R.mipmap.g);
        names.add("Myo");
        images.add(R.mipmap.h);
        names.add("Myo");
        images.add(R.mipmap.i);
        names.add("Myo \n Set\n Paing");
        images.add(R.mipmap.j);
        names.add("Set Paing \n Myo");
        images.add(R.mipmap.k);
        names.add("Myo \n Set\n Paing");
        images.add(R.mipmap.l);
        names.add("Set");
        images.add(R.mipmap.m);
        names.add("Paing \n Myo");
        images.add(R.mipmap.n);
        names.add("Paing \n Myo");
        images.add(R.mipmap.o);
        names.add("Myo \n Set\n Paing");


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hotel");

    }
}
