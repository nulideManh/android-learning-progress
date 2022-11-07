package com.karl.appviewpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VpAdapter extends RecyclerView.Adapter<VpAdapter.VpViewHolder> {

    private List<Integer> backgrounds;
    private Context mContext;

    VpAdapter(Context context) {
        mContext = context;
        if (backgrounds == null) {
            backgrounds = new ArrayList<>();
            backgrounds.add(android.R.color.holo_blue_bright);
            backgrounds.add(android.R.color.holo_red_dark);
            backgrounds.add(android.R.color.holo_green_dark);
            backgrounds.add(android.R.color.holo_orange_light);
            backgrounds.add(android.R.color.holo_purple);
        }
    }

    @NonNull
    @Override
    public VpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VpViewHolder(LayoutInflater.from(mContext).inflate((R.layout.item_h_v), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VpViewHolder holder, int position) {
        holder.mTextView.setText("Thứ " + (position + 1) + " giao diện");
        holder.mLinearLayout.setBackgroundResource(backgrounds.get(position));
    }

    @Override
    public int getItemCount() {
        if (backgrounds == null) {
            return 0;
        }
        return backgrounds.size();
    }

    class VpViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mTextView;

        VpViewHolder(@NonNull View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.ll_h_v);
            mTextView = itemView.findViewById(R.id.tv_hv);
        }
    }
}
