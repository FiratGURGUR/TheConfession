package frt.gurgur.theconfession.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.GiphyListItemBinding;
import frt.gurgur.theconfession.model.post.giphy.DataItem;


public class GiphyListAdapter extends RecyclerView.Adapter<GiphyListAdapter.MyViewHolder> {


    private List<DataItem> list;


    public GiphyListAdapter() {

    }

    public GiphyListAdapter(List<DataItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GiphyListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding;

        binding = DataBindingUtil.inflate(inflater, R.layout.giphy_list_item, parent, false);
        return new GiphyListAdapter.MyViewHolder((GiphyListItemBinding) binding);

    }

    @Override
    public void onBindViewHolder(@NonNull GiphyListAdapter.MyViewHolder holder, int position) {


        GiphyListItemBinding binding = holder.binding;
        binding.setGiphy(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private GiphyListItemBinding binding;

        public MyViewHolder(GiphyListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }


}
