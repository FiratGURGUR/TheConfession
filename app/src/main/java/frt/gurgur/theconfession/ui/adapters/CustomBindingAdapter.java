package frt.gurgur.theconfession.ui.adapters;

import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.like.LikeButton;

import frt.gurgur.theconfession.R;

public class CustomBindingAdapter {


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        if (url == null) {
            view.setImageDrawable(null);
        } else {
            //Picasso.get().load(url).into(view);
            Glide.with(view.getContext())
                    .load(url)
                    .into(view);
        }
    }


    @BindingAdapter("animationFav")
    public static void setFav(LikeButton view, String like) {
        view.setUnlikeDrawableRes(R.drawable.m_fav);
        view.setLikeDrawableRes(R.drawable.heart_on);
        if (like.equals("true")) {
            //dolu
            view.setLiked(true);
        } else {
           //boş
            view.setLiked(false);
        }
    }

    @BindingAdapter("followText")
    public static void setFollowText(Button textView, String follow) {
         if (follow != null && follow.equals("true")){
             textView.setText(R.string.profile_unfollow);
         }else{
             textView.setText(R.string.profile_follow);
         }
    }




}
