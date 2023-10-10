package tmall.servlet;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 因为订单的增加和删除，都是在前台进行的。 所以OrderServlet提供的是list方法和delivery(发货)方法
 * 访问时
 * admin_order_list 导致OrderServlet.list()方法被调用
 * 1. 分页查询订单信息
 * 2. 借助orderItemDAO.fill()方法为这些订单填充上orderItems信息
 * 3. 服务端跳转到admin/listOrder.jsp页面
 * 4. 在listOrder.jsp借助c:forEach把订单集合遍历出来
 * 5. 遍历订单的时候，再把当前订单的orderItem订单项集合遍历出来
 */
public class OrderServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Order> orders = orderDAO.list(page.getStart(), page.getCount());
        orderItemDAO.fill(orders);
        int total = orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("os", orders);
        request.setAttribute("page", page);
        return "admin/listOrder.jsp";
    }

    /**
     * 当订单状态是waitDelivery的时候，就会出现发货按钮
     * 1. 发货按钮链接跳转到admin_order_delivery
     * 2. OrderServlet.delivery()方法被调用
     * 2.1 根据id获取Order对象
     * 2.2 修改发货时间，设置发货状态
     * 2.3 更新到数据库
     * 2.4 客户端跳转到admin_order_list页面
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.get(id);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderDAO.waitConfirm);
        orderDAO.update(order);
        return "@admin_order_list";
    }
}
