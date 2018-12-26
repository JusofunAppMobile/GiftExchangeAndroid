package com.jusfoun.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * 带图片的二维码四周会有一定的边距，是因为将容差级别设置为ErrorCorrectionLevel.H造成的，如果不设置为H级别，二维码不能正常被识别
 */
public class QRCodeUtils {

    private final static int FIRST_COLOR = 0xff000000;
    private final static int SECOND_COLOR = 0xffffffff;
    private final static int DEFAULT_IMAGE_SCALE = 4;
    private final static int MIN_IMAGE_SCALE = 3;

    private static OnDrawColorCall defaultCall = new OnDrawColorCall() {
        @Override
        public int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException {
            return getDefaultPixels(size, FIRST_COLOR, SECOND_COLOR, bitMatrix);
        }
    };

    /**
     * 生成二维码
     *
     * @param value
     * @param size  二维码宽、高大小
     * @return
     */
    public static Bitmap createQRImage(String value, int size) {
        return createQRImage(value, size, false, defaultCall);
    }


    /**
     * 生成二维码
     *
     * @param value
     * @param size        二维码宽、高大小
     * @param firsColor   二维码黑色区域要替换的颜色
     * @param secondColor 二维码白色区域要替换的颜色
     * @return
     */
    public static Bitmap createQRImage(String value, int size, final int firsColor, final int secondColor) {
        return createQRImage(value, size, false, new OnDrawColorCall() {
            @Override
            public int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException {
                return getDefaultPixels(size, firsColor, secondColor, bitMatrix);
            }

        });
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size  二维码宽、高大小
     * @param call  处理绘制彩色二维码
     * @return
     */
    public static Bitmap createQRImage(String value, int size, OnDrawColorCall call) {
        return createQRImage(value, size, false, call);
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size   二维码宽、高大小
     * @param bitmap 在二维码中间需要添加的图片
     * @param scale  取值 >= 3 。中间图片相对于二维码图片的几分之一，3= 二维码宽高的三分之一 ，默认是四分之一。必须大于等于3，小于3时，图片过大无法正常识别二维码。
     * @return
     */

    public static Bitmap createQRImage(String value, int size, Bitmap bitmap, int scale) {
        return addImage(createQRImage(value, size, true, defaultCall), bitmap, scale);
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size        二维码宽、高大小
     * @param firsColor   二维码黑色区域要替换的颜色
     * @param secondColor 二维码白色区域要替换的颜色
     * @param bitmap      在二维码中间需要添加的图片
     * @param scale       取值 >= 3 。中间图片相对于二维码图片的几分之一，3= 二维码宽高的三分之一 ，默认是四分之一。必须大于等于3，小于3时，图片过大无法正常识别二维码。
     * @return
     */
    public static Bitmap createQRImage(String value, int size, final int firsColor, final int secondColor, Bitmap bitmap, int scale) {
        return addImage(createQRImage(value, size, true, new OnDrawColorCall() {
            @Override
            public int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException {
                return getDefaultPixels(size, firsColor, secondColor, bitMatrix);
            }
        }), bitmap, scale);
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size   二维码宽、高大小
     * @param bitmap 在二维码中间需要添加的图片
     * @param scale  取值 >= 3 。中间图片相对于二维码图片的几分之一，3= 二维码宽高的三分之一 ，默认是四分之一。必须大于等于3，小于3时，图片过大无法正常识别二维码。
     * @param call   处理绘制彩色二维码
     * @return
     */
    public static Bitmap createQRImage(String value, int size, Bitmap bitmap, int scale, OnDrawColorCall call) {
        return addImage(createQRImage(value, size, true, call), bitmap, scale);
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size   二维码宽、高大小
     * @param bitmap 在二维码中间需要添加的图片
     * @return
     */
    public static Bitmap createQRImage(String value, int size, Bitmap bitmap) {
        return addImage(createQRImage(value, size, true, defaultCall), bitmap, DEFAULT_IMAGE_SCALE);
    }

    /**
     * 生成二维码
     *
     * @param value
     * @param size        二维码宽、高大小
     * @param firsColor   二维码黑色区域要替换的颜色
     * @param secondColor 二维码白色区域要替换的颜色
     * @param bitmap      在二维码中间需要添加的图片
     * @return
     */
    public static Bitmap createQRImage(String value, int size, final int firsColor, final int secondColor, Bitmap bitmap) {
        return addImage(createQRImage(value, size, true, new OnDrawColorCall() {
            @Override
            public int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException {
                return getDefaultPixels(size, firsColor, secondColor, bitMatrix);
            }
        }), bitmap, DEFAULT_IMAGE_SCALE);
    }

    /**
     * @param value
     * @param size   二维码宽、高大小
     * @param bitmap 在二维码中间需要添加的图片
     * @param call   处理绘制彩色二维码
     * @return
     */
    public static Bitmap createQRImage(String value, int size, Bitmap bitmap, OnDrawColorCall call) {
        return addImage(createQRImage(value, size, true, call), bitmap, DEFAULT_IMAGE_SCALE);
    }

    /**
     * 设置二维码四个角为不同的颜色
     *
     * @param color1  左上颜色
     * @param color2  左下颜色
     * @param color3  右下颜色
     * @param color4  右上颜色
     * @param colorBg 二维码空白处填充的颜色
     * @return
     */
    public static OnDrawColorCall getFourDrawColorCall(final int color1, final int color2, final int color3, final int color4, final int colorBg) {
        return new OnDrawColorCall() {
            @Override
            public int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException {
                int width = size;
                int height = size;
                int[] pixels = new int[width * height];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (bitMatrix.get(x, y)) {
                            if (x < size / 2 && y < size / 2) {
                                pixels[y * size + x] = color1;
                            } else if (x < size / 2 && y > size / 2) {
                                pixels[y * size + x] = color2;
                            } else if (x > size / 2 && y > size / 2) {
                                pixels[y * size + x] = color3;
                            } else {
                                pixels[y * size + x] = color4;
                            }
                        } else {
                            pixels[y * width + x] = colorBg;
                        }
                    }
                }
                return pixels;
            }
        };
    }

    private static Bitmap createQRImage(String value, int size, boolean isAddImage, OnDrawColorCall call) {
        try {
            if (TextUtils.isEmpty(value))
                return null;
            int width = size;
            int height = size;

            BitMatrix bitMatrix = getBitMatrix(value, size, isAddImage);
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            int[] pixels = call.getPixels(size, bitMatrix);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BitMatrix getBitMatrix(String text, int size, boolean isAddImage) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置空白边距的宽度,默认为4
        hints.put(EncodeHintType.MARGIN, 0);
        // 如果是带图片的，要将容级别设置为H， 默认是L
        if (isAddImage) {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }
        // 图像数据转换，使用了矩阵转换
        return new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);
    }

    private static int[] getDefaultPixels(int size, int firstColor, int secondColor, BitMatrix bitMatrix) throws WriterException {
        int width = size;
        int height = size;
        int[] pixels = new int[width * height];
        // 下面这里按照二维码的算法，逐个生成二维码的图片，
        // 两个for循环是图片横列扫描的结果
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = firstColor;
                } else {
                    pixels[y * width + x] = secondColor;
                }
            }
        }
        return pixels;
    }


    /**
     * 处理绘制彩色二维码，可以参考getFourDrawColorCall中的绘制方法
     */
    public interface OnDrawColorCall {
        /**
         * 可以参考getFourDrawColorCall中的绘制方法
         */
        int[] getPixels(int size, BitMatrix bitMatrix) throws WriterException;
    }

    private static Bitmap addImage(Bitmap bit, Bitmap logo, int scale) {
        if (bit == null)
            return null;
        if (logo == null)
            return bit;
        int srcWidth = bit.getWidth();
        int srcHeight = bit.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0)
            return null;
        if (logoWidth == 0 || logoHeight == 0)
            return bit;
        if (scale < 3)
            scale = MIN_IMAGE_SCALE;
        float scaleFactor = srcWidth * 1.0f / scale / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bit, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }
}
