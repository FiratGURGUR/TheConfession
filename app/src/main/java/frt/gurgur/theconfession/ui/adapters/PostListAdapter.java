package frt.gurgur.theconfession.ui.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.PostListItemBinding;
import frt.gurgur.theconfession.databinding.PostListItemImageBinding;
import frt.gurgur.theconfession.model.main.DataItem;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_PHOTO = 2;

    private static final String TAG = "PostListAdapter";
    private List<DataItem> postList;
    OnItemClickListener listener;
    FavClickListener favClickListener;

    public PostListAdapter() {

    }

    public PostListAdapter(List<DataItem> postList, OnItemClickListener listener, FavClickListener favClickListener) {
        this.postList = postList;
        this.listener = listener;
        this.favClickListener = favClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding;
        switch (viewType) {
            case TYPE_TEXT:
                binding = DataBindingUtil.inflate(inflater, R.layout.post_list_item, parent, false);
                return new MyViewHolder((PostListItemBinding) binding);
            case TYPE_PHOTO:
                binding = DataBindingUtil.inflate(inflater, R.layout.post_list_item_image, parent, false);
                return new MyViewHolder((PostListItemImageBinding) binding, listener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_TEXT:
                PostListItemBinding bindingText = holder.bindingText;
                bindingText.setPost(postList.get(position));
                break;
            case TYPE_PHOTO:
                PostListItemImageBinding bindingImage = holder.bindingImage;
                bindingImage.setPost(postList.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private PostListItemBinding bindingText;
        private PostListItemImageBinding bindingImage;

        public MyViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.bindingText = binding;

            bindingText.starButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost());
                }
            });



        }

        public MyViewHolder(PostListItemImageBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.bindingImage = binding;
            bindingImage.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(binding.getPost());
                }
            });
            bindingImage.starButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost());
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (postList.get(position).getIsWithImage() == 0) {
            return TYPE_TEXT;
        } else {
            return TYPE_PHOTO;
        }
    }
}
