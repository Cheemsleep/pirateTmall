package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * ProductSaleCountComparator 销量比较器
 * 把 销量高的放前面
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
