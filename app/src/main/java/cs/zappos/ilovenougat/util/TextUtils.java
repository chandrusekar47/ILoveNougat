package cs.zappos.ilovenougat.util;

import android.support.annotation.NonNull;
import android.text.Html;

public class TextUtils {
    @NonNull
    public static String decodeHtmlEntities(String brandName) {
        return Html.fromHtml(brandName).toString();
    }
}
