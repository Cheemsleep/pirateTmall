package tmall.filter;


import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 1. 首先在web.xml配置文件中，让所有的请求都会经过BackServletFilter
 * <url-pattern>/*</url-pattern>
 * 2. 还是假设访问的路径是：
 * http://127.0.0.1:8080/tmall/admin_category_list
 * 3. 在BackServletFilter 中通过request.getRequestURI()取出访问的uri: /tmall/admin_category_list
 * 4. 然后截掉/tmall，得到路径/admin_category_list
 * 5. 判断其是否以/admin开头
 * 6. 如果是，那么就取出两个_之间的字符串，category，并且拼接成/categoryServlet，通过服务端跳转到/categoryServlet
 * 7. 在跳转之前，还取出了list字符串，然后通过request.setAttribute的方式，借助服务端跳转，传递到categoryServlet里去
 */
public class BackServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 一、getRequestDispatcher()
     * getRequestDispatcher()包含两个重要方法，分别是请求转发和请求包含。一个请求跨多个Servlet时，需要使用请求转发和请求包含。
     * 首先需要获得一个RequestDispatcher 对象：RequestDispatcher rd = request.getRequestDispatcher("/MyServlet");
     * 请求转发： rd.forward( request , response );
     * 请求包含： rd.include( request  , response);
     * 需注意的是，无论是请求转发还是请求包含，都在一个请求范围内！使用同一个request和response！
     * 二、请求转发和请求包含的区别
     * 请求转发：由下一个Servlet完成响应体，当前Servlet可以设置响应头（留头不留体）。举个例子，AServlet请求转发到BServlet，那么AServlet不能够使用response.getWriter（） 和response.getOutputStream（）向客户端输出响应体，但可以使用response.setContentType("text/html;charset=utf-8") 设置响应头。而在BServlet中可以输出响应体。
     * 请求包含：由两个Servlet共同完成响应体（留头又留体）。同样用上面的例子，AServlet请求包含到BServlet，那么AServlet既可以设置响应头，也可以完成响应体。
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getServletContext().getContextPath(); //获取当前应用的虚拟访问目录，即相对路径，返回相对路径字符串即/tmall
        String uri = request.getRequestURI();//获取请求的路径
        uri = StringUtils.remove(uri, contextPath);
        if (uri.startsWith("/admin_")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri, "_");
            request.setAttribute("method", method);
            request.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
