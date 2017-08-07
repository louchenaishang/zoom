package com.github.louchen.zoom.utils.net;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * net - 网络请求客户端,默认实现
 *
 * @author louchen
 */
public class DefaultHttpClient implements HttpClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml; charset=utf-8");

    protected static DefaultHttpClient client;

    protected OkHttpClient okHttpClient;

    /**
     * 内部构造函数
     */
    private DefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(128);
        dispatcher.setMaxRequestsPerHost(10);

        builder.dispatcher(dispatcher);
        builder.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(10, 10, TimeUnit.MINUTES));

        builder.retryOnConnectionFailure(true);

//        builder.sslSocketFactory(createSSLSocketFactory());

        this.okHttpClient = builder.build();
    }

//    private SSLSocketFactory createSSLSocketFactory(){
//        SSLContext sslContext = null;
//        try {
//            KeyStore clientStore = KeyStore.getInstance("PKCS12");
//            clientStore.load(new FileInputStream(certPath), certPassword.toCharArray());
//
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            kmf.init(clientStore, "testPass".toCharArray());
//            KeyManager[] kms = kmf.getKeyManagers();
//
//            KeyStore trustStore = KeyStore.getInstance("JKS");
//            trustStore.load(new FileInputStream("cacerts"), "changeit".toCharArray());
//
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            tmf.init(trustStore);
//            TrustManager[] tms = tmf.getTrustManagers();
//
//            sslContext = null;
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(kms, tms, new SecureRandom());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return sslContext.getSocketFactory();
//    }

    /**
     * 获取单例
     *
     * @return
     */
    public static DefaultHttpClient getInstance() {
        if (client == null) {
            synchronized (DefaultHttpClient.class) {
                if (client == null) {
                    client = new DefaultHttpClient();
                }
            }
        }
        return client;
    }

    @Override
    public HttpResponse getSync(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return sync(request);
    }

    @Override
    public void getAsync(String url, HttpResponseListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        async(request, listener);
    }

    @Override
    public HttpResponse postSync(String url, String json) {
        return postSync(url, json, MEDIA_TYPE_JSON);
    }

    @Override
    public HttpResponse postSync(String url, String data, MediaType mediaType) {
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return sync(request);
    }

    @Override
    public void postAsync(String url, String json, HttpResponseListener listener) {
        postAsync(url, json, MEDIA_TYPE_JSON, listener);
    }

    @Override
    public void postAsync(String url, String data, MediaType mediaType, HttpResponseListener listener) {
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        async(request, listener);
    }

    /**
     * 同步发送
     *
     * @param request
     * @return
     */
    public HttpResponse sync(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            return parseResponse(response);
        } catch (Throwable t) {
            return new HttpResponse(-1, null, t);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
    }

    /**
     * 异步发送
     *
     * @param request
     * @param listener
     */
    public void async(Request request, HttpResponseListener listener) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(request, new HttpResponse(-1, null, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final HttpResponse hr = parseResponse(response);
                if (response.isSuccessful()) {
                    listener.onSuccess(request, hr);
                } else {
                    listener.onFailure(request, hr);
                }
            }
        });
    }

    /**
     * 封装okhttp的response
     *
     * @param response
     * @return
     * @throws IOException
     */
    public HttpResponse parseResponse(Response response) throws IOException {
        return new HttpResponse(response.code(), response.body() != null ? response.body().string() : null, null);
    }

}
