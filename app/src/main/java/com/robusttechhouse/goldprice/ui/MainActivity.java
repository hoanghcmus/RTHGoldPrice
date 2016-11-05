package com.robusttechhouse.goldprice.ui;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.base.BaseActivity;
import com.robusttechhouse.goldprice.base.BaseFragment;
import com.robusttechhouse.goldprice.common.FRAGMENT_ID;
import com.robusttechhouse.goldprice.common.RTHDialogManager;
import com.robusttechhouse.goldprice.mvp.pojo.ProfileModel;
import com.robusttechhouse.goldprice.utils.CommonUtils;
import com.robusttechhouse.goldprice.utils.FontUtils;
import com.robusttechhouse.goldprice.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private TextView mToolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolBar);
        initToolbarIcon();
        mToolBarTitle = (TextView) mToolBar.findViewById(R.id.txt_toolbar_title);
        CommonUtils.setFontForTextView(mToolBarTitle, FontUtils.FONTLIST.Myriad_Pro_Bold);

        BaseFragment goldPriceListFragment = getFragment(FRAGMENT_ID.GOLD_PRICE_LIST_FRAGMENT);
        attachFragment(goldPriceListFragment, getContainerID(), FRAGMENT_ID.GOLD_PRICE_LIST_FRAGMENT.getKey());
    }

    @OnClick(R.id.ll_my_profile)
    void showMyProfileDialog() {
        displaySideMenu();
        String fullName = getStr(R.string.profile_user_name);
        String email = getStr(R.string.profile_user_email);
        ProfileModel profileModel = new ProfileModel("", fullName, email);
        RTHDialogManager.shareInstance().showMyProfileDialog(this, profileModel);
    }

    public void setToolbarTitle(String title) {
        setTitle("");
        if (mToolBarTitle == null) return;
        mToolBarTitle.setText(title);
    }

    public void setToolbarTitle(int resId) {
        setTitle("");
        setToolbarTitle(getStr(resId));
    }

    private void initToolbarIcon() {
        if (mToolBar != null) {
            mToolBar.setLogo(R.drawable.info_icon);
            for (int i = 0; i < mToolBar.getChildCount(); i++) {
                View child = mToolBar.getChildAt(i);
                if (child != null) {
                    if (child.getClass() == ImageView.class) {
                        ImageView iv = (ImageView) child;
                        if (iv.getDrawable() == mToolBar.getLogo()) {
                            int logoDimen = (int) CommonUtils.convertDpToPixel(getResources().getDimension(R.dimen._10));
                            LogUtils.d("logoDimen: " + logoDimen);
                            iv.getLayoutParams().height = logoDimen;
                            iv.getLayoutParams().width = logoDimen;
                            iv.requestLayout();
                            iv.setAdjustViewBounds(true);
                            iv.setOnClickListener(mNavigationOnClickListener);
                            break;
                        }
                    }
                }
            }
        }
    }

    private View.OnClickListener mNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displaySideMenu();
        }
    };

    private void displaySideMenu() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
