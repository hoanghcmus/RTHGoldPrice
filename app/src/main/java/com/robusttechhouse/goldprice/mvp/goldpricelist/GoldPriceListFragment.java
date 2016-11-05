package com.robusttechhouse.goldprice.mvp.goldpricelist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.base.BaseFragment;
import com.robusttechhouse.goldprice.mvp.adapter.BaseAdapter;
import com.robusttechhouse.goldprice.mvp.holder.GoldPriceViewHolder;
import com.robusttechhouse.goldprice.mvp.pojo.GoldModel;
import com.robusttechhouse.goldprice.utils.CommonUtils;
import com.robusttechhouse.goldprice.utils.FontUtils;
import com.robusttechhouse.goldprice.utils.LogUtils;

import butterknife.BindView;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class GoldPriceListFragment extends BaseFragment
        implements GoldPriceListContract.GoldPriceListView {

    @BindView(R.id.txt_day_of_month)
    TextView mTxtDayOfMonth;

    @BindView(R.id.txt_day_of_week)
    TextView mTxtDayOfWeek;

    @BindView(R.id.txt_month_and_year)
    TextView mTxtMonthAndYear;

    @BindView(R.id.txt_message)
    TextView mTxtMessage;

    @BindView(R.id.rcl_gold_price_list)
    RecyclerView mRclGoldPriceList;

    private GoldPriceListContract.GoldPriceListPresenter mGoldPriceListPresenter;
    private LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.chart)
    LineChartView mLineChartViewGold;

    private NetworkStateReceiver mNetworkStateReceiver;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_gold_price;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoldPriceListPresenter = new GoldPriceListPresenterImpl(this);
        IntentFilter intentFilterNetworkChange = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkStateReceiver = new NetworkStateReceiver(mGoldPriceListPresenter);
        getActivity().registerReceiver(mNetworkStateReceiver, intentFilterNetworkChange);
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.title_gold_price_list_fragment);
        initTextFontForTextView();
        initListView();
        if (mGoldPriceListPresenter != null) {
            mGoldPriceListPresenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getActivity().unregisterReceiver(mNetworkStateReceiver);
        } catch (Exception e) {
            LogUtils.e("Unregister network receiver exception: " + e.getMessage());
        }
        if (mGoldPriceListPresenter != null) {
            mGoldPriceListPresenter.unSubscribe();
        }
    }

    private void initListView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRclGoldPriceList.setLayoutManager(mLinearLayoutManager);
    }

    private void initTextFontForTextView() {
        CommonUtils.setFontForTextView(mTxtDayOfMonth, FontUtils.FONTLIST.Myriad_Pro_Bold);
        CommonUtils.setFontForTextView(mTxtDayOfWeek, FontUtils.FONTLIST.Myriad_Pro_Bold);
        CommonUtils.setFontForTextView(mTxtMonthAndYear, FontUtils.FONTLIST.Myriad_Pro_Regular);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void initGoldPriceItemTextFont(GoldPriceViewHolder viewHolder) {
        CommonUtils.setFontForTextView(viewHolder.mTxtAmount, FontUtils.FONTLIST.Myriad_Pro_Bold);
        CommonUtils.setFontForTextView(viewHolder.mTxtDate, FontUtils.FONTLIST.Myriad_Pro_Regular);
    }

    @Override
    public void setTime(String dayOfMonth, String dayOfWeek, String monthAndYear) {
        mTxtDayOfMonth.setText(dayOfMonth);
        mTxtDayOfWeek.setText(dayOfWeek);
        mTxtMonthAndYear.setText(monthAndYear);
    }

    @Override
    public void setListGoldPrice(BaseAdapter<GoldModel, GoldPriceViewHolder> mGoldPriceAdapter) {
        mRclGoldPriceList.setAdapter(mGoldPriceAdapter);
        boolean isListGoldEmpty = mGoldPriceAdapter.getItemCount() == 0;
        adjustDisplayOfListGoldPriceSection(isListGoldEmpty);
        setMessage(getStr(isListGoldEmpty ? R.string.msg_info_list_gold_empty : R.string.empty));
    }

    @Override
    public void setChartData(LineChartData data) {
        mLineChartViewGold.setLineChartData(data);
    }

    @Override
    public void adjustDisplayOfListGoldPriceSection(boolean isListGoldEmpty) {
        mTxtMessage.setVisibility(isListGoldEmpty ? View.VISIBLE : View.GONE);
        mRclGoldPriceList.setVisibility(isListGoldEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setMessage(String message) {
        mTxtMessage.setText(message);
    }

    public static class NetworkStateReceiver extends BroadcastReceiver {
        private GoldPriceListContract.GoldPriceListPresenter mGoldPriceListPresenter;
        private static boolean isFirstTime = true;

        public NetworkStateReceiver(GoldPriceListContract.GoldPriceListPresenter goldPriceListPresenter) {
            this.mGoldPriceListPresenter = goldPriceListPresenter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    if (!isFirstTime) {
                        LogUtils.e("Network connected");
                        Toast.makeText(context,
                                context.getResources().getString(R.string.msg_network_connected),
                                Toast.LENGTH_SHORT).show();
                        if (mGoldPriceListPresenter != null) {
                            mGoldPriceListPresenter.init();
                        }
                    }
                    isFirstTime = false;
                } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                    LogUtils.e("Network disconnected");
                    Toast.makeText(context,
                            context.getResources().getString(R.string.msg_network_dis_connected),
                            Toast.LENGTH_SHORT).show();
                    isFirstTime = false;
                }
            }
        }
    }
}
