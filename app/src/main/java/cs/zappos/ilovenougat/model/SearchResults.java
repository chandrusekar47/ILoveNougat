package cs.zappos.ilovenougat.model;

import java.util.List;

public class SearchResults {
    public String originalTerm;
    public String currentResultCount;
    public String totalResultCount;
    public String term;
    public List<Product> results;

    public Product getMatchingProduct(Product product) {
        for (Product sixPmProduct : results) {
            if (sixPmProduct.isSame(product) && sixPmProduct.isCheaperThan(product.getActualPrice())) {
                return sixPmProduct;
            }
        }
        return null;
    }

}
