package cs.zappos.ilovenougat.model;

import android.text.Html;

public class ZapposProduct {
    private String brandName;
    private String productName;
    public String thumbnailImageUrl;
    public String productId;
    public String originalPrice;
    public String styleId;
    public String colorId;
    public String price;
    public String percentOff;
    public String productUrl;

    public boolean isOnSale() {
        return originalPrice != null && !originalPrice.equals(price);
    }

    public String getBrandName() {
        return Html.fromHtml(brandName).toString();
    }

    public String getProductName() {
        return Html.fromHtml(productName).toString();
    }
}
