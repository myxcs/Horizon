package com.example.horizon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.horizon.Activity.DetailActivity;
import com.example.horizon.Models.PopularModel;
import com.example.horizon.R;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {
private Context context;
private List<PopularModel> popularModelsList;

    public PopularAdapters(Context context, List<PopularModel> popularModelsList) {
        this.context = context;
        this.popularModelsList = popularModelsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.popular_item, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(popularModelsList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularModelsList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object", popularModelsList.get(position));
            context.startActivity(intent);
        }
        );}

    @Override
    public int getItemCount() {
        return popularModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popImg;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.game_name);
        }


    }

}
