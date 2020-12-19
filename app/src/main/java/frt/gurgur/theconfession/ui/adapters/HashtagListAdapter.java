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
import frt.gurgur.theconfession.databinding.HashtagListItemBinding;
import frt.gurgur.theconfession.databinding.StoryItemBinding;
import frt.gurgur.theconfession.model.hashtag.HashtagsItem;
import frt.gurgur.theconfession.model.stories.StoriessItem;
import frt.gurgur.theconfession.ui.listeners.HashtagClickListener;
import frt.gurgur.theconfession.ui.listeners.StoryClickListener;

public class HashtagListAdapter extends RecyclerView.Adapter<HashtagListAdapter.MyViewHolder> {
    private List<HashtagsItem> list;
    private HashtagClickListener listener;

    public HashtagListAdapter() {

    }

    public HashtagListAdapter(List<HashtagsItem> list, HashtagClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HashtagListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.hashtag_list_item, parent, false);
        return new HashtagListAdapter.MyViewHolder((HashtagListItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull HashtagListAdapter.MyViewHolder holder, int position) {


        HashtagListItemBinding binding = holder.binding;
        binding.setHashtag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private HashtagListItemBinding binding;

        public MyViewHolder(HashtagListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.hashtagLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickHashtag(list.get(getAdapterPosition()).getHashtag());
                }
            });
        }


    }


}
