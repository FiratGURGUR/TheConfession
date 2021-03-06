package frt.gurgur.theconfession.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;

import javax.inject.Inject;

import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.databinding.PostListItemBinding;
import frt.gurgur.theconfession.databinding.PostListItemImageBinding;
import frt.gurgur.theconfession.model.main.DataItem;
import frt.gurgur.theconfession.ui.listeners.CommentClickListener;
import frt.gurgur.theconfession.ui.listeners.FavClickListener;
import frt.gurgur.theconfession.ui.listeners.HashtagClickListener;
import frt.gurgur.theconfession.ui.listeners.OnItemClickListener;
import frt.gurgur.theconfession.ui.listeners.ProfileClickListener;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_PHOTO = 2;


    private List<DataItem> postList;
    OnItemClickListener listener;
    FavClickListener favClickListener;
    CommentClickListener commentClickListener;
    ProfileClickListener profileClickListener;
    HashtagClickListener hashtagClickListener;

    Context context;


    public PostListAdapter() {

    }

    @Inject
    public PostListAdapter(Context context,List<DataItem> postList, OnItemClickListener listener, FavClickListener favClickListener,CommentClickListener commentClickListener,ProfileClickListener profileClickListener,HashtagClickListener hashtagClickListener) {
        this.postList = postList;
        this.listener = listener;
        this.favClickListener = favClickListener;
        this.commentClickListener = commentClickListener;
        this.profileClickListener = profileClickListener;
        this.context=context;
        this.hashtagClickListener=hashtagClickListener;
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
            HashTagHelper mTextHashTagHelper;
            mTextHashTagHelper = HashTagHelper.Creator.create(context.getResources().getColor(R.color.blue_active), new HashTagHelper.OnHashTagClickListener() {
                @Override
                public void onHashTagClicked(String hashTag) {
                    hashtagClickListener.clickHashtag(hashTag);
                }
            });
            mTextHashTagHelper.handle(bindingText.txtContent);
            bindingText.starButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost(),getAdapterPosition());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost(),getAdapterPosition());
                }
            });

            bindingText.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClickListener.openCommentClick(binding.getPost().getId());
                }
            });

            bindingText.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileClickListener.showProfile(binding.getPost().getUserId());
                }
            });
        }

        public MyViewHolder(PostListItemImageBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.bindingImage = binding;

            HashTagHelper mTextHashTagHelper;
            mTextHashTagHelper = HashTagHelper.Creator.create(context.getResources().getColor(R.color.blue_active), new HashTagHelper.OnHashTagClickListener() {
                @Override
                public void onHashTagClicked(String hashTag) {
                    hashtagClickListener.clickHashtag(hashTag);
                }
            });
            mTextHashTagHelper.handle(bindingImage.txtContent);

            bindingImage.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(binding.getPost());
                }
            });
            bindingImage.starButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost(),getAdapterPosition());
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    favClickListener.favClick(binding.getPost(),getAdapterPosition());
                }
            });

            bindingImage.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClickListener.openCommentClick(binding.getPost().getId());
                }
            });

            bindingImage.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profileClickListener.showProfile(binding.getPost().getUserId());
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
