package cs.zappos.ilovenougat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import cs.zappos.ilovenougat.util.TextUtils;

public class Product {
    private String brandName;
    private String productName;
    @JsonProperty("price")
    private String price;
    public String thumbnailImageUrl;
    public String productId;
    public String originalPrice;
    public String styleId;
    public String colorId;
    public String percentOff;
    public String productUrl;

    public boolean isOnSale() {
        return originalPrice != null && !originalPrice.equals(price);
    }

    public String getBrandName() {
        return TextUtils.decodeHtmlEntities(brandName);
    }

    public String getProductName() {
        return TextUtils.decodeHtmlEntities(productName);
    }

    public String getFormattedActualPrice() {
        return price;
    }

    public Double getActualPrice() {
        return Double.valueOf(price.substring(1));
    }

    public boolean isSame(Product product) {
        return this.productId.equals(product.productId) || (this.getBrandName().equals(product.getBrandName())
                && this.getProductName().equals(product.getProductName()));
    }

    public boolean isCheaperThan(Double zapposProduct) {
        return getActualPrice() < zapposProduct;
    }

}
