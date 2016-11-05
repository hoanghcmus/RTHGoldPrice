package com.robusttechhouse.goldprice.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.mvp.pojo.ProfileModel;
import com.robusttechhouse.goldprice.utils.LogUtils;
import com.squareup.picasso.Picasso;

/**
 * Singleton Pattern used to manage all dialog of the application
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class RTHDialogManager {

    private static RTHDialogManager rthDialogManager;

    public static RTHDialogManager shareInstance() {
        if (rthDialogManager == null) {
            rthDialogManager = new RTHDialogManager();
        }
        return rthDialogManager;
    }

    /**
     * Display dialog that show user profile information
     *
     * @param context
     * @param profileModel
     */
    public void showMyProfileDialog(Context context, ProfileModel profileModel) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_my_profile, null);
            builder.setView(dialogView);

            final AlertDialog dialog = builder.create();

            ImageView imgAvatar = (ImageView) dialogView.findViewById(R.id.img_avatar);
            TextView txtFullName = (TextView) dialogView.findViewById(R.id.txt_full_name);
            TextView txtEmail = (TextView) dialogView.findViewById(R.id.txt_user_email);
            Button btnClose = (Button) dialogView.findViewById(R.id.btn_close);

            if (profileModel != null) {
                if (!profileModel.getAvatar().equals(""))
                    Picasso.with(context).load(profileModel.getAvatar()).into(imgAvatar);
                txtFullName.setText(profileModel.getFullName());
                txtEmail.setText(profileModel.getEmail());
            }

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            LogUtils.e("Exception: " + e.getMessage());
        }
    }

    /**
     * Show dialog as confirm popup with 2 options {@code Positive} and {@code Negative}
     *
     * @param context          Given context
     * @param title            Dialog's title
     * @param message          Dialog's message
     * @param positiveListener Callback for positive button
     * @param negativeListener Callback for negative button
     */
    public void showConfirmDialog(Context context,
                                  String title,
                                  String message,
                                  DialogInterface.OnClickListener positiveListener,
                                  DialogInterface.OnClickListener negativeListener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.yes), positiveListener)
                .setNegativeButton(context.getString(R.string.no), negativeListener).show();
    }
}
