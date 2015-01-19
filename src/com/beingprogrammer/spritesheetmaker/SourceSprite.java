package com.beingprogrammer.spritesheetmaker;

import java.awt.Graphics;
import javax.swing.JLabel;

public class SourceSprite extends JLabel {

    int w = 260;
    int h = 200;
    More more;
    private String img, def;

    SourceSprite() {
        super();
        setSize(w, h);
        more = new More();
        def = More.TRANSPARENT;
        img = "0";
    }
    int white = 0;

    @Override
    protected void paintComponent(Graphics g) {

        int box = 2;
        super.paintComponent(g);

        more.makeAlphaBackground(g, w, h);
        if (!img.equals("0")) {
            g.drawImage(more.setBackgroundEngine(this.img, w, h), 0, 0, null);
        }
    }
public static void reset(){
      new SourceSprite().img = "0";
      new SourceSprite().repaint();
    }
    public void setImage(String path) {
        this.img = path;
        repaint();
    }

    private class More extends Singleton {
    }
}
