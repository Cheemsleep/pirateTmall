package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * ProductReviewComparator 人气比较器
 * 把 评价数量多的放前面
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}
