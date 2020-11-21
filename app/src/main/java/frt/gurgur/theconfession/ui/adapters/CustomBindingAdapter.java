package frt.gurgur.theconfession.ui.adapters;

import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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

            Picasso.get().load(R.drawable.m_fav_true).into(view, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    DrawableCompat.setTint(view.getDrawable(), view.getContext().getResources().getColor(R.color.red_active));
                }

                @Override
                public void onError(Exception e) {

                }

            });


        } else {
            Picasso.get().load(R.drawable.m_fav).into(view);
        }
    }

}
