package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.dao.*;
import tmall.util.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseBackServlet extends HttpServlet {
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);

    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            /*获取分页信息*/
            int start = 0;
            int count = 5;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception e) {
                //不处理
            }

            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {
                //不处理
            }
            Page page = new Page(start, count);
            /*使用反射来调用对应的方法*/
            String method = (String) request.getAttribute("method"); //接收从BackFilter中传递的参数
            //通过反射获取method(参数)
            Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();//调用
            //test
            /*根据返回值进行相应的服务端客户端跳转，或输出字符串*/
            if (redirect.startsWith("@"))
                response.sendRedirect(redirect.substring(1)); //进行客户端跳转 去除开头的@符号
            else if (redirect.startsWith("%"))
                response.getWriter().print(redirect.substring(1));
            else
                request.getRequestDispatcher(redirect).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * DiskFileItemFactory 类
     * 设置临时文件夹；
     * 设置文件缓存区大小。
     *
     * ServletFileUpload 类
     * 判断表单是否包含文件上传控件：即是否设置了enctype="multipart/form-data"；
     * 设置 FileItemFactory 属性；
     * 解析前端请求：将表单项解析成 FileItem 对象；
     * *监听文件上传进度、处理乱码问题、设置文件上传最大值
     *
     * DiskFileItem：FileItem 接口实现类
     * 判断表单项是否上传文件，即类型是否为type=“file”；
     * 获取字段名；
     * 获取数据流内容；
     * 获取上传文件名；
     * 获取上传文件输入流；
     * 清空 FileItem 对象。
     * @param request
     * @param params
     * @return
     */
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream is = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory); //通过工厂设计模式实例化
            //设置上传文件的大小限制为10M
            factory.setSizeThreshold(1024 * 10240);
            List items = upload.parseRequest(request);
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if (!item.isFormField()) //判断表单项是否为上传文件：普通文本返回true，否则返回false
                    is = item.getInputStream(); //是上传文件获取上传文件的输入流
                else {
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

}
