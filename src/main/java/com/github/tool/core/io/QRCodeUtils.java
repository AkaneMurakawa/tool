package com.github.tool.core.io;

import com.github.tool.core.io.enums.LabelTypeEnum;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * QRCodeUtils
 */
@Log4j2
public class QRCodeUtils {

    private static final String UTF_8 = "UTF-8";
    private static final Integer BARCODE_HEIGHT = 200;
    private static final int BLACK = 0x000000;
    private static final int WHITE = 0xFFFFFF;

    /**
     * 生成二维码，写入到文件中
     *
     * @param contents 内容
     */
    public static void createQRCodeWriteFile(String contents, File file) throws IOException {
        BufferedImage image = createQRCode(contents, BARCODE_HEIGHT);
        ImageIO.write(image, "png", file);
    }

    /**
     * 生成二维码，写入到文件中
     *
     * @param contents 内容
     * @param height   高度（px），最小100
     */
    public static void createQRCodeWriteFile(String contents, Integer height, File file) throws IOException {
        BufferedImage image = createQRCode(contents, height);
        ImageIO.write(image, "png", file);
    }

    /**
     * 生成一维码，写入到文件中
     *
     * @param contents 内容
     */
    public static void createBarcodeWriteFile(String contents, LabelTypeEnum labelType, File file) throws IOException {
        BufferedImage image = createBarcode(contents, labelType.getWidth(), labelType.getHeight());
        ImageIO.write(image, "png", file);
    }

    /**
     * 生成一维码，写入到文件中
     *
     * @param contents 内容
     * @param width    宽
     * @param height   高
     */
    public static void createBarcodeWriteFile(String contents, Integer width, Integer height, File file) throws IOException {
        BufferedImage image = createBarcode(contents, width, height);
        ImageIO.write(image, "png", file);
    }

    /**
     * 生成QR二维码
     *
     * @param contents 内容
     * @param height   高度（px），最小100
     */
    public static BufferedImage createQRCode(String contents, Integer height) {
        if (height == null || height < 100) {
            height = BARCODE_HEIGHT;
        }
        try {
            HashMap<EncodeHintType, String> hints = new HashMap<>();
            // 文字编码
            hints.put(EncodeHintType.CHARACTER_SET, UTF_8);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, height, height, hints);
            return toBufferedImage(bitMatrix);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 生成一维码（128）
     *
     * @param contents 内容
     */
    public static BufferedImage createBarcode(String contents, Integer width, Integer height) {
        try {
            HashMap<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, UTF_8);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_128, width, height, hints);
            return toBufferedImage(bitMatrix);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 转换成图片
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
}
