package com.prm392.assignment.productsale.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit mainClient;

    private static String mainClientUrl = "http://10.0.2.2:5120/api/";

    // Khi muốn gọi đến các api khác nhau thì thêm các RetrofitClient khác nhau
    private static Retrofit upcItemDbClient;
    private static Retrofit barcodeMonsterClient;
    private static String upcItemDbClientUrl="https://api.upcitemdb.com/";
    private static String barcodeMonsterClientUrl="https://barcode.monster/";

    private RetrofitClient() { // apply singleton pattern
    }

    public static Retrofit getMainInstance() {
        if (mainClient == null) {
            mainClient = new Retrofit.Builder()
                    .baseUrl(mainClientUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        return mainClient;
    }

    public static Retrofit getUpcItemDbInstance(){
        if(upcItemDbClient == null){
            upcItemDbClient = new Retrofit.Builder()
                    .baseUrl(upcItemDbClientUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        return upcItemDbClient;
    }

    public static Retrofit getBarcodeMonsterInstance(){
        if(barcodeMonsterClient == null){
            barcodeMonsterClient = new Retrofit.Builder()
                    .baseUrl(barcodeMonsterClientUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        return barcodeMonsterClient;
    }
}
