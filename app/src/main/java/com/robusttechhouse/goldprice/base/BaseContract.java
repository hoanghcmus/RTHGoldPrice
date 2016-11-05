package com.robusttechhouse.goldprice.base;

/**
 * This is used for MVP pattern
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class BaseContract {

    public interface BasePresenter<V extends BaseView> {

        void onAttach(V view);

        void onDetach(V view);
    }

    public interface BaseView {
        void showProgress();

        void hideProgress();
    }
}