package com.beingprogrammer.spritesheetmaker;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

public class SpriteSheet extends JLabel {

    int w = 300;
    int h = 300;
    private int spriteSheetWidth;
    private int spriteSheetHeight;
    int iX, iY = 0;
    More more;
    private String sourcePath;
    private int bottomSpace;
    private int leftSpace;
    private int status;
    private SingleSpriteMaker singleSprite;
    private int[] dimension;
    private int[] padding;
    private int index;
    private int paddingType;
    private int inRow;
    private String rawPath;
    private File tempDir;
    private String currentImage;
    private DefaultListModel listModel;
    private final SpriteCreate spriteCreate;
    private int saveIt = 0;
    private String saveSpriteSheetIn;
    private String extension;
    public static int[] progress;
    public static int snapIt;
    public static int showIt;
    public static int canvasWidth, canvasHeight;
     SpriteSheet() {
        super();
     
        snapIt = 0;
        showIt = 0;
        canvasWidth = 0;
        canvasHeight = 0;
        dimension = new int[2];
        padding = new int[4];
        progress = new int[2];
        setSize(w, h);
        more = new More();
        status = 0;
        spriteCreate = new SpriteCreate();
    }
    int white = 0;

    void initSpriteSheet(String rawPath) {
        this.rawPath = rawPath;
        makeTempDir();
    }

    private void makeTempDir() {
        tempDir = new File(rawPath + More.TEMP);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

    }

    private void delTempDir() {
        if (tempDir.exists()) {
            tempDir.delete();
        }
    }
    int i = 0;

    BufferedImage saveSprites() {

        try {
             ColorChooser cc = new ColorChooser();
        if(cc.isThere()){
          cc.readColor();
        }else{
          cc.makeColor();
          cc.readColor();
        }
            BufferedImage img = new BufferedImage(spriteSheetWidth, spriteSheetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.createGraphics();
            int t = 0;
            progress[1] = listModel.getSize();
            for (int i = 0; i <= listModel.getSize() - 1; i++) {
                if(Singleton.cancel == 1){break;}
                if (t >= inRow) {
                    t = 0;
                    iX = 0;
                    iY += dimension[1] + bottomSpace;
                }
                String path = listModel.get(i).toString();
                SpriteCreate spriteCreate = new SpriteCreate();
                spriteCreate.setData(dimension[0], dimension[1], padding[0], padding[1], padding[2], padding[3], path, index, paddingType);
                if (snapIt == 1) {
                    spriteCreate.takeImage(null, 2);
                } else if (snapIt == 4) {
                    spriteCreate.takeImage(null, 2);
                }else if (snapIt == 5) {
                    spriteCreate.takeImage(null, 4);
                }
                else {
                    spriteCreate.takeImage(null, 3);
                }
//            x - the x coordinate of the rectangle to be filled.
//            y - the y coordinate of the rectangle to be filled.
//            width - the width of the rectangle to be filled.
//            height - the height of the rectangle to be filled.
                spriteCreate.paintAgain();
                if (snapIt != 1 && snapIt != 4) {
                    g.setColor(cc.getMarginCode());
                    g.fillRect(iX, iY, (dimension[0] + leftSpace), (dimension[1] + bottomSpace));
                }
               // System.out.println(path);
                g.drawImage(spriteCreate.image(), iX, iY, null);
                iX += dimension[0] + leftSpace;
                progress[0] = i;
                t++;
            }
            if(Singleton.cancel == 1){Singleton.cancel = 0;}else{
            if (snapIt == 1) {
                try {
                    ImageIO.write(img, "PNG", new File(saveSpriteSheetIn));
                } catch (IOException ex) {
                    Singleton.response("Error SS123: Sorry! Unable to Save Image");
                }
            } else {
                Image i = img.getScaledInstance(canvasWidth, canvasHeight, Image.SCALE_SMOOTH);
                BufferedImage bi = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);//285 , 155
                Graphics gg = bi.createGraphics();
                gg.drawImage(i, 0, 0, null);
                return bi;

            }
            g.dispose();}
            Singleton.progress(0, "hide", 0);
        } catch (Exception e) {
        } catch (OutOfMemoryError oe) {
            Singleton.response("Error SS141: To Much Files("+Singleton.innerFiles.size()+") or Image in row must be below 100  \n Program Can't handle \n Press CTRL + T to enable Application again");
            Singleton.progress(0, "hide", 0);
        }
        progress[0] = 0;
        return null;
    }

    public void setData(int ssw, int ssh, DefaultListModel path, int lSpace, int rSpace, int inrow) {
        spriteSheetWidth = ssw;        //width of spritesheet
        spriteSheetHeight = ssh;        //height of spritesheet
        inRow = inrow;      //images in a row
        listModel = path;       //path to source files
        leftSpace = lSpace;     //margin right
        bottomSpace = rSpace;     //margin bottom
        status = 1;          //status
    }

    public void spriteSettings(int w, int h, int p1, int p2, int p3, int p4, String img, int inDex, int pType) {

        dimension[0] = w;          //width of sprite
        dimension[1] = h;          //height of sprite
        padding[0] = p1;         //padding top
        padding[1] = p2;         //padding bottom
        padding[2] = p3;         //padding left
        padding[3] = p4;         //padding right
        index = inDex;      //index
        paddingType = pType;      //padding type (Resize image or overflow hide)
        currentImage = img;
    }

    public void paintAgain() {
        repaint();
    }

    public void saveIt(int i) {
        saveIt = i;
    }

    void saveIn(String saveSpriteSheetIn, String extension) {
        this.saveSpriteSheetIn = saveSpriteSheetIn;
        this.extension = extension;
    }

    private class More extends Singleton {
        
    }
   
}
