package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * 1. ProductAllComparator 综合比较器
 * 把 销量x评价 高的放前面
 */
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() * o2.getSaleCount() - o1.getReviewCount() * o1.getSaleCount();
    }
}
