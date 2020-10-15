package com.kmt.massage.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kmt.massage.R;
import com.kmt.massage.data.model.VibPattern;
import com.kmt.massage.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings({"unused", "RedundantSuppression", "FieldCanBeLocal"})
public class MenuAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context context;
    private List<VibPattern> patterns;
    private Callback mCallback;
    private int selectedPosition = 0;
    public MenuAdapter(Context context, ArrayList<VibPattern> patterns) {
        this.patterns = patterns;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemEvents = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_application, viewGroup, false);
        return new AssignedTasksViewHolder(itemEvents);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return patterns.size()/*options.size()*/;
    }

    public void addItems(List<VibPattern> options) {
        this.patterns.clear();
        this.patterns.addAll(options);
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(VibPattern application);
    }

    public class AssignedTasksViewHolder extends BaseViewHolder {

        @BindView(R.id.pattern_name)
        TextView patternName;

        @BindView(R.id.pattern_pic)
        ImageView ivPattern;

        @BindView(R.id.card_view)
        CardView cardView;

        VibPattern application;

        public AssignedTasksViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        protected void clear() {
            patternName.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            application = patterns.get(position);
            if (application != null) {

                if(selectedPosition==position)
                    cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.selected_background_color));
                else
                    cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));

                patternName.setText(application.title);
                ivPattern.setImageDrawable(itemView.getResources().getDrawable(application.pic));
                itemView.setOnClickListener(v -> {
                    selectedPosition=position;
                    notifyDataSetChanged();
                    mCallback.onItemClicked(application);
                });
            }
        }
    }
}