package frt.gurgur.theconfession.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import static frt.gurgur.theconfession.util.Constants.GENERATED_PHOTO_URL;

public class Helper {

    public static String generateUserPhoto(String fullName){
        int idx = fullName.lastIndexOf(' ');
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        return GENERATED_PHOTO_URL + firstName + "+" + lastName;
    }


    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static void keyboardVisibility(final Context context, final View rootView, final Constants.KeyboardVisibility listener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > dpToPx(context, 300))  // if more than 200 dp, it's probably a keyboard...
                    listener.onKeyboardOpen();
                else
                    listener.onKeyboardClose();
            }
        });
    }

}
