package frt.gurgur.theconfession.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import frt.gurgur.theconfession.R;

public class Common {

    public static Dialog customAlert(Context context, String title, String msg, String btnText, int textGravity,
                                     boolean isHtml) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_alert_dialog);

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.txtAlertText);
        if (isHtml) {
            text.setText(Html.fromHtml(msg));
            text.setClickable(true);
            text.setLinksClickable(true);
            text.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            text.setText(msg);
        }

        text.setGravity(textGravity);

        Button dialogButton = dialog.findViewById(R.id.dialogButton1);
        dialogButton.setText(btnText);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog customAlert(Context context, String title, String msg, String btnText, int textGravity,
                                     boolean isHtml, Handler handler, Runnable runnable, Toast toast) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_alert_dialog);

        if(toast != null){
            toast.cancel();
        }

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.txtAlertText);
        if (isHtml) {
            text.setText(Html.fromHtml(msg));
            text.setClickable(true);
            text.setLinksClickable(true);
            text.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            text.setText(msg);
        }

        text.setGravity(textGravity);

        Button dialogButton = dialog.findViewById(R.id.dialogButton1);
        dialogButton.setText(btnText);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                handler.post(runnable);
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        if(toast != null){
            toast.cancel();
        }
        dialog.show();
        return dialog;
    }

    public static void customAlert(Context context, String msg, String btnText, int textGravity,
                                   boolean isHtml, boolean cancelable) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.setCancelable(cancelable);

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.txtAlertText);
        TextView titlee = dialog.findViewById(R.id.txtAlertTitle);
        if (isHtml) {
            text.setText(Html.fromHtml(msg));
            text.setClickable(true);
            text.setLinksClickable(true);
            text.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            text.setText(msg);
        }

        text.setGravity(textGravity);

        Button dialogButton = dialog.findViewById(R.id.dialogButton1);
        dialogButton.setText(btnText);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public static Dialog customAlert(Context context, String title, String msg, String btnText, String btnText2,
                                     View.OnClickListener positive, View.OnClickListener negative) {
        Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.custom_alert_dialog);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.txtAlertText);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton1);
        Button dialogButton2 = (Button) dialog.findViewById(R.id.dialogButton2);
        dialogButton.setText(btnText);
        if (btnText2 != null) {
            dialogButton2.setText(btnText2);
            dialogButton2.setVisibility(View.VISIBLE);
        } else {
            dialogButton2.setVisibility(View.GONE);
        }

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(positive);
        if (negative != null) dialogButton2.setOnClickListener(negative);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void customAlert(Context context, String title, String msg, String btnText) {
        customAlert(context, title, msg, btnText, Gravity.CENTER, false);
    }

    public static void customAlert(Context context, String msg, String btnText, boolean cancelable) {
        customAlert(context, msg, btnText, Gravity.CENTER, false, cancelable);
    }



}
