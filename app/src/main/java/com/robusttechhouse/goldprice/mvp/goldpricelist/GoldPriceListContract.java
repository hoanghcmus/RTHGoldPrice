package com.robusttechhouse.goldprice.mvp.goldpricelist;

import com.robusttechhouse.goldprice.base.BaseContract;
import com.robusttechhouse.goldprice.mvp.adapter.BaseAdapter;
import com.robusttechhouse.goldprice.mvp.holder.GoldPriceViewHolder;
import com.robusttechhouse.goldprice.mvp.pojo.GoldModel;

import lecho.lib.hellocharts.model.LineChartData;

/**
 * Contract for {@link GoldPriceListFragment} and {@link GoldPriceListPresenterImpl}
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class GoldPriceListContract {

    public interface GoldPriceListPresenter extends BaseContract.BasePresenter<GoldPriceListView> {

        void init();

        void unSubscribe();
    }

    public interface GoldPriceListView extends BaseContract.BaseView {

        void initGoldPriceItemTextFont(GoldPriceViewHolder viewHolder);

        void setTime(String dayOfMonth, String dayOfWeek, String monthAndYear);

        void setListGoldPrice(BaseAdapter<GoldModel, GoldPriceViewHolder> mGoldPriceAdapter);

        void setChartData(LineChartData data);

        void adjustDisplayOfListGoldPriceSection(boolean isListGoldEmpty);

        void setMessage(String message);
    }
}
