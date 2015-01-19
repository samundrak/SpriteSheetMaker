package com.beingprogrammer.spritesheetmaker;

import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class OptionDialog extends JFrame {

    More more;
    JFrame j;
    NumberFormat amountFormat;
    Object[] message;
    JFormattedTextField width, height, paddingTop, paddingBottom, paddingLeft, paddingRight;
    String detailsOf, imgFile;
    private static int shown;

    OptionDialog(JFrame j) {
        more = new More();
        amountFormat = NumberFormat.getNumberInstance();
        this.j = j;

    }

    public void showDialog(int index, JList c) {
        String file = c.getSelectedValue().toString();
        imgFile = file;
        j.setEnabled(false);
        initVar(index, file);
        setData();
    }

    private void initVar(int index, String file) {
        width = new JFormattedTextField(amountFormat);
        height = new JFormattedTextField(amountFormat);
        paddingTop = new JFormattedTextField(amountFormat);
        paddingBottom = new JFormattedTextField(amountFormat);
        paddingLeft = new JFormattedTextField(amountFormat);
        paddingRight = new JFormattedTextField(amountFormat);
        width.setText(more.getDimension(file, 0));
        height.setText(more.getDimension(file, 1));
        paddingTop.setText("0");
        paddingBottom.setText("0");
        paddingLeft.setText("0");
        paddingRight.setText("0");
    }

    private void setData() {
        message = new Object[]{
            "Enter Dimension Details:",
            "Width",
            width,
            "Height",
            height,
            "Enter Padding Values in pixel:",
            "Padding Top", paddingTop,
            "Padding Bottom", paddingBottom,
            "Padding Left", paddingLeft,
            "Padding Right", paddingRight
        };

        int option = JOptionPane.showConfirmDialog(j, message, "Enter Dimension Padding values", JOptionPane.OK_CANCEL_OPTION);
        JOptionPane.setRootFrame(j);
        if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.CANCEL_OPTION || option == JOptionPane.OK_OPTION) {
            j.setEnabled(true);

            if (option == JOptionPane.OK_OPTION) {
                int x = JOptionPane.showConfirmDialog(j, "Are you sure you want rescale image with new Dimension and padding values?");
                if (x == 0) {
                    int pt, pb, pl, pr, w, h;
                    w = more.getInt(width.getText());
                    h = more.getInt(height.getText());
                    pt = more.getInt(paddingTop.getText());
                    pb = more.getInt(paddingBottom.getText());
                    pl = more.getInt(paddingLeft.getText());
                    pr = more.getInt(paddingRight.getText());
                    GraphicsEditor gr = new GraphicsEditor();
                    gr.saveImage(gr.rescaleImage(imgFile, w, h, pt, pb, pl, pr), imgFile);

                }

            }
        }

    }

    private class More extends GraphicsEditor {
    }
}
