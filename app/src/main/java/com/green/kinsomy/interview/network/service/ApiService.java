package com.green.kinsomy.interview.network.service;

import com.green.kinsomy.interview.model.ResponseResult;
import com.green.kinsomy.interview.model.WordResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kinsomy on 2017/10/19.
 */

public interface ApiService {

    @GET("bdc/search/")
    Observable<ResponseResult<WordResult>> search(@Query("word") String word);

}
