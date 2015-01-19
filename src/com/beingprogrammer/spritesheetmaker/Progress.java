package com.beingprogrammer.spritesheetmaker;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class Progress {

    private int maximum;
    private int minimum;
    private JProgressBar progressBar;
    private int value;

    Progress(String msg) {
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder(msg);
        progressBar.setBorder(border);
        setMaximum(0);
        setMinimum(0);
        setValue(0);
    }

    public static void show(String msg) {
//   int option = JOptionPane.showConfirmDialog(j, new Progress(msg), "Enter Dimension Padding values", JOptionPane.NO_OPTION);
//        //JOptionPane.setRootFrame(j);
//        if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.CANCEL_OPTION || option == JOptionPane.OK_OPTION) {
//           
//            
//          if (option == JOptionPane.OK_OPTION) {
//                int x = JOptionPane.showConfirmDialog(j, "Are you sure you want rescale image with new Dimension and padding values?");
//                if(x == 0){
//                        
//                }
//                
        //     }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int i) {
        value = i;
        progressBar.setValue(i);
    }

    public void setMaximum(int i) {
        maximum = i;
        progressBar.setMaximum(i);
    }

    public void setMinimum(int i) {
        minimum = i;
        progressBar.setMinimum(i);
    }

    public int getMaximum() {
        return maximum;
    }

    public int getMinimum() {
        return minimum;
    }
}
