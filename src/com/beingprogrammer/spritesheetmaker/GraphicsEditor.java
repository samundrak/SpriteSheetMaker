package com.beingprogrammer.spritesheetmaker;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GraphicsEditor extends Singleton {

    BufferedImage img2;

    public BufferedImage rescaleImage(String path, int w, int h, int pt, int pb, int pl, int pr) {
        try {
            File f = new File(path);
            //int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bi.createGraphics();


            int newWidth = bi.getWidth() - (pl + pr);
            int newHeight = bi.getHeight() - (pt + pb);

            System.out.println(bi.getWidth());
            System.out.println(bi.getHeight() + "\n");
            System.out.println(newWidth);
            System.out.println(newHeight + "\n");

            BufferedImage img = ImageIO.read(f);
            Image image = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            g.drawImage(image, pl, pt, chooser);
            return bi;


        } catch (IOException ex) {
            //Logger.getLogger(GraphicsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
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

    public void makeImage(String path, int width, int height, int pt, int pb, int pl, int pr) {
        //BufferedImage 
        img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img2.createGraphics();
        //int newWidth = bufImg(path).getWidth() - (pl - pr);
        //int newHeight = bufImg(path).getHeight() - (pt - pb);
        //  bufImg(path).getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        //(img,0,0,width,height,sx1,sy1,sx2,sy2,null);
        //g2.drawImage(bufImg(path), pl, pt, pr, pb, 0, 0, newWidth,newHeight, null);
        saveImage(img2, path);
    }

    public void saveImage(BufferedImage img, String path) {
        try {
            ImageIO.write(img, "PNG", new File(path));
        } catch (Exception ex) {
            response("Error GE68: "+ex.getLocalizedMessage());
        }
    }
}
