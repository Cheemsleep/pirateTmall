package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * ProductPriceComparator 价格比较器
 * 把 价格低的放前面
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o2.getPromotePrice() - o1.getPromotePrice());
    }
}
