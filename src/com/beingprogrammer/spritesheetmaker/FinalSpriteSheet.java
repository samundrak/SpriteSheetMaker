package com.beingprogrammer.spritesheetmaker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

public class FinalSpriteSheet extends JLabel {

    int w = 500;
    int h = 500;
    More more;
    private String def;
    private BufferedImage img;

    FinalSpriteSheet() {
        super();
        setSize(w, h);
        more = new More();
        def = More.TRANSPARENT;
        img = null;
    }
    int white = 0;

    @Override
    protected void paintComponent(Graphics g) {

        int box = 2;
        super.paintComponent(g);

        more.makeAlphaBackground(g, w, h);
        if (img != null) {
            g.drawImage(this.img, 0, 0, this);

        }
    }

    public void setImage(BufferedImage path) {
        this.img = path;
        repaint();
    }
    public static void reset(){
      new FinalSpriteSheet().img = null;
      new FinalSpriteSheet().repaint();
    }
    private class More extends Singleton {
    }
}
