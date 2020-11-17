package frt.gurgur.theconfession.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.FollowlistItemBinding;
import frt.gurgur.theconfession.model.user.follow.FollowsItem;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.MyViewHolder> {


    private static final String TAG = "FollowerAdapter";
    private List<FollowsItem> list;


    public FollowListAdapter() {

    }

    public FollowListAdapter(List<FollowsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FollowListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.followlist_item, parent, false);
        return new FollowListAdapter.MyViewHolder((FollowlistItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowListAdapter.MyViewHolder holder, int position) {


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
