package tmall.servlet;


import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据web.xml中的配置
 * <servlet-name>CategoryServlet</servlet-name>
 * <url-pattern>/categoryServlet</url-pattern>
 * 服务端跳转/categoryServlet就到了CategoryServlet这个类里
 * 1. 首先CategoryServlet继承了BaseBackServlet，而BaseBackServlet又继承了HttpServlet
 * 2. 服务端跳转过来之后，会访问CategoryServlet的doGet()或者doPost()方法
 * 3. 在访问doGet()或者doPost()之前，会访问service()方法
 * 4. BaseBackServlet中重写了service() 方法，所以流程就进入到了service()中
 * 5. 在service()方法中有三块内容
 * 5.1 第一块是获取分页信息
 * 5.2 第二块是根据反射访问对应的方法
 * 5.3 第三块是根据对应方法的返回值，进行服务端跳转、客户端跳转、或者直接输出字符串。
 * 6. 第一块和第三块放在后面讲解，这里着重讲解第二块是根据反射访问对应的方法
 * 6.1 取到从BackServletFilter中request.setAttribute()传递过来的值 list
 * 6.2 根据这个值list，借助反射机制调用CategoryServlet类中的list()方法
 * 这样就达到了CategoryServlet.list()方法被调用的效果
 */
public class CategoryServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        String name = params.get("name");
        Category category = new Category();
        category.setName(name);
        categoryDAO.add(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category")); //path必须是/开头，代表当前web应用程序的根目录。通过该相对路径获取绝对路径，返回一个路径字符串String，如果不能进行映射返回null；
        File file = new File(imageFolder, category.getId() + ".jpg"); //创建文件

        try {
            if (null != is && 0 != is.available()) { //要一次读取多个字节时，经常用到InputStream.available()方法，这个方法可以在读写操作前先得知数据流里有多少个字节可以读取
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(bytes))) {
                        fos.write(bytes, 0, length);
                    }
                    fos.flush();
                    //保存文件为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDAO.get(id);
        request.setAttribute("c", category);
        return "admin/editCategory.jsp";
    }

    /**
     * 在update()方法中做了如下操作：
     * 1. parseUpload 获取上传文件的输入流
     * 2. parseUpload 方法会修改params 参数，并且把浏览器提交的name信息放在其中
     * 3. 从params 中取出id和name信息，并根据这个id,name信息，创建新的Category对象，并借助categoryDAO，向数据库中更新数据。
     * 4. 根据request.getServletContext().getRealPath( "img/category")，定位到存放分类图片的目录
     * 5. 文件命名以保存到数据库的分类对象的id+".jpg"的格式命名
     * 6. 如果通过parseUpload 获取到的输入流是空的，或者其中的可取字节数为0，那么就不进行上传处理
     * if(null!=is && 0!=is.available())
     * 7. 根据步骤1获取的输入流，把浏览器提交的文件，复制到目标文件
     * 8. 借助ImageUtil.change2jpg()方法把格式真正转化为jpg，而不仅仅是后缀名为.jpg
     * 9. 最后客户端跳转到admin_category_list
     *
     * 注： 为什么不能直接使用request.getParameter("name")的方式来获取数据？ 因为当浏览器提交的数据是二进制的时候，Servlet不能够通过这种方式直接获取参数。请参考 上传文件时，Servlet 何处理其他非File字段
     * 注 为什么要用request.getServletContext().getRealPath( )的方式定位e:/project/tmall/web/img/category 这目录，而不是用硬编码写死？ 因为在部署到Linux 实际运行的时候，Linux上的目录就有是其他的路径了，比如 /usr/public/tmall/img/category，只有采用这种方式才能兼容
     * 注 第八步为什么要这么做？ 因为浏览器提交来的图片文件，有可能是png,gif,bmp等非jpg格式的图片。 仅仅修改文件的后缀名有可能会导致显示异常。借助 ImageUtil工具类 真正保证文件的格式是jpg的。
     * @param request
     * @param response
     * @param page
     * @return
     */
    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        System.out.println(params);
        String name = params.get("name");
        int id = Integer.parseInt(params.get("id"));

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryDAO.update(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        file.getParentFile().mkdirs();

        try {
            if (null != is && 0 != is.available()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(bytes))) {
                        fos.write(bytes, 0, length);
                    }
                    fos.flush();//写入
                    BufferedImage image = ImageUtil.change2jpg(file);
                    ImageIO.write(image, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", categories);
        request.setAttribute("page", page);
        return "admin/listCategory.jsp"; //在BaseBackServlet的70行，在借助反射机制调用了list()方法之后，获取返回值redirect.
//        如果redirect是以@开头的字符串，那么就进行客户端跳转
//        如果redirect是以%开头的字符串，那么就直接输出字符串
//        如果都不是，则进行服务端跳转
    }

}
