package com.example.acer.srikestra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<Integer> images=new ArrayList<>();
    private ArrayList<String > names=new ArrayList<>();
    private Context context;
    public static final String TAG="StraggeredRecycleViewAd";

    public RecyclerAdapter(Context context , ArrayList<Integer> images, ArrayList<String> names) {
        this.images = images;
        this.names = names;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_fragment,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called:");
        RequestOptions requestOptions=new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(context)
                .load(images.get(i))
                .apply(requestOptions)
                .into(viewHolder.imageView);
        viewHolder.textView.setText(names.get(i));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked."+names.get(i));
                Toast.makeText(context,names.get(i),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.imgview);
            this.textView=itemView.findViewById(R.id.txtview);
        }
    }
}

