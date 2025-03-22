package com.prm392.assignment.productsale.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortAndFilterModel {
    //Sorting
    private String sortBy;

    public static final String SORT_POPULARITY = "popular";
    public static final String SORT_PRICE_ASC = "priceAsc";
    public static final String SORT_PRICE_DSC = "priceDsc";
    public static final String SORT_RATING = "rating";
    public static final String SORT_NEAREST_STORE = "nearest_store";
    public static final String SORT_BEST_DEAL = "best_deal";
    public static final String SORT_NEWEST = "newest";
    public static final String SORT_OLDEST = "oldest";

    //Filtering
    private long minPrice, maxPrice;
    private String category;
    private String brand;

    public static final long PRICE_MIN = 0;
    public static final long PRICE_MAX = 1000000;
    public static final String CATEGORY_ALL = "all";
    public static final String BRAND_ALL = "all";

    public SortAndFilterModel() {
        sortBy = SORT_POPULARITY;
        minPrice = PRICE_MIN;
        maxPrice = PRICE_MAX;
        category = CATEGORY_ALL;
        brand = BRAND_ALL;
    }

}
