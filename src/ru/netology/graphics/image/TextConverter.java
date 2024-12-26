package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextConverter implements TextGraphicsConverter {

    private int maxWidth = 0;
    private int maxHeight = 0;
    private double maxRatio = 0;
    private TextColorSchema schema = new ColorSchema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        int naturalImageWidth = img.getWidth();
        int naturalImageHeight = img.getHeight();
        double naturalRatio = (double) naturalImageWidth / naturalImageHeight;
        int newWidth = naturalImageWidth;
        int newHeight = naturalImageHeight;

        if (maxRatio != 0 && naturalRatio > maxRatio) {
            throw new BadImageSizeException(maxRatio, naturalRatio);
        }

        if (maxWidth != 0 && maxHeight == 0 && naturalImageWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = naturalImageHeight / (naturalImageWidth / maxWidth);
        } else if (maxHeight != 0 && maxWidth == 0 && naturalImageHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = naturalImageWidth / (naturalImageHeight / maxHeight);
        } else if (maxWidth != 0 && maxHeight != 0 && (naturalImageWidth > maxWidth || naturalImageHeight > maxHeight)) {
            if ((naturalImageWidth / maxWidth) >= (naturalImageHeight / maxHeight)) {
                newWidth = maxWidth;
                newHeight = naturalImageHeight / (naturalImageWidth / maxWidth);
            } else {
                newHeight = maxHeight;
                newWidth = naturalImageWidth / (naturalImageHeight / maxHeight);
            }
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();

        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        int[] colorPixel = new int[3];
        char[][] resultChars = new char[newHeight][newWidth];

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, colorPixel)[0];
                char c = schema.convert(color);
                resultChars[h][w] = c;
            }
        }

        StringBuilder result = new StringBuilder();

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                result.append(resultChars[h][w]);
                result.append(resultChars[h][w]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
