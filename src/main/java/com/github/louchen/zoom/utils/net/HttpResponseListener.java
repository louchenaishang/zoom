package com.github.louchen.zoom.utils.net;

import okhttp3.Request;

public interface HttpResponseListener {

    /**
     * 成功回调
     * @param request
     * @param response
     */
    void onSuccess(Request request, HttpResponse response);

    /**
     * 失败回调
     * @param request
     * @param response
     */
    void onFailure(Request request, HttpResponse response);

}
