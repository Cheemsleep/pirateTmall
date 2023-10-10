package tmall.bean;

import java.util.List;

/**
 * Category 除了基本属性id和name的getter和setter外，还提供了
 * 一对多关系 products的 getter与setter。
 * 另外还有一个List<List<Product>> productsByRow;
 * productsByRow这个属性的类型是List<List<Product>> productsByRow。
 * 即一个分类又对应多个 List<Product>，提供这个属性，是为了在首页竖状导航的分类名称右边显示产品列表。
 */
public class Category {
    private int id;
    private String name;
    private List<Product> products;
    List<List<Product>> productsByRow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

}


