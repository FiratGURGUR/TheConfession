package frt.gurgur.theconfession.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.CommentListItemBinding;
import frt.gurgur.theconfession.databinding.FollowlistItemBinding;
import frt.gurgur.theconfession.model.comment.CommentsItem;
import frt.gurgur.theconfession.model.user.follow.FollowsItem;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {

    private List<CommentsItem> list;

    public CommentListAdapter() {
    }

    public CommentListAdapter(List<CommentsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.comment_list_item, parent, false);
        return new CommentListAdapter.MyViewHolder((CommentListItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.MyViewHolder holder, int position) {
        CommentListItemBinding binding = holder.binding;
        binding.setComment(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private CommentListItemBinding binding;

        public MyViewHolder(CommentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
