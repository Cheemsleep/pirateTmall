package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 除开CRUD之外，ProductDAO还提供了一些其他用于支持业务的方法
 * 查询分类下的产品
 * public List<Product> list(int cid)
 * public List<Product> list(int cid, int start, int count)
 * 获取某种分类下的产品数量
 * public int getTotal(int cid)
 * 为分类填充产品集合
 * public void fill(Category c)
 * public void fill(List<Category> cs)
 * 为多个分类设置productsByRow属性
 * public void fillByRow(List<Category> cs)
 * fillByRow方法
 * 假设一个分类恰好对应40种产品，那么这40种产品本来是放在一个集合List里。
 * 可是，在页面上显示的时候，需要每8种产品，放在一列 为了显示的方便，把这40种产品，按照每8种产品方在一个集合里的方式，拆分成了5个小的集合，这5个小的集合里的每个元素是8个产品。
 * 这样到了页面上，显示起来就很方便了。 否则页面上的处理就会复杂不少。
 * 根据关键字查询产品
 * public List<Product> search(String keyword, int start, int count)
 * 一个产品有多个图片，但是只有一个主图片，把第一个图片设置为主图片
 * public void setFirstProductImage(Product p)
 * 为产品设置销售和评价数量
 * public void setSaleAndReviewNumber(List<Product> products)
 * public void setSaleAndReviewNumber(Product p)
 */
public class ProductDAO {
    public int getTotal(int cid) {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from product where cid = " + cid;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Product bean) {
        String sql = "insert into product values(null,?,?,?,?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getSubTitle());
            preparedStatement.setFloat(3, bean.getOrignalPrice());
            preparedStatement.setFloat(4, bean.getPromotePrice());
            preparedStatement.setInt(5, bean.getStock());
            preparedStatement.setInt(6, bean.getCategory().getId());
            preparedStatement.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "delete from product where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Product bean) {
        String sql = "update product set name = ?, subTitle = ?, orignalPrice = ?, promotePrice = ?, stock = ?, cid = ?, createDate = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getName());
            preparedStatement.setString(2, bean.getSubTitle());
            preparedStatement.setFloat(3, bean.getOrignalPrice());
            preparedStatement.setFloat(4, bean.getPromotePrice());
            preparedStatement.setInt(5, bean.getStock());
            preparedStatement.setInt(6, bean.getCategory().getId());
            preparedStatement.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product get(int id) {
        Product bean = new Product();
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from product where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float orignalPrice = resultSet.getFloat("orignalPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int stock = resultSet.getInt("stock");
                int cid = resultSet.getInt("cid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOrignalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                setFirstProductImage(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Product> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Product> list(int start, int count) {
        List<Product> beans = new ArrayList<>();
        String sql = "select * from product order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product bean = new Product();
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float orignalPrice = resultSet.getFloat("orignalPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int stock = resultSet.getInt("stock");
                int cid = resultSet.getInt("cid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOrignalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public List<Product> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Product> list(int cid, int start, int count) {
        List<Product> beans = new ArrayList<>();
        String sql = "select * from product where cid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product bean = new Product();
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float orignalPrice = resultSet.getFloat("orignalPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int stock = resultSet.getInt("stock");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOrignalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                setFirstProductImage(bean);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    public void fill(Category c) {
        List<Product> ps = this.list(c.getId());
        c.setProducts(ps);
    }

    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products = c.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }

    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = new ProductImageDAO().list(product, ProductImageDAO.type_single);
        if (!pis.isEmpty()) {
            product.setFirstProductImage(pis.get(0));
        }
    }

    public void setSaleAndReviewNumber(Product product) {
        int saleCount = new OrderItemDAO().getSaleCount(product.getId());
        product.setSaleCount(saleCount);

        int reviewCount = new ReviewDAO().getCount(product.getId());
        product.setReviewCount(reviewCount);
    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    public List<Product> search(String keyword, int start, int count) {
        List<Product> beans = new ArrayList<>();
        if (null == keyword || 0 == keyword.trim().length())  //trim() 函数移除字符串两侧的空白字符或其他预定义字符
            return beans;
        String sql = "select * from product where name like ? limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%"+keyword.trim()+"%");
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product bean = new Product();
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                String subTitle = resultSet.getString("subTitle");
                float orignalPrice = resultSet.getFloat("orignalPrice");
                float promotePrice = resultSet.getFloat("promotePrice");
                int cid = resultSet.getInt("cid");
                int stock = resultSet.getInt("stock");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOrignalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                setFirstProductImage(bean);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

}
