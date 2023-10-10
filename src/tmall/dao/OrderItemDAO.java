package tmall.dao;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取某一种产品的销量。 产品销量就是这种产品对应的订单项OrderItem的number字段的总和
 * public int getSaleCount(int pid)
 * 查询某种订单下所有的订单项
 * public List<OrderItem> listByOrder(int oid)
 * 查询某个用户的未生成订单的订单项(既购物车中的订单项)
 * public List<OrderItem> listByUser(int uid)
 * 为订单设置订单项集合
 * public void fill(Order o)
 * public void fill(List<Order> os)
 */
public class OrderItemDAO {
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select count(*) from orderItem";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(OrderItem bean) {
        String sql = "insert into orderItem values(null,?,?,?,?)";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, bean.getProduct().getId());
            //订单项在创建的时候没有订单信息
            if (null == bean.getOrder()) {
                preparedStatement.setInt(2, -1);
            } else {
                preparedStatement.setInt(2, bean.getOrder().getId());
            }
            preparedStatement.setInt(3, bean.getUser().getId());
            preparedStatement.setInt(4, bean.getNumber());
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
            String sql = "delete from orderItem where id = " + id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(OrderItem bean) {
        String sql = "update orderItem set pid = ?, oid = ?, uid = ?, number = ? where id = ?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bean.getProduct().getId());
            if (null == bean.getOrder())
                preparedStatement.setInt(2, -1);
            else
                preparedStatement.setInt(2, bean.getOrder().getId());
            preparedStatement.setInt(3, bean.getUser().getId());
            preparedStatement.setInt(4, bean.getNumber());
            preparedStatement.setInt(5, bean.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderItem get(int id) {
        OrderItem bean = new OrderItem();
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select * from orderItem where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int pid = resultSet.getInt("pid");
                int oid = resultSet.getInt("oid");
                int uid = resultSet.getInt("uid");
                int number = resultSet.getInt("number");
                if (-1 != oid) {
                    Order order = new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "select * from orderItem where uid = ? and oid = -1 order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem bean = new OrderItem();
                int id = resultSet.getInt(1);
                int pid = resultSet.getInt("pid");
                int oid = resultSet.getInt("oid");
                int number = resultSet.getInt("number");
                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "select * from orderItem where oid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, oid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem bean = new OrderItem();
                int id = resultSet.getInt(1);
                int pid = resultSet.getInt("pid");
                int uid = resultSet.getInt("uid");
                int number = resultSet.getInt("number");
                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void fill(List<Order> os) {
        for (Order o : os) {
            List<OrderItem> ois = listByOrder(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                total += oi.getNumber()*oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }
    }

    public void fill(Order o) {
        List<OrderItem> ois = listByOrder(o.getId());
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }

    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "select * from orderItem where pid = ? order by id desc limit ?,?";
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem bean = new OrderItem();
                int id = resultSet.getInt(1);
                int oid = resultSet.getInt("oid");
                int uid = resultSet.getInt("uid");
                int number = resultSet.getInt("number");
                User user = new UserDAO().get(uid);
                Product product = new ProductDAO().get(pid);
                if (-1 != oid) {
                    Order order = new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public int getSaleCount(int pid) {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement()) {
            String sql = "select sum(number) from orderItem where pid = " + pid;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
