package com.kmt.massage.ui.main.musicDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmt.massage.R;
import com.kmt.massage.data.model.MusicData;
import com.kmt.massage.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings({"unused", "RedundantSuppression", "FieldCanBeLocal"})
public class MusicAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Context context;
    private List<MusicData> patterns;
    private Callback mCallback;

    public MusicAdapter(Context context, ArrayList<MusicData> patterns) {
        this.patterns = patterns;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemEvents = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music, viewGroup, false);
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

    public void addItems(List<MusicData> options) {
        this.patterns.clear();
        this.patterns.addAll(options);
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(MusicData application);
    }

    public class AssignedTasksViewHolder extends BaseViewHolder {

        @BindView(R.id.music_name)
        TextView musicName;

        MusicData musicData;

        public AssignedTasksViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @Override
        protected void clear() {
            musicName.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            musicData = patterns.get(position);
            if (musicData != null) {
                musicName.setText(musicData.title);
                itemView.setOnClickListener(v -> mCallback.onItemClicked(musicData));
            }
        }
    }
}