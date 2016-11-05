package com.robusttechhouse.goldprice.mvp.holder;

import android.view.View;
import android.widget.TextView;

import com.robusttechhouse.goldprice.R;

import butterknife.BindView;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class GoldPriceViewHolder extends BaseViewHolder {
    @BindView(R.id.txt_date)
    public TextView mTxtDate;

    @BindView(R.id.txt_amount)
    public TextView mTxtAmount;

    public GoldPriceViewHolder(View itemView) {
        super(itemView);
    }
}
