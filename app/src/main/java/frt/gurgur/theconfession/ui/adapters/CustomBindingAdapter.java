package frt.gurgur.theconfession.ui.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;
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




}
