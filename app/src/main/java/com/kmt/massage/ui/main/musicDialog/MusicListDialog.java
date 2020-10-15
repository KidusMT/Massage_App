package com.kmt.massage.ui.main.musicDialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kmt.massage.R;
import com.kmt.massage.data.model.MusicData;
import com.kmt.massage.di.component.ActivityComponent;
import com.kmt.massage.ui.base.BaseDialog;
import com.kmt.massage.ui.main.DialogCommunicator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListDialog extends BaseDialog implements MusicListMvpView, MusicAdapter.Callback {


    public static final String TAG = MusicListDialog.class.getSimpleName();
    public static List<MusicData> musicDataList;
    @Inject
    MusicListMvpPresenter<MusicListMvpView> mPresenter;

    @Inject
    MusicAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.rv_music)
    RecyclerView mRecyclerView;

    @BindView(R.id.play_music)
    SwitchCompat playMusic;

    private DialogCommunicator communicator;
    private static boolean isPlaying = true;
    public static MusicListDialog newInstance(List<MusicData> musicData, boolean isMusicPlaying ) {
        musicDataList = musicData;
        isPlaying = isMusicPlaying;
        MusicListDialog fragment = new MusicListDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_music_list, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
        }

        setUp(view);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (DialogCommunicator) getBaseActivity();
    }

    @Override
    protected void setUp(View view) {
        setupMenuRecyclerView();
        communicator.setMusicOnOff(isPlaying);
        playMusic.setChecked(isPlaying);
        playMusic.setOnCheckedChangeListener((compoundButton, b) -> {
            isPlaying = b;
            communicator.setMusicOnOff(isPlaying);
        });
    }

    private void setupMenuRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addItems(musicDataList);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onItemClicked(MusicData application) {
        communicator.setSelectedMusic(application.fileName);
        dismissDialog();
    }
}