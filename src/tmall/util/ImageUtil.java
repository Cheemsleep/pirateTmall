package tmall.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static BufferedImage change2jpg(File file) {
        try {
            Image image = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            /**
             * PixelGrabber​(Image img, int x, int y, int w, int h, boolean forceRGB)
             * 创建PixelGrabber对象以从指定图像中抓取（x，y，w，h）矩形像素部分。
             */
            PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, true);
            pg.grabPixels(); //请求Image或ImageProducer开始提供像素，并等待感兴趣的矩形中的所有像素被传递。
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            /**
             * DirectColorModel类是一个ColorModel类，它使用像素值表示RGB颜色和alpha信息作为单独的样本，并将单个像素的所有样本打包成单个int，short或byte数量。
             */
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]); //根据指定的掩码构造一个 DirectColorModel ，指示 int像素表示中的哪些位包含红色，绿色和蓝色样本以及alpha样本
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            Image image = ImageIO.read(srcFile);
            image = resizeImage(image, width, height);
            ImageIO.write((RenderedImage) image, "jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image resizeImage(Image srcImage, int width, int height) {
        try {
            BufferedImage bufferedImage = null;
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0 ,null);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
