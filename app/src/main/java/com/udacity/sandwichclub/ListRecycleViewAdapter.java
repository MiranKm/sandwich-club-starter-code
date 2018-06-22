package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListRecycleViewAdapter extends RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder> {


    private static final String TAG = "ListRecycleViewAdapter";

    ArrayList<String> list;
    private Context context;

    public ListRecycleViewAdapter(Context context, ArrayList<String> list) {
        Log.d(TAG, "ListRecycleViewAdapter: works");
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tv.setText(list.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                    context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.items);
            view = itemView;
        }
    }
}
