package com.robusttechhouse.goldprice.utils;

import android.util.Log;

import com.robusttechhouse.goldprice.BuildConfig;

/**
 * App's log utilities
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class LogUtils {

    private static final String TAG = "RobustTechHouse";

    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, message);
            Log.v(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, message);
            Log.i(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
            Log.d(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, message);
            Log.w(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message);
            Log.e(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
            Log.v(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
            Log.i(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
            Log.d(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
            Log.w(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
            Log.e(tag, "--------------------------------------------------------------------------");
        }
    }

}