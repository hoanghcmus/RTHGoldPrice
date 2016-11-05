package com.robusttechhouse.goldprice.mvp.goldpricelist;

import android.graphics.Color;
import android.view.View;

import com.robusttechhouse.goldprice.R;
import com.robusttechhouse.goldprice.mvp.adapter.BaseAdapter;
import com.robusttechhouse.goldprice.mvp.holder.GoldPriceViewHolder;
import com.robusttechhouse.goldprice.mvp.pojo.GoldModel;
import com.robusttechhouse.goldprice.rest.RestClient;
import com.robusttechhouse.goldprice.rest.UserService;
import com.robusttechhouse.goldprice.rx.SimpleSubscriber;
import com.robusttechhouse.goldprice.utils.LogUtils;
import com.robusttechhouse.goldprice.utils.NetworkUtils;
import com.robusttechhouse.goldprice.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for {@link com.robusttechhouse.goldprice.mvp.goldpricelist.GoldPriceListContract.GoldPriceListView}
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class GoldPriceListPresenterImpl implements GoldPriceListContract.GoldPriceListPresenter {
    private GoldPriceListFragment mGoldPriceListView;
    private UserService mUserService;
    private Subscription mSubscription;
    private BaseAdapter<GoldModel, GoldPriceViewHolder> mBaseAdapter;

    public GoldPriceListPresenterImpl(GoldPriceListFragment mGoldPriceListFragment) {
        this.mGoldPriceListView = mGoldPriceListFragment;
    }

    @Override
    public void onAttach(GoldPriceListContract.GoldPriceListView view) {

    }

    @Override
    public void onDetach(GoldPriceListContract.GoldPriceListView view) {

    }

    @Override
    public void init() {
        initTime();

        mUserService = RestClient.getInstance().getUserService();

        mBaseAdapter = new BaseAdapter<GoldModel, GoldPriceViewHolder>(
                R.layout.item_gold_price,
                GoldPriceViewHolder.class) {

            @Override
            protected void populateViewHolder(GoldPriceViewHolder viewHolder, GoldModel gold, int position) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMMM yyyy");
                String date = formatter.format(gold.getDate());
                String amount = String.format("$%1$d", (int) gold.getAmount());

                viewHolder.mTxtDate.setText(date);
                viewHolder.mTxtAmount.setText(amount);

                if (mGoldPriceListView != null)
                    mGoldPriceListView.initGoldPriceItemTextFont(viewHolder);
            }
        };

        if (mGoldPriceListView != null) {
            if (NetworkUtils.isNetworkConnected(mGoldPriceListView.getContext())) {
                mGoldPriceListView.showProgressDialog();
                fetchGoldPriceList();
            } else {
                mGoldPriceListView.adjustDisplayOfListGoldPriceSection(true);
                String message = mGoldPriceListView.getStr(R.string.msg_error_fail_to_load_gold_list_without_network_connection);
                mGoldPriceListView.setMessage(message);
            }
        }
    }

    private void fetchGoldPriceList() {
        unSubscribe();
        mSubscription = mUserService.getGolds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<GoldModel>>() {
                    @Override
                    public void onNext(List<GoldModel> golds) {
                        if (mGoldPriceListView != null) {
                            mGoldPriceListView.hideProgressDialog();

                            if (golds != null && !golds.isEmpty()) {
                                Collections.sort(golds);
                                mBaseAdapter.setData(golds);
                                mGoldPriceListView.setListGoldPrice(mBaseAdapter);

                                initGoldPriceChart(golds);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mGoldPriceListView != null) {
                            mGoldPriceListView.hideProgressDialog();
                            String message = mGoldPriceListView.getStr(R.string.msg_error_fail_to_load_gold_list);
                            mGoldPriceListView.adjustDisplayOfListGoldPriceSection(true);
                            mGoldPriceListView.setMessage(message + e.toString());
                        }
                        LogUtils.e(e.toString());
                    }
                });
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            if (!mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
            mSubscription = null;
        }
    }

    private void initTime() {
        if (mGoldPriceListView != null) {
            Calendar calendar = Calendar.getInstance();

            String year = StringUtils.getYearString(calendar.get(Calendar.YEAR));
            String month = StringUtils.getMonthString(calendar.get(Calendar.MONTH));
            String dayOfMonth = StringUtils.getDayOfMonthString(calendar.get(Calendar.DATE));
            String dayOfWeek = StringUtils.getDayOfWeekString(calendar.get(Calendar.DAY_OF_WEEK));

            mGoldPriceListView.setTime(dayOfMonth, dayOfWeek, String.format("%1$s %2$s", month, year));
        }
    }

    private void initGoldPriceChart(List<GoldModel> goldModelList) {
        if (mGoldPriceListView != null) {
            List<PointValue> values = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            for (GoldModel model : goldModelList) {
                cal.setTime(model.getDate());
                values.add(new PointValue(cal.get(Calendar.DATE), model.getAmount()));
            }

            Line line = new Line(values)
                    .setColor(mGoldPriceListView.getResources().getColor(R.color.colorPrimaryDark))
                    .setCubic(true)
                    .setHasLabelsOnlyForSelected(true)
                    .setPointRadius(4);
            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);

            mGoldPriceListView.setChartData(data);
        }
    }
}
