package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ReviewDAO还提供了一些其他用于支持业务的方法
 * 获取指定产品一共有多少条评价
 * public int getCount(int pid)
 * 获取指定产品的评价
 * public List<Review> list(int pid)
 * public List<Review> list(int pid, int start, int count)
 */
public class ReviewDAO {
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from review";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Review bean) {
        String sql = "insert into review values(null,?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bean.getContent());
            preparedStatement.setInt(2, bean.getUser().getId());
            preparedStatement.setInt(3, bean.getProduct().getId());
            preparedStatement.setTimestamp(4, DateUtil.d2t(bean.getCreateDate())); //设置为数据库时间格式
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
            String sql = "delete from review where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Review bean) {
        String sql = "update review set content = ?, uid = ?, pid = ?, createDate = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bean.getContent());
            preparedStatement.setInt(2, bean.getUser().getId());
            preparedStatement.setInt(3, bean.getProduct().getId());
            preparedStatement.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
            preparedStatement.setInt(5, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Review get(int id) {
        Review bean = new Review();
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from review where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String content = resultSet.getString("content");
                int uid = resultSet.getInt("uid");
                int pid = resultSet.getInt("pid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));//数据库时间转为util时间
                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);
                bean.setId(id);
                bean.setContent(content);
                bean.setUser(user);
                bean.setProduct(product);
                bean.setCreateDate(createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }

    public List<Review> list(int pid, int start, int count) {
        List<Review> beans = new ArrayList<>();
        String sql = "select * from review where pid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review bean = new Review();
                int id = resultSet.getInt(1);
                String content = resultSet.getString("content");
                int uid = resultSet.getInt("uid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));

                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);

                bean.setId(id);
                bean.setContent(content);
                bean.setUser(user);
                bean.setProduct(product);
                bean.setCreateDate(createDate);

                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public int getCount(int pid) {
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from review where pid = " + pid;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isExist(String content, int pid) {
        String sql = "select * from review where pid = ? and content = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setString(2, content);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
