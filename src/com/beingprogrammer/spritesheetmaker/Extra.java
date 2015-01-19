package com.beingprogrammer.spritesheetmaker; 
 
public class Extra {

     
      
 static int getInt(String s) {
    
        int x;
        try {
             x = Integer.parseInt(s);
        } catch (Exception e) {
            x = 0;
        }
        return x;
    }
 static String getString(int i){
    String s;
    try{
      s = Integer.toString(i);
    }
    catch(Exception e){
     s  = null;
    }
    return s;
 }
}
