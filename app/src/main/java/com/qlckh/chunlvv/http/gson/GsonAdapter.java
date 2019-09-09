package com.qlckh.chunlvv.http.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Andy
 * @date   2018/5/15 18:42
 * Desc:    GsonAdapter.java
 */

public class GsonAdapter {

    public static Gson buildGson() {
        Gson gson = null;
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .setLenient()
                    .create();
        }
        return gson;
    }
}
