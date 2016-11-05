package com.robusttechhouse.goldprice.mvp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}