package cs.zappos.ilovenougat.model;

public class ZappoProduct {
    public String brandName;
    public String thumbnailImageUrl;
    public String productId;
    public String originalPrice;
    public String styleId;
    public String colorId;
    public String price;
    public String percentOff;
    public String productUrl;
    public String productName;

    public boolean isOnSale() {
        return originalPrice != null && !originalPrice.equals(price);
    }

}
