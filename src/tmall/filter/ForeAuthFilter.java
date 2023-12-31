package tmall.filter;

import org.apache.commons.lang.StringUtils;
import tmall.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 新建一个过滤器ForeAuthFilter，根据解决思路中
 * 哪些页面需要登录？哪些页面不需要呢？
 * a. 不需要登录也可以访问的
 * 如：注册，登录，产品，首页，分类，查询等等
 * b. 需要登录才能够访问的
 * 如：购买行为，加入购物车行为，查看购物车，查看我的订单等等
 * 不需要登录也可以访问的已经确定了，但是需要登录才能够访问，截止目前为止还不能确定，所以这个过滤器就判断如果不是注册，登录，产品这些，就进行登录校验
 * 1. 准备字符串数组 noNeedAuthPage，存放哪些不需要登录也能访问的路径
 * 2. 获取uri
 * 3. 去掉前缀/tmall
 * 4. 如果访问的地址是/fore开头，又不是/foreServlet
 * 4.1 取出fore后面的字符串，比如是forecart,那么就取出cart
 * 4.2 判断cart是否是在noNeedAuthPage
 * 4.2 如果不在，那么就需要进行是否登录验证
 * 4.3 从session中取出"user"对象
 * 4.4 如果对象不存在，就客户端跳转到login.jsp
 * 4.5 否则就正常执行
 */
public class ForeAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getContextPath();
        String[] noNeedAuthPage = new String[] {
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if (uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
            String method = StringUtils.substringAfterLast(uri, "/fore");
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                User user = (User) request.getSession().getAttribute("user");
                if (null == user) {
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
