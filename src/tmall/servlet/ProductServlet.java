package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class ProductServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setSubTitle(subTitle);
        product.setOrignalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCreateDate(new Date());

        productDAO.add(product);
        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);
        productDAO.delete(id);
        return "@admin_product_list?cid="+product.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);
        request.setAttribute("p", product);
        return "admin/editProduct.jsp";
    }

    /**
     * 对于PropertyValue编辑和修改的支持放了在ProductServlet中进行：
     * 主要是editPropertyValue、updatePropertyValue这两个方法
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(id);
        request.setAttribute("p", product);
        List<Property> properties = propertyDAO.list(product.getCategory().getId());
        propertyValueDAO.init(product);
        List<PropertyValue> propertyValues = propertyValueDAO.list(product.getId());
        request.setAttribute("pvs", propertyValues);
        return "/admin/editPropertyValue.jsp";
    }

    /**
     * 1. 是监听输入框上的keyup事件
     * 2. 获取输入框里的值
     * 3. 获取输入框上的自定义属性pvid，这就是当前PropertyValue对应的id
     * 4. 把边框的颜色修改为黄色，表示正在修改的意思
     * 5. 借助JQuery的ajax函数 $.post，把id和值，提交到admin_product_updatePropertyValue
     * 6. admin_product_updatePropertyValue导致ProductServlet的updatePropertyValue方法被调用
     * 6.1 获取pvid
     * 6.2 获取value
     * 6.3 基于pvid和value,更新PropertyValue对象
     * 6.4 返回"%success"
     * 7. BaseBackServlet根据返回值"%success"，直接输出字符串"success" 到浏览器
     * 8. 浏览器判断如果返回值是"success",那么就把边框设置为绿色，表示修改成功，否则设置为红色，表示修改失败
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int propertyValueId = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");
        PropertyValue propertyValue = propertyValueDAO.get(propertyValueId);
        propertyValue.setValue(value);
        propertyValueDAO.update(propertyValue);
        return "%success";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);
        int id = Integer.parseInt(request.getParameter("id"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        String subTitle = request.getParameter("subTitle");
        String name = request.getParameter("name");

        Product product = new Product();

        product.setCategory(category);
        product.setName(name);
        product.setId(id);
        product.setStock(stock);
        product.setOrignalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setSubTitle(subTitle);
        productDAO.update(product);
        return "@admin_product_list?cid="+product.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        List<Product> products = productDAO.list(cid, page.getStart(), page.getCount());
        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+category.getId());
        request.setAttribute("ps", products);
        request.setAttribute("c", category);
        request.setAttribute("page", page);
        return "admin/listProduct.jsp";
    }
}