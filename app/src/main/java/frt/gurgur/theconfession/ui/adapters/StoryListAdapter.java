package frt.gurgur.theconfession.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.StoryItemBinding;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.ui.listeners.OnItemClickListener;
import frt.gurgur.theconfession.ui.listeners.StoryClickListener;
import frt.gurgur.theconfession.ui.user.profile.ProfileBottomSheetFragment;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.MyViewHolder> {
    private List<StoriessItem> list;
    private StoryClickListener listener;

    public StoryListAdapter() {

    }

    public StoryListAdapter(List<StoriessItem> list,StoryClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.story_item, parent, false);
        return new StoryListAdapter.MyViewHolder((StoryItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull StoryListAdapter.MyViewHolder holder, int position) {


        StoryItemBinding binding = holder.binding;
        binding.setStory(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private StoryItemBinding binding;

        public MyViewHolder(StoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.storyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStoryClick(getAdapterPosition());
                }
            });
        }


    }


}
