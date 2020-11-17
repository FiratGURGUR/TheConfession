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
import frt.gurgur.theconfession.databinding.FollowlistItemBinding;
import frt.gurgur.theconfession.databinding.PostListItemBinding;
import frt.gurgur.theconfession.databinding.PostListItemImageBinding;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.model.user.follow.FollowListResponse;
import frt.gurgur.theconfession.model.user.follow.FollowsItem;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.MyViewHolder> {


    private static final String TAG = "FollowerAdapter";
    private List<FollowsItem> list;


    public FollowerAdapter() {

    }

    public FollowerAdapter(List<FollowsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FollowerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.followlist_item, parent, false);
        return new FollowerAdapter.MyViewHolder((FollowlistItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowerAdapter.MyViewHolder holder, int position) {


        FollowlistItemBinding binding = holder.binding;
        binding.setFollower(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private FollowlistItemBinding binding;

        public MyViewHolder(FollowlistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }


}
