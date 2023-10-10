package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * PropertyValueDAO还提供了一些其他用于支持业务的方法。
 * 根据属性id和产品id，获取一个PropertyValue对象
 * public PropertyValue get(int ptid, int pid )
 * 初始化某个产品对应的属性值，初始化逻辑：
 * 1. 根据分类获取所有的属性
 * 2. 遍历每一个属性
 * 2.1 根据属性和产品，获取属性值
 * 2.2 如果属性值不存在，就创建一个属性值对象
 * public void init(Product p)
 * 查询某个产品下所有的属性值
 * public List<PropertyValue> list(int pid)
 */
public class PropertyValueDAO {
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from propertyValue";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue bean) {
        String sql = "insert into propertyValue values(null,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, bean.getProduct().getId());
            preparedStatement.setInt(2, bean.getProperty().getId());
            preparedStatement.setString(3, bean.getValue());
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
            String sql = "delete from propertyValue where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PropertyValue bean) {
        String sql = "update propertyValue set pid = ?, ptid = ?, value = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bean.getProduct().getId());
            preparedStatement.setInt(2, bean.getProperty().getId());
            preparedStatement.setString(3, bean.getValue());
            preparedStatement.setInt(4, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PropertyValue get(int id) {
        PropertyValue bean = null;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from propertyValue where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                bean = new PropertyValue();
                int pid = resultSet.getInt("pid");
                int ptid = resultSet.getInt("ptid");
                String value = resultSet.getString("type");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public PropertyValue get(int ptid, int pid) {
        PropertyValue bean = new PropertyValue();
        String sql = "select * from propertyValue where pid = ? and ptid = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, ptid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String value = resultSet.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> beans = new ArrayList<>();
        String sql = "select * from propertyValue order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PropertyValue bean = new PropertyValue();
                int id = resultSet.getInt(1);
                int pid = resultSet.getInt(2);
                int ptid = resultSet.getInt(3);
                String value = resultSet.getString(4);
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void init(Product product) {
        List<Property> properties = new PropertyDAO().list(product.getCategory().getId());

        for (Property property : properties) {
            PropertyValue propertyValue = get(property.getId(), product.getId());
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                this.add(propertyValue);
            }
        }
    }

    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<>();
        String sql = "select * from propertyValue where pid = ? order by ptid desc";

        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PropertyValue bean = new PropertyValue();
                int id = resultSet.getInt(1);
                int ptid = resultSet.getInt("ptid");
                String value = resultSet.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
