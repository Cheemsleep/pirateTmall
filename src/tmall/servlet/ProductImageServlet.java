package tmall.servlet;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
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

public class ProductImageServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        InputStream inputStream = null; //上传文件的输入流
        Map<String, String> params = new HashMap<>(); //提交上传文件时的其他参数
        inputStream = parseUpload(request, params); //解析上传的文件

        //根据上传的参数生成productImage对象
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product product = productDAO.get(pid);

        ProductImage productImage = new ProductImage();
        productImage.setType(type);
        productImage.setProduct(product);
        productImageDAO.add(productImage);

        //生成文件
        String filaName = productImage.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        } else
            imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
        File file = new File(imageFolder, filaName);
        file.getParentFile().mkdirs();

        //复制文件
        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read(bytes))) {
                        fos.write(bytes, 0, length);
                    }
                    fos.flush();
                    //保存文件为jpg格式
                    BufferedImage image = ImageUtil.change2jpg(file);
                    ImageIO.write(image, "jpg", file);

                    if (ProductImageDAO.type_single.equals(productImage.getType())) {
                        File file_small = new File(imageFolder_small, filaName);
                        File file_middle = new File(imageFolder_middle, filaName);

                        ImageUtil.resizeImage(file, 56, 56, file_small);
                        ImageUtil.resizeImage(file, 217, 190, file_middle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "@admin_productImage_list?pid="+pid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = productImageDAO.get(id);
        productImageDAO.delete(id);

        if (ProductImageDAO.type_single.equals(productImage.getType())) {
            String imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
            String imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");

            File file_single = new File(imageFolder_single, productImage.getType()+".jpg");
            file_single.delete();
            File file_small = new File(imageFolder_small, productImage.getType()+".jpg");
            file_small.delete();
            File file_middle = new File(imageFolder_middle, productImage.getType()+".jpg");
            file_middle.delete();
        } else {
            String imageFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
            File file_detail = new File(imageFolder_detail, productImage.getId()+".jpg");
            file_detail.delete();
        }
        return "@admin_productImage_list?pid="+productImage.getProduct().getId();
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
        int pid = Integer.parseInt(request.getParameter("pid")); //这里的pid在地址栏传递
        Product product = productDAO.get(pid);
        List<ProductImage> pisSingle = productImageDAO.list(product, ProductImageDAO.type_single);
        List<ProductImage> pisDetail = productImageDAO.list(product, ProductImageDAO.type_detail);

        request.setAttribute("p", product);
        request.setAttribute("pisSingle", pisSingle);
        request.setAttribute("pisDetail", pisDetail);
        return "admin/listProductImage.jsp";
    }
}
