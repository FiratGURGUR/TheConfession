package frt.gurgur.theconfession.ui.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import frt.gurgur.theconfession.R;

public class CustomBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        if (url == null) {
            view.setImageDrawable(null);
        } else {
            Picasso.get().load(url).into(view);
        }
    }


    @BindingAdapter("likeStatus")
    public static void setLike(ImageView view, String like) {
        if (like.equals("true")) {
            Picasso.get().load(R.drawable.m_fav_true).into(view);
        } else {
            Picasso.get().load(R.drawable.m_fav).into(view);
        }
    }

}
