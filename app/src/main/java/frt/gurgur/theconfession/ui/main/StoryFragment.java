package frt.gurgur.theconfession.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FragmentStoryBinding;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import jp.shts.android.storiesprogressview.StoriesProgressView;


public class StoryFragment extends BaseFragment implements StoriesProgressView.StoriesListener, View.OnClickListener {
    FragmentStoryBinding binding;

    @BindView(R.id.stories)
    StoriesProgressView storiesProgressView;
    @BindView(R.id.skip)
    View skip;
    @BindView(R.id.reverse)
    View reverse;
    @BindView(R.id.close_story)
    ImageView close_story;
    @Inject
    ViewModelFactory vmFactory;
    MainViewModel vm;

    long pressTime = 0L;
    long limit = 500L;

    private int counter = 0;
    public List<StoriessItem> storyList;


    public StoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        showToolbar(false);
        showNavigation(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel.class);
        vm.loadWatchList();

        counter = getArguments().getInt("position");

        observeStoryList();
        observeLoadStatus();
        observerErrorStatus();
    }


    public void observeStoryList(){
        vm.getWatchList().observe(this, new Observer<List<StoriessItem>>() {
            @Override
            public void onChanged(List<StoriessItem> storiessItems) {
                storyList= storiessItems;
                initView();
            }
        });
    }


    public void initView() {
        skip.setOnClickListener(this);
        reverse.setOnClickListener(this);
        reverse.setOnTouchListener(onTouchListener);
        skip.setOnTouchListener(onTouchListener);

        close_story.setOnClickListener(this);
        binding.setStory(storyList.get(counter));

        storiesProgressView.setStoriesCount(storyList.size()); // <- set stories
        storiesProgressView.setStoryDuration(5000L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories(counter); // <- start progress
    }





    @Override
    public void onNext() {
        binding.setStory(storyList.get(++counter));
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        binding.setStory(storyList.get(--counter));
    }

    @Override
    public void onComplete() {
        multipleStackNavigator.goBack();
    }

    @Override
    public void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        showToolbar(true);
        showNavigation(true);
        super.onDestroy();
    }







    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                storiesProgressView.skip();
                break;
            case R.id.reverse:
                storiesProgressView.reverse();
                break;
            case R.id.close_story:
                multipleStackNavigator.goBack();
                break;
        }
    }
}