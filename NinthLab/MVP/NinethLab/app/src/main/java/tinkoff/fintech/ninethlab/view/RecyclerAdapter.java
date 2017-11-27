package tinkoff.fintech.ninethlab.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.ninethlab.R;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemView;

        public ViewHolder(View v) {
            super(v);
            itemView = (TextView)v.findViewById(R.id.itemTextView);
        }
    }

    public RecyclerAdapter(List<String> items) {
        this.items = items;
    }

    public RecyclerAdapter(){
        this.items = new ArrayList<>();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}