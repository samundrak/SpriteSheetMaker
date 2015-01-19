package com.beingprogrammer.spritesheetmaker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class SpriteSheetView extends JLabel {

    int w = 310;
    int h = 300;
    More more;
    static BufferedImage img;
    private static int show;

    SpriteSheetView() {
        super();
        img = null;
        show = 0;
        more = new More();
        setSize(w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
         more.makeAlphaBackground(g, w, h);
       
        if (show == 1) {
            g.drawImage(img, 5, 5, null);

        }
    }

    public void showIt(int i) {
        show = i;
    }
    public static void reset(){
      show = 0 ;
      new SpriteSheetView().repaint();
    }
    public void image(BufferedImage i) {
        img = i;
        try {
            ImageIO.write(img, "png", new File("c:/users/acer/desktop/file.png"));
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheetView.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }

    private class More extends Singleton {
    }
}
