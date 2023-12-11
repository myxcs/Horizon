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

import com.example.horizon.Models.GamesModel;
import com.example.horizon.R;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private Context context;
    private List<GamesModel> gamesModelsList;

    public GamesAdapter(Context context, List<GamesModel> gamesModelsList) {
        this.context = context;
        this.gamesModelsList = gamesModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.games_item, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(gamesModelsList.get(position).getImg_url()).into(holder.pics);
        holder.name.setText(gamesModelsList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object3", gamesModelsList.get(position));
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return gamesModelsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pics;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pics = itemView.findViewById(R.id.game_pics);
            name = itemView.findViewById(R.id.game_name);
        }
    }
}
