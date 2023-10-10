package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {
    public int getTotal(int cid) {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from property where cid = " + cid;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Property bean) {
        String sql = "insert into property values(null,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, bean.getName());
            preparedStatement.setInt(1, bean.getCategory().getId());
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
            String sql = "delete from property where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Property bean) {
        String sql = "update property set cid = ?, name = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bean.getCategory().getId());
            preparedStatement.setString(2, bean.getName());
            preparedStatement.setInt(3, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Property get(int id) {
        Property bean = new Property(); //防止空指针异常
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from property where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int cid = resultSet.getInt("cid");
                String name = resultSet.getString("name");
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setCategory(category);
                bean.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * PropertyDAO还提供了一些其他用于支持业务的方法。
     * 获取某种分类下的属性总数，在分页显示的时候会用到
     * public int getTotal(int cid)
     * 查询某个分类下的的属性对象
     * public List<Property> list(int cid, int start, int count)
     * public List<Property> list(int cid)
     * @param cid
     * @return
     */

    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<>();
        String sql = "select * from property where cid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Property bean = new Property();
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setCategory(category);
                bean.setName(name);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
