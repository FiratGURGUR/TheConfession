package frt.gurgur.theconfession.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.PostListItemBinding;
import frt.gurgur.theconfession.model.main.DataItem;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {


    private static final String TAG = "PostListAdapter";
    private List<DataItem> postList;

    public PostListAdapter() {

    }

    public PostListAdapter(List<DataItem> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PostListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.post_list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final PostListItemBinding binding;

        public ViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DataItem post) {
            binding.setPost(post);
        }

    }
}
