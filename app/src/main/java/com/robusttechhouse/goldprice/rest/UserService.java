package com.robusttechhouse.goldprice.rest;

import com.robusttechhouse.goldprice.mvp.pojo.GoldModel;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Rest APIs
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public interface UserService {
    /**
     * Content-Type: application/json
     * X-App-Token: 76524a53ee60602ac3528f38
     */
    @GET("/prices/chart_data")
    Observable<List<GoldModel>> getGolds();

}
