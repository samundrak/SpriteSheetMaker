package com.beingprogrammer.spritesheetmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Demo extends JLabel {

    int w = 320;
    int h = 170;
    More more;
    AlphaEditor alphaEditor;
    private int[] padding;
    private int[] image;
    private int[] newImg;
    private String imgPath;
    private int status;
    private int paddingType;
    private int snapIt;
    Image finalImg;
    private String savePath;
 
    Demo() {

        super();
       
        alphaEditor = new AlphaEditor();
        snapIt = 0;
        padding = new int[4];
        image = new int[2];
        newImg = new int[2];
        status = 0;
        setSize(w, h);
        more = new More();
        inOpening();
    }

    private void inOpening() {
        padding[0] = 0;
        padding[1] = 0;
        padding[2] = 0;
        padding[3] = 0;
        image[0] = 200;
        image[1] = 200;
        newImg[0] = 200;
        newImg[1] = 200;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        more.makeAlphaBackground(g, w, h);
        g.drawImage(image(), 5, 5, null);
    }

    public Image image() {
        try{
         ColorChooser cc = new ColorChooser();
        if(cc.isThere()){
          cc.readColor();
        }else{
          cc.makeColor();
          cc.readColor();
        }
        BufferedImage img = new BufferedImage(image[0], image[1], BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = img.createGraphics();
        if (snapIt == 0 || snapIt == 3 || snapIt == 4 || snapIt == 5) {
             //padding Color
            g.setColor(cc.getPaddingCode());
            g.fillRect(0, 0, image[0], image[1]);

            //Sprite Color
            g.setColor(cc.getSpriteCode());
            g.fillRect(padding[2], padding[0], newImg[0], newImg[1]);
        }
        if (snapIt != 3 && snapIt != 5) {
            if (status == 1) {

                try {
                    if (paddingType == 0) {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0] - (padding[2] + padding[3]), image[1] - (padding[0] + padding[1])), padding[2], padding[0], null);
                    } else {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0], image[1]), (padding[2] - padding[3]), (padding[0] - padding[1]), null);

                    }

                } catch (Exception ex) {
                    Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (status == 2) {
                try {
                    alphaEditor.setSource(imgPath);
                    alphaEditor.doing();
                    if (paddingType == 0) {
                        g.drawImage(alphaEditor.writeImage().getScaledInstance(image[0] - (padding[2] + padding[3]), image[1] - (padding[0] + padding[1]), Image.SCALE_SMOOTH), padding[2], padding[0], null);
                    } else {
                        g.drawImage(alphaEditor.writeImage().getScaledInstance(image[0], image[1], Image.SCALE_SMOOTH), (padding[2] - padding[3]), (padding[0] - padding[1]), null);
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    if (paddingType == 0) {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0] - (padding[2] + padding[3]), image[1] - (padding[0] + padding[1])), padding[2], padding[0], null);
                    } else {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0], image[1]), (padding[2] - padding[3]), (padding[0] - padding[1]), null);

                    }
                } catch (IOException ex) {
                    if (paddingType == 0) {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0] - (padding[2] + padding[3]), image[1] - (padding[0] + padding[1])), padding[2], padding[0], null);
                    } else {
                        g.drawImage(more.setBackgroundEngine(imgPath, image[0], image[1]), (padding[2] - padding[3]), (padding[0] - padding[1]), null);

                    }
                }
            }
        }
        try {
            if (snapIt == 1) {
                javax.swing.JOptionPane.showMessageDialog(null, "Done");
                String sp = (snapIt == 1) ? imgPath : savePath;
                ImageIO.write(img, "PNG", new File(sp));
                snapIt = 0;
                inOpening();
                repaint();
            }
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image i;
        if (snapIt == 2 || snapIt == 3 || snapIt == 4) {
            i = img.getScaledInstance(image[0], image[1], Image.SCALE_SMOOTH);
        } else {
            i = img.getScaledInstance(275, 130, Image.SCALE_SMOOTH);
        }
        return i;
  
    }
  catch(IllegalArgumentException e){
         Singleton.response("Error D138: IllegalArguement Width(0)  and Height(0)");
        }
        return null;
}

    public void setData(int w, int h, int pt, int pb, int pl, int pr, String path, int index, int paddingTyp) {
        padding[0] = pt;
        padding[1] = pb;
        padding[2] = pl;
        padding[3] = pr;
        image[0] = w;
        image[1] = h;
        newImg[0] = w - (pl + pr);
        newImg[1] = h - (pt + pb);
        imgPath = path;
        status = (index == 0) ? 1 : (index == 1) ? 2 : 3;
        // System.err.println(status);
        paddingType = paddingTyp;
        repaint();
    }

    public void paintAgain() {
        repaint();
    }

    public void takeImage(String path, int saveFor) {
        savePath = path;
        snapIt = saveFor;
    }

    private class More extends Singleton {
    }
}
