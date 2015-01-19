package com.beingprogrammer.spritesheetmaker;

public class SpriteMaker {

    public static void main(String[] args) {
        // TODO code application logic here
        
        Link link = new Link();
        
        
        if (link.isFileExists()) {
            View.main(args);
        } else {
            link.linkBase();
            main(args);
        }
    }
}
