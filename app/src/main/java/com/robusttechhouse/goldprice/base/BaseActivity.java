package com.robusttechhouse.goldprice.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.common.FRAGMENT_ID;
import com.robusttechhouse.goldprice.mvp.goldpricelist.GoldPriceListFragment;

import butterknife.ButterKnife;

/**
 * Base Activity for the application
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class BaseActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void showProgressDialog() {

        if (isDialogProgressShowing()) {
            return;
        }
        if (!isFinishing()) {
            mProgressDialog = new Dialog(this);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mProgressDialog.setContentView(R.layout.dialog_loading);
            mProgressDialog.getWindow().getAttributes().width = WindowManager.LayoutParams.WRAP_CONTENT;

            ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(android.R.color.transparent), android.graphics.PorterDuff.Mode.SRC_ATOP);

            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (mProgressDialog == null)
            return;
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    /**
     * Check if progress dialog is showing or not
     *
     * @return
     */
    private boolean isDialogProgressShowing() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return true;
        }
        return false;
    }

    /**
     * Get string resource from <i>strings.xml</i>
     *
     * @param stringResID id of string resource
     * @return string corresponding {@code stringResID}
     */
    protected String getStr(int stringResID) {
        return getString(stringResID);
    }

    /**
     * Get container Id that used to add or replace a fragment
     *
     * @return Id of container (Id of FrameLayout)
     */
    protected int getContainerID() {
        return R.id.main_container;
    }

    /**
     * Base function show message for all activity and fragment
     *
     * @param msg
     */
    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Base function show message for all activity and fragment
     *
     * @param msg
     */
    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Get current display fragment from BackStack if it has added to BackStack with a tag
     *
     * @return Current display fragment
     */
    protected Fragment getCurrentFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * Attach fragment to this activity
     *
     * @param attachedFragment Fragment need to attach
     * @param containerID      Id of container that contain fragment
     */
    protected void attachFragment(Fragment attachedFragment, int containerID, String fragmentName) {
        if (!isFinishing()) {
            if (mFragmentManager != null && attachedFragment != null) {
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.add(containerID, attachedFragment, fragmentName);
                mFragmentTransaction.commitAllowingStateLoss();
            }
        }

    }

    /**
     * Replace current fragment with another fragment
     *
     * @param newFragment  Fragment need to replace
     * @param containerID  Container Id
     * @param isBackStack  If true, add fragment to BackStack and can go back later.
     *                     Otherwise, cannot go back.
     * @param fragmentName Tag string for fragment, will be use when call {@code findFragmentByTag}
     */
    protected void replaceFragment(Fragment newFragment, int containerID, boolean isBackStack, String fragmentName) {
        if (!isFinishing()) {
            if (mFragmentManager != null && newFragment != null) {
                mFragmentTransaction = mFragmentManager.beginTransaction();

                mFragmentTransaction.replace(containerID, newFragment, fragmentName);
                if (isBackStack)
                    mFragmentTransaction.addToBackStack(fragmentName);
                mFragmentTransaction.commitAllowingStateLoss();
            }
        }
    }

    protected BaseFragment getFragment(FRAGMENT_ID id) {
        BaseFragment fragment = null;
        if (id == FRAGMENT_ID.GOLD_PRICE_LIST_FRAGMENT) {
            fragment = new GoldPriceListFragment();
        }
        return fragment;
    }

    protected void setToolbarTitle(String title) {
        setTitle(title);
    }

    protected void setToolbarTitle(int resId) {
        setTitle(resId);
    }
}
