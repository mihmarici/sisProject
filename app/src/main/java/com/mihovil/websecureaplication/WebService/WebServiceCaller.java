package com.mihovil.websecureaplication.WebService;

import android.util.Log;

import com.mihovil.websecureaplication.Models.UserModel;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Mihovil on 10.1.2018..
 */
public class WebServiceCaller {
  private OnServiceFinished listener;

    private Retrofit retrofit;
    private APIinterface serviceCaller;
    private Call<ServiceResponse> call;

    public static OkHttpClient OkHttpSSL() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            okHttpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .header("user-agent", "Android")
                            .header("Basic","ovojenasAPIkljuc")
                            .build();
                    return chain.proceed(request);
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public WebServiceCaller(OnServiceFinished listener) {
        final String baseUrl = "https://mihmarici.000webhostapp.com/";
        this.listener=listener;

        final OkHttpClient client = OkHttpSSL();


        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        serviceCaller = retrofit.create(APIinterface.class);
    }

    public void loginOrRegistrate(UserModel user){
        call = serviceCaller.loginOrRegistrate(user.getEmail(),user.getPassword());
        checkCall();
    }


    private void checkCall(){
        if (call != null) {
            System.out.println(call.toString());
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()){
                            listener.onRequestArrived(response.body());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Log.d("TAG",t.getMessage());
                    listener.onRequestFailed("Conecting to server did not work!");
                }
            });
        }
    }
}
