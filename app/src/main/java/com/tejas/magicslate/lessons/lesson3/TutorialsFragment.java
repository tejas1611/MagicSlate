package com.tejas.magicslate.lessons.lesson3;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tejas.magicslate.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorialsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorialsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String VIDEO_SAMPLE = "lesson3_an_introduction_to_drawing_plants";
    private VideoView mVideoView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";

    public TutorialsFragment() {
        // Required empty public constructor
    }

    public static TutorialsFragment newInstance(String param1, String param2) {
        TutorialsFragment fragment = new TutorialsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getActivity().getPackageName() +
                "/raw/" + mediaName);
    }

    private void initializePlayer() {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorials, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVideoView = getView().findViewById(R.id.videoView);
        initializePlayer();
        MediaController controller = new MediaController(getActivity());
        controller.setMediaPlayer(mVideoView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        controller.setLayoutParams(lp);

        ((ViewGroup) controller.getParent()).removeView(controller);

        ((FrameLayout) getActivity().findViewById(R.id.videoViewWrapper)).addView(controller);
        mVideoView.setMediaController(controller);
    }
}
