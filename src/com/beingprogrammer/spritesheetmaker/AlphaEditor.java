package com.beingprogrammer.spritesheetmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AlphaEditor {

    public static AlphaEditor INSTANCE = new AlphaEditor();
    private BufferedImage img, finalImg;
    Graphics g;
    File orginal;
    private int width, height = 0;
    private int[] startOfPixel, endOfPixel, heightOfImage;

    public AlphaEditor() {
    }

    public void setSource(String source) {
        try {
            orginal = new File(source);
            img = ImageIO.read(new File(source));
            g = img.createGraphics();
            startOfPixel = new int[getHeight()];
            endOfPixel = new int[getHeight()];
            heightOfImage = new int[getHeight()];
        } catch (IOException ex) {
            System.err.println("Error IO");
        }
    }

    private int getHeight() {
        height = img.getHeight();
        return height;
    }

    private int getWidth() {
        width = img.getWidth();
        return width;
    }

    public static AlphaEditor getInstance() {
        return INSTANCE;
    }

    //pub 
    public void doing() throws IOException {

        int c = img.getRGB(400, 100);
        String x = ((isTransparent(400, 100)) ? " y " : "x");
        int red = (c & 0x00ff0000) >> 16;
        int green = (c & 0x0000ff00) >> 8;
        int blue = c & 0x000000ff;
// and the Java Color is ...
        Color color = new Color(red, green, blue);
        detectYData();

    }

    public boolean isTransparent(int x, int y) {
        int pixel = img.getRGB(x, y);
        if ((pixel >> 24) == 0x00) {
            return true;
        } else {
            return false;
        }
    }

    private int getColor(Color c, String color) {
        //Color c = new Color(colorValue[0],colorValue[1],colorValue[2]);
        int x;
        if (color.equals("red")) {
            x = c.getRed();
        } else if (color.equals("green")) {
            x = c.getGreen();
        } else if (color.equals("blue")) {
            x = c.getBlue();
        } else {
            x = 0;
        }
        return x;
    }

    public BufferedImage writeImage() throws IOException {
        int width = (getEndPixel(endOfPixel) - getStartPixel(startOfPixel));
        int height = (getEndPixel(heightOfImage) - getStartPixel(heightOfImage));

        int sx1, sx2, sy1, sy2;
        sx1 = getStartPixel(startOfPixel);// - 10;
        sy1 = getStartPixel(heightOfImage);// - 10;
        sx2 = getEndPixel(endOfPixel);//- 10;
        sy2 = getEndPixel(heightOfImage);//- 10;
//    
//            for(int x: heightOfImage){
//              System.out.println(x);
//            }
        // System.exit(0);

//img - the specified image to be drawn. This method does nothing if img is null.
//dx1 - the x coordinate of the first corner of the destination rectangle.
//dy1 - the y coordinate of the first corner of the destination rectangle.
//dx2 - the x coordinate of the second corner of the destination rectangle.
//dy2 - the y coordinate of the second corner of the destination rectangle.
//sx1 - the x coordinate of the first corner of the source rectangle.
//sy1 - the y coordinate of the first corner of the source rectangle.
//sx2 - the x coordinate of the second corner of the source rectangle.
//sy2 - the y coordinate of the second corner of the source rectangle.
//observer - object to be notified as more of the image is scaled and converted.
        try {
            finalImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            finalImg.getGraphics().drawImage(img, 0, 0, width, height, sx1, sy1, sx2, sy2, null);
        } catch (IllegalArgumentException e) {

            finalImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            finalImg.getGraphics().drawImage(img, 0, 0, null);
        }

        return finalImg;
        //Desktop.getDesktop().open(new File("c:/users/acer/desktop/test.png"));
    }

    public void saveFile(BufferedImage finalImg, String dir) {
        try {
            ImageIO.write(finalImg, "png", new File(dir + "/" + orginal.getName()));
        } catch (IOException ex) {
            //throw 
        }
    }
    static int pixelStatus = 0;

    private void detectYData() {
        // detectXData(300); detectXData(100); detectXData(200);
        for (int i = 0; i <= getHeight() - 1; i++) {
            detectXData(i);

        }

    }

    private void detectXData(int y) {
        for (int x = 0; x <= getWidth() - 1; x++) {
            getPixelValue(x, y, startOfPixel);
        }
        pixelStatus = 0;
    }

    private int getStartPixel(int[] arr) {
        int v = 0;
        for (int x : arr) {
            if (x != 0) {
                if (v == 0) {
                    v = x;
                }
                if (v > x) {
                    v = x;
                }
            }
        }
        //System.out.println(v);
        return v;
    }

    private int getEndPixel(int[] arr) {
        int v = 0;
        for (int x : arr) {
            if (x != 0) {
                if (v == 0) {
                    v = x;
                }
                if (v < x) {
                    v = x;
                }
            }
        }
        //System.out.println(v);
        return v;
    }

    private void getPixelValue(int x, int y, int[] arr) {
        if (isTransparent(x, y)) {
            //System.out.println("trans pixel found" + x);

            if (pixelStatus == 1) {
                endOfPixel[y] = x;
                pixelStatus = 2;
                heightOfImage[y] = y;
                //System.out.println("<"+y+">"+"pixel ends here: " + x);
            }
        } else {

            if (pixelStatus == 0) {
                arr[y] = x;
                heightOfImage[y] = y;
                // System.out.println("<"+y+">"+"color pixel found" + x);

            }

            pixelStatus = 1;
        }

    }

    public File pathToFile() {
        File x = new File("c:/users/acer/desktop/test");
        return x;
    }
}
