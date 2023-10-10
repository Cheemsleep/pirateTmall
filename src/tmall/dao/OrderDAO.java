package tmall.dao;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 这些public static final 修饰的常量字符串，用于表示订单类型，在实体类Order的getStatusDesc方法中就用到了这些属性
 * public static final String waitPay = "waitPay";
 * public static final String waitDelivery = "waitDelivery";
 * public static final String waitConfirm = "waitConfirm";
 * public static final String waitReview = "waitReview";
 * public static final String finish = "finish";
 * public static final String delete = "delete";
 *
 * 除开CRUD之外，OrderDAO还提供了一些其他用于支持业务的方法。
 * 查询指定用户的订单(去掉某种订单状态，通常是"delete")
 * public List<Order> list(int uid,String excludedStatus)
 * public List<Order> list(int uid, String excludedStatus, int start, int count)
 */
public class OrderDAO {
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from order_";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Order bean) {
        String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bean.getOrderCode());
            preparedStatement.setString(2, bean.getAddress());
            preparedStatement.setString(3, bean.getPost());
            preparedStatement.setString(4, bean.getReceiver());
            preparedStatement.setString(5, bean.getMobile());
            preparedStatement.setString(6, bean.getUserMessage());
            preparedStatement.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            preparedStatement.setTimestamp(8, DateUtil.d2t(bean.getPayDate()));
            preparedStatement.setTimestamp(9, DateUtil.d2t(bean.getDeliveryDate()));
            preparedStatement.setTimestamp(10, DateUtil.d2t(bean.getConfirmDate()));
            preparedStatement.setInt(11, bean.getUser().getId());
            preparedStatement.setString(12, bean.getStatus());
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
            String sql = "delete from order_ where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Order bean) {

        String sql = "update order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,createDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , orderCode =?, uid=?, status=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getAddress());
            ps.setString(2, bean.getPost());
            ps.setString(3, bean.getReceiver());
            ps.setString(4, bean.getMobile());
            ps.setString(5, bean.getUserMessage());
            ps.setTimestamp(6, DateUtil.d2t(bean.getCreateDate()));;
            ps.setTimestamp(7, DateUtil.d2t(bean.getPayDate()));;
            ps.setTimestamp(8, DateUtil.d2t(bean.getDeliveryDate()));;
            ps.setTimestamp(9, DateUtil.d2t(bean.getConfirmDate()));;
            ps.setString(10, bean.getOrderCode());
            ps.setInt(11, bean.getUser().getId());
            ps.setString(12, bean.getStatus());
            ps.setInt(13, bean.getId());
            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public Order get(int id) {
        Order bean = new Order();
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from order_ where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String orderCode =resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String mobile = resultSet.getString("mobile");
                String userMessage = resultSet.getString("userMessage");
                String status = resultSet.getString("status");
                int uid =resultSet.getInt("uid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( resultSet.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(resultSet.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(resultSet.getTimestamp("confirmDate"));

                User user = new UserDAO().get(uid);
                bean.setId(id);//没有set导致无法修改数据库内容
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setStatus(status);
                bean.setUser(user);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Order> list(int start, int count) {
        List<Order> beans = new ArrayList<>();
        String sql = "select * from order_ order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order bean = new Order();
                int id = resultSet.getInt("id");
                String orderCode =resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String mobile = resultSet.getString("mobile");
                String userMessage = resultSet.getString("userMessage");
                String status = resultSet.getString("status");

                int uid =resultSet.getInt("uid");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( resultSet.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(resultSet.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(resultSet.getTimestamp("confirmDate"));

                User user = new UserDAO().get(uid);

                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setStatus(status);
                bean.setUser(user);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);

                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public List<Order> list(int uid, String excludedStatus) {
        return list(uid, excludedStatus, 0, Short.MAX_VALUE);
    }

    public List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> beans = new ArrayList<>();
        String sql = "select * from Order_ where uid = ? and status != ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, excludedStatus);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order bean = new Order();
                int id = resultSet.getInt("id");
                String orderCode =resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String mobile = resultSet.getString("mobile");
                String userMessage = resultSet.getString("userMessage");
                String status = resultSet.getString("status");
                Date createDate = DateUtil.t2d(resultSet.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d( resultSet.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(resultSet.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(resultSet.getTimestamp("confirmDate"));

                User user = new UserDAO().get(uid);

                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setStatus(status);
                bean.setUser(user);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);

                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }




    public static void main(String[] args) {
        System.out.println(new OrderDAO().getTotal());
    }
}
