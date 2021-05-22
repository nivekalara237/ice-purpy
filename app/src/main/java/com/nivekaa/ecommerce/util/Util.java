package com.nivekaa.ecommerce.util;

import android.content.Context;
import android.graphics.Typeface;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static final String ATTRIBUTE_TTF_KEY = "ttf_name";
    public static final String ATTRIBUTE_SCHEMA = "http://schemas.android.com/apk/lib/com.nivekaa.ecommerce.util";
    public static final long DELIVERY_PRICE = 100L;
    private static final Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();


    // --- METHODS ---- //

    public static Typeface getFonts(Context context, String fontName) {
        Typeface typeface = TYPEFACE.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "font/"
                    + fontName);
            TYPEFACE.put(fontName, typeface);
        }
        return typeface;
    }

    public static Float avoidNullable(Float currentValue) {
        if (currentValue == null)
            return 0F;
        else
            return currentValue;
    }

    public static Float getFloatValAvoidingNullable(BigDecimal val) {
        if (val == null)
            return 0F;
        val.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return val.floatValue();
    }
}
