package com.zonassoft.footprintforlife.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.model.HActivity;
import com.zonassoft.footprintforlife.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListActivities extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HActivity> items = new ArrayList<>();
    private final int VIEW_ITEM = 1;
    private final int VIEW_SECTION = 0;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, HActivity obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListActivities(Context context, List<HActivity> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,bg_image;
        public CardView card;
        public TextView title,subtitle,number;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            number = (TextView) v.findViewById(R.id.task_number);
            subtitle = (TextView) v.findViewById(R.id.subtitle);
            card = (CardView) v.findViewById(R.id.card);
            lyt_parent = (View) v.findViewById(R.id.btn_action);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_h_activity, parent, false);
            vh = new AdapterListActivities.OriginalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
            vh = new AdapterListActivities.SectionViewHolder(v);
        }
        return vh;
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title_section,subtitle_secction;

        public SectionViewHolder(View v) {
            super(v);
            title_section = (TextView) v.findViewById(R.id.title_section);
            subtitle_secction = (TextView) v.findViewById(R.id.subtitle);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        HActivity a = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            String number="0"+position;
            if(position>9){
                number=""+(position-1);
                if(position<11){
                    number="0"+(position-1);
                }
            }

            view.title.setText(a.title);
            view.subtitle.setText(a.subtitle);
            view.number.setText(ctx.getString(R.string.task_info)+" "+(number));
            view.card.setCardBackgroundColor(ctx.getResources().getColor(a.color));
            Tools.displayImageOriginal(ctx, view.image, a.image);

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }else{
                SectionViewHolder view = (SectionViewHolder) holder;
                view.title_section.setText(a.title);
                view.subtitle_secction.setText(a.subtitle);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).section ? VIEW_SECTION : VIEW_ITEM;
    }

}