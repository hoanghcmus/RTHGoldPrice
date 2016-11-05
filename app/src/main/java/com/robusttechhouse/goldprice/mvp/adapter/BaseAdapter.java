package com.robusttechhouse.goldprice.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robusttechhouse.goldprice.mvp.holder.BaseViewHolder;
import com.robusttechhouse.goldprice.mvp.pojo.BaseModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Common adapter for the application
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public abstract class BaseAdapter<M extends BaseModel, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    Class<VH> mViewHolderClass;

    int mModelLayout;

    List<M> mListData = new ArrayList<>();

    public List<M> getData() {
        return mListData;
    }

    public BaseAdapter(int modelLayout, Class<VH> viewHolderClass) {
        mViewHolderClass = viewHolderClass;
        mModelLayout = modelLayout;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        M model = getItem(position);
        populateViewHolder(holder, model, position);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public M getItem(int position) {
        return mListData.get(position);
    }

    public void setData(List<M> listData) {
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    public void addItemTop(M item) {
        if (!mListData.contains(item)) {
            mListData.add(0, item);
        }
        notifyDataSetChanged();
    }

    /**
     * This method is used to populate view with given data model
     *
     * @param viewHolder Holder corresponding {@code model}
     * @param model      Model corresponding {@code viewHolder}
     * @param position   Position of the item inside the list of items
     */
    abstract protected void populateViewHolder(VH viewHolder, M model, int position);
}