package com.robusttechhouse.goldprice.utils;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;

/**
 * App's Common Utilities
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class CommonUtils {
    public static void setFontForTextView(TextView textView,
                                          FontUtils.FONTLIST font) {
        // Check input data.
        if ((textView == null)) {
            return;
        }

        Typeface fontFace = FontUtils.getFont(textView.getContext(), font);
        textView.setTypeface(fontFace);
    }

    public static void setFontForTextView(EditText textView,
                                          FontUtils.FONTLIST font) {
        // Check input data.
        if ((textView == null)) {
            return;
        }

        Typeface fontFace = FontUtils.getFont(textView.getContext(), font);
        textView.setTypeface(fontFace);
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / metrics.DENSITY_DEFAULT);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / metrics.DENSITY_DEFAULT);
        return Math.round(px);
    }
}
