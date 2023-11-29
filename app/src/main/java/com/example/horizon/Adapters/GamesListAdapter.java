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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.horizon.Activity.DetailActivity;
import com.example.horizon.Domian.ListGame;
import com.example.horizon.R;

//public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.ViewHolder> {
//
//    ListGame items;
//    Context context;
//
//    public GamesListAdapter(ListGame items) {
//        this.items = items;
//    }
//
//    @NonNull
//    @Override
//    public GamesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        context = parent.getContext();
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_games, parent, false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GamesListAdapter.ViewHolder holder, int position) {
//
//        holder.title.setText(items.getData().get(position).getTitle());
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
//        Glide.with(context).load(items.getData().get(position).getThumbnail()).apply(requestOptions).into(holder.pic);
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
//            intent.putExtra("id", items.getData().get(position).getId());
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.getData().size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title;
//        ImageView pic;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.title);
//            pic = itemView.findViewById(R.id.pic);
//        }
//    }
//}
