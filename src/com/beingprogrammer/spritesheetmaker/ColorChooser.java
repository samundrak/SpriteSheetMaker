package com.beingprogrammer.spritesheetmaker;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ColorChooser  {

 private File path;
 static Color padding,margin,sprite;
         
    ColorChooser(){
       path = new File(new File("color.ssm").getAbsolutePath());
       padding = new Color(136, 0, 204);
       margin = new Color(255,0,0);
       sprite = new Color(43, 214, 124);
    }
    
    boolean isThere(){
       if(path.exists()){
         return true;
       }else{
        return false;
       }
    }

    
   void readColor() {
         if(isThere() == true){
             FileInputStream fis = null;
            try {
                fis = new FileInputStream(path);
                DataInputStream dis = new DataInputStream(fis);
                String[] c = new String[3];
                String x;
                int  i = 0;
                while((x = dis.readLine()) != null){
                   c[i] = x;
                    i++;
                }
                padding =  getColorValue(c[0]);
                margin  =    getColorValue(c[1]);
                sprite  =    getColorValue(c[2]);
            } catch (IOException ex) {
                Singleton.response("Error CC47: File not Found");
                makeColor();
            }   finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Singleton.response("Error CC53: Error Closing file");
                }
            }
             
         }else{
           makeColor();
           readColor();
       }
    }
    
    void makeColor(){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
             dos.writeBytes(getColor(padding)+"\n"+getColor(margin)+"\n"+getColor(sprite));
            
        } catch (IOException ex) {
            Singleton.response("Error CC81: Error while opening file\n Color file is missing");
        }   finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Singleton.response("Error CC86: Error while closing file");
            }
        }
       
    }

    
     public Color getPaddingCode() {
        return padding;
    }

    public Color getMarginCode() {
         return margin;
    }

    public Color getSpriteCode() {
        return sprite;
    }
    public String getColor(Color c){
       int r,g,b ;
         r = c.getRed();
         g = c.getGreen();
         b = c.getBlue();
         String s = Extra.getString(r)+"-"+Extra.getString(g)+"-"+Extra.getString(b);
         return s;
    }

    public Color getColorValue(String string) {
        return colorSplit(string);
    }

    
    
    private Color colorSplit(String s){
       String[] a = s.split("-");
       int[] i = new int[3];
       int b = 0;
       for(String x : a){
         i[b] = Extra.getInt(x);
         b++;
       }
       return new Color(i[0],i[1],i[2]);
    }
}
