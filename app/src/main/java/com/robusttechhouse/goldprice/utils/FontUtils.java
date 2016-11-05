package com.robusttechhouse.goldprice.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.io.File;
import java.util.Hashtable;

/**
 * App's font utilities
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class FontUtils {

    /**
     * Fonts in the app
     */
    public enum FONTLIST {
        Myriad_Pro_Regular("MyriadPro-Regular", "MyriadPro-Regular.otf"),
        Myriad_Pro_Bold("MyriadPro-Bold", "MyriadPro-Bold.otf"),
        Myriad_Pro_Black_Semi_Cn("MyriadPro-BlackSemiCn", "MyriadPro-BlackSemiCn.otf");

        private FONTLIST(String name, String path) {
            this.mName = name;
            this.mPath = path;
        }

        private String mName, mPath;

        public String getName() {
            return mName;
        }

        public String getPath() {
            return mPath;
        }
    }

    public static Typeface getFont(Context context, FONTLIST font) {

        if (font == null) {
            return null;
        }

        FontItem item = new FontItem();
        item.mFontName = font.mName;
        item.mFontPath = "fonts/" + font.mPath;
        item.mSrc = FontUtils.FONTSRC.INTERNAL_TYPE;

        return FontUtils.get(context, item);

    }

    /**
     * Font manage structure
     */
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    private static Typeface get(Context c, FontItem font) {

        synchronized (cache) {

            try {

                if (!cache.containsKey(font.mFontName)) {

                    Typeface typeFace = null;

                    if (font.mSrc == FONTSRC.INTERNAL_TYPE) {
                        typeFace = Typeface.createFromAsset(c.getAssets(), font.mFontPath);
                    } else if (font.mSrc == FONTSRC.EXTERNAL_TYPE) {
                        //
                        File f = new File(font.mFontPath);

                        if (f.exists()) {
                            typeFace = Typeface.createFromFile(f);
                        }
                    } else {

                        if (font.mFontName.equalsIgnoreCase("DEFAULT")) {
                            typeFace = Typeface.DEFAULT;
                        } else if (font.mFontName.equalsIgnoreCase("MONOSPACE")) {
                            typeFace = Typeface.MONOSPACE;
                        } else if (font.mFontName.equalsIgnoreCase("SANS_SERIF")) {
                            typeFace = Typeface.SANS_SERIF;
                        } else {
                            typeFace = Typeface.SERIF;
                        }
                    }

                    if (typeFace != null) {
                        cache.put(font.mFontName, typeFace);
                    }
                }
            } catch (Exception e) {
                System.out.println("Get Typeface '" + font.mFontPath + "' error " + e.getMessage());
                return null;
            }
            return cache.get(font.mFontName);
        }
    }

    static class FontItem {
        public String mFontName;
        public String mFontPath;
        public FONTSRC mSrc;
    }

    enum FONTSRC {
        INTERNAL_TYPE,
        EXTERNAL_TYPE,
        SYSTEM_TYPE
    }
}

