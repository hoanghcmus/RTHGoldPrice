package com.robusttechhouse.goldprice.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.base.BaseActivity;
import com.robusttechhouse.goldprice.common.RTHDialogManager;
import com.robusttechhouse.goldprice.utils.CommonUtils;
import com.robusttechhouse.goldprice.utils.FontUtils;
import com.robusttechhouse.goldprice.utils.NetworkUtils;

import butterknife.BindView;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.img_butterfly)
    ImageView mImgButterfly;

    @BindView(R.id.txt_splash_text)
    TextView mTxtSplashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        CommonUtils.setFontForTextView(mTxtSplashText, FontUtils.FONTLIST.Myriad_Pro_Bold);

        AnimationDrawable animation = (AnimationDrawable) mImgButterfly.getDrawable();
        animation.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If network connected --> Go inside the app
        if (NetworkUtils.isNetworkConnected(this)) {
            splash(3000);
        }
        // Network is not connected --> Ask user
        else {
            RTHDialogManager.shareInstance().showConfirmDialog(this,
                    getStr(R.string.app_name),
                    getStr(R.string.msg_confirm_continue_or_not_without_network_connection),
                    // User would to continue without internet
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            splash(2000);
                        }
                    },
                    // User doesn't want to continue --> Close the app
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SplashActivity.this.finish();
                        }
                    });
        }
    }

    private void splash(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, delay);
    }

    private void gotoMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }
}
