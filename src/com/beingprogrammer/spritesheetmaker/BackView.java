package com.beingprogrammer.spritesheetmaker;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BackView extends Methods {

    static String scannedFile = null;
    Thread t1, t2 = null;
    More more = new More();
    DefaultListModel lm;
    int spriteDemoToggle = 0;
    OptionDialog optionDialog;
    SpriteView demo;
    SourceSprite sourceSprite;
    SpriteSheetView spriteSheetView;
    FinalSpriteSheet finalSpriteSheet;
    JPopupMenu jpm;
    String orginalRawFolder;
    int showImageToggle = 0;
    public static int[] progressOfImportingFiles;
    Menu menu;
    ColorChooser cc;
    BackView(View v) {
        super(v);
        cc = new ColorChooser();
            if(cc.isThere() == false){
               cc.makeColor();
               cc.readColor();
            }else{
              cc.readColor();
            }
        context.colorPadding.setBackground(cc.getPaddingCode());
        context.colorPadding1.setBackground(cc.getPaddingCode());
        context.colorMargin.setBackground(cc.getMarginCode());
        context.colorSprite.setBackground(cc.getSpriteCode());
        context.colorSprite1.setBackground(cc.getSpriteCode());
        menu = new Menu();
        demo = new SpriteView();
        finalSpriteSheet = new FinalSpriteSheet();
        sourceSprite = new SourceSprite();
        spriteSheetView = new SpriteSheetView();
        progressOfImportingFiles = new int[2];
        lm = new DefaultListModel();
        optionDialog = new OptionDialog(v);
        onListItemSelected();
        onListItemDoubleClicked();
        paddingTypeSelected(context.paddingType);
        paddingTypeSelected(context.getImageType);
        jpm = new JPopupMenu();
        context.sourceSpriteViewPanel.add(sourceSprite);
        context.spriteDemoPanel.add(demo);
        context.spriteDemoPanel2.add(spriteSheetView);
        context.finalSpriteViewPanel.add(finalSpriteSheet);
        getRawFolderName();

    }

    public void paddingTypeSelected(JComboBox e) {
        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                onChange();
            }
        });
    }

    public void delItem(int i) {
        try {
            if (lm.size() >= 0) {
                disableComponents();
                String wm;
                if (i == 1) {
                    wm = "Are you sure you want to Remove all item?";
                } else {
                    wm = "Are you sure you want to Remove item?";
                }
                int confirm = JOptionPane.showConfirmDialog(context, wm);
                if (confirm == 0) {

                    if (i == 1) {
                        File f = new File(getRawFolderName() + More.RAWFOLDER + More.ASSETS);
                        Path p = f.toPath();
                        File[] ff = f.listFiles();
                        try {
                            for (File x : ff) {
                                Files.deleteIfExists(x.toPath());
                            }
                        } catch (Exception e) {
                            enableComponents();
                        }
                        More.response("Success BV101: All File has been deleted",0);
                        lm.clear();
                        View.setSourceFiles.setModel(lm);

                    } else {
                        String item = View.setSourceFiles.getSelectedValue().toString();
                        Files.deleteIfExists(new File(item).toPath());
                        lm.remove(View.setSourceFiles.getSelectedIndex());
                        View.setSourceFiles.setModel(lm);
                    }
                }

            }
        } catch (IOException ex) {
            More.response("Error BV115: " + ex.getLocalizedMessage());
        } catch (NullPointerException ex) {
            More.response("Error BV118: " + ex.getLocalizedMessage());
        }
        enableComponents();
    }

    public void onListItemDoubleClicked() {
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (View.setSourceFiles.getSelectedIndex() >= 0) {
                    if (e.getClickCount() == 2) {
                        optionDialog.showDialog(View.setSourceFiles.getSelectedIndex(), View.setSourceFiles);
                    }
                }
            }
        };
        View.setSourceFiles.addMouseListener(mouseListener);
    }

    private void onListItemSelected() {
        View.setSourceFiles.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    // code here
                    JList source = (JList) lse.getSource();
                    try {
                        String selected = source.getSelectedValue().toString();
                        sourceSprite.setImage(selected);
                    } catch (NullPointerException e) {
                    }

                    //context.moveTo.setEnabled(true);
                    context.delItem.setEnabled(true);
                    setSpriteWidth(more.getDimension(getSelectedItem(), 0));
                    setSpriteHeight(more.getDimension(getSelectedItem(), 1));
                    onChange();

                    //SpriteDisplay.setIcon(new ImageIcon(more.setBackgroundEngine(selected, 246, 158)));
                }
            }
        });
    }

    void rawFolderClicked() {
        String path = more.fileChooser(context, 1, null, new Link().getRawFolder());
        if (path != null && new File(path).isDirectory()) {
            File file = new File(path + More.RAWFOLDER);
            if (!file.exists()) {
                file.mkdir();
            }
            setRawFolder(path);
        } else {
            rawFolderClicked();
        }
    }

    void sourceFolderClicked() {
        Thread t1, t2;
        t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (progressOfImportingFiles[0] >= 1000) {
                            Singleton.progress(0, "hide", 0);
//                            View.cancel.setEnabled(false);
                            break;
                        }
                        sleep(500);
                    } catch (InterruptedException e) {
                    } finally {
                       // setTotalSprites(more.getString(Singleton.innerFiles.size()));
                        int x = (progressOfImportingFiles[0] >= 100) ? 100 : progressOfImportingFiles[0];
                        Singleton.progress(x, "show", 100);
                    }
                }
            }
        };
        t2 = new Thread() {
            @Override
            public void run() {
                sourceFolderTask(0);
            }
        };
        t1.start();
        t2.start();

    }

    void sourceFolderTask(int addType) {
        //System.out.println(getRawFolder());
        if (getRawFolder().equals("First Select Raw Folder ....")) {
            More.response("First please select any raw folder ");
        } else {
            String path = null;
            try {
                if (addType == 1) {
                    path = more.fileChooser(context, 0, lm, getSourceFolderName());
                } else if (addType == 2) {
                    path = getRawFolder() + More.RAWFOLDER + More.ASSETS;
                } else {
                    path = more.fileChooser(context, 1, lm, getSourceFolderName());
                }
            } catch (NullPointerException e) {
            }

            if (path != null && new File(path).isDirectory()) {


                try {
                    File dir = new File(getRawFolder() + More.RAWFOLDER + More.ASSETS);
                    dir.mkdir();

                    if (getImportType() == 1) {
//                        View.cancel.setEnabled(true);
                        more.searchFile(path, lm, dir);
//                        View.cancel.setEnabled(false);
                        Iterator it = Singleton.innerFiles.iterator();
                        while (it.hasNext()) {
                            lm.addElement(it.next().toString());
                        }
                         Singleton.response("Error BV298: Only "+500+" files are allowed.");
                            

                    } else {
                        File[] files;
                        if (addType == 1) {
                            File file = new File(path);
                            files = new File[1];
                            files[0] = file;
                        } else {
                            File file = new File(path);
                            files = file.listFiles();
                        }
                        progressOfImportingFiles[1] = files.length;
                        progressOfImportingFiles[0] = 1;
                        disableComponents();
                        for (File x : files) {
                            try {
                                //InputStream is = new InputStream(x.toPath());
                                for (String ext : Singleton.IMAGEFORMAT) {
                                    if (x.toString().endsWith(ext)) {

                                        Files.copy(x.toPath(), dir.toPath().resolve(x.getName()));

                                    }

                                }


                            } catch (IOException ex) {
                                //   more.response(ex.getMessage());
                            }
                            progressOfImportingFiles[0]++;
                        }

                        addElement(dir);
                    }
                    progressOfImportingFiles[0] = 0;

                    Path p = new File(dir + More.RAWFOLDER).toPath();
                    Files.deleteIfExists(p);
                    if (lm.size() >= 4) {
                        setSpritesheetRow("4");
                    }
                    if (lm.size() > 0) {
                        enableComponents();
                    }
                    enableComponents();
                    String a, b;
                    a = more.getDimension(lm.get(0).toString(), 0);
                    b = more.getDimension(lm.get(0).toString(), 1);
                    setSpriteWidth(a);
                    setSpriteHeight(b);
                    setSourceFolder(path);
                    View.setSourceFiles.setModel(lm);
                    View.setSourceFiles.setSelectedIndex(0);
                    Singleton.cancel = 0;
                   // System.out.println(lm.size());
                    setTotalSprites(more.getString(lm.size()));
                    //onPreviewClicked();
                } catch (ArrayIndexOutOfBoundsException e) {
                    More.response("Error BV296: " + "File not found");
                } catch (Exception ex) {
                    disableComponents();
                    File f = new File(path);
                    File[] ff = f.listFiles();
                    try {
                        if (ff.length >= 1) {
                            enableComponents();
                        }
                    } catch (NullPointerException e) {
                        More.response("Error BV297: No any File Found");
                    }
                    ex.printStackTrace();
                    More.response("Error BV293: Error Loading Files");
                    rawFolderClicked();
                    sourceFolderClicked();
                }

            }
        }
    }

    private void addElement(File file) {
        File[] newFiles = file.listFiles();
        for (File x : newFiles) {
            //
            if (x.toString().endsWith("png") || x.toString().endsWith("jpg") || x.toString().endsWith("bmp")) {
                lm.addElement(x.toString());
            }
        }
    }

    public void onChange() {
        try {
            int[] p = {getPaddingTop(), getPaddingBottom(), getPaddingLeft(), getPaddingRight()};
            more.newWidthAndHeight(getSpritesheetRow(), lm.size(), getSpriteWidth(), getSpriteHeight(), getSpriteSpaceLeft(), getSpriteSpaceBottom(), p);
            if (getSelectedItem() != null) {
                demo.setData(getSpriteWidth(), getSpriteHeight(), getPaddingTop(), getPaddingBottom(), getPaddingLeft(), getPaddingRight(), getSelectedItem(), getSelectedImageItem(), getSelectedPaddingItem());
            }
            demo.paintAgain();
        } catch (Exception e) {
        }
    }

    void onWindowClosed() {
        File f = new File(getRawFolderName() + More.RAWFOLDER);
        f.delete();
    }

    void applyToSprite() {
        try {
            int x;
            if (getImportType() == 1) {
                x = JOptionPane.showConfirmDialog(context, "Applying this settings will replace the orginal file, Dou You want to apply?");
            } else {
                x = JOptionPane.showConfirmDialog(context, "Applying this settings will replace same file but willn't replace orginal file");
            }
            if (x == 0) {

                demo.takeImage(View.setSourceFiles.getSelectedValue().toString(), 1);
                onChange();
                resetInfo();
            }
        } catch (NullPointerException e) {
            More.response("Error BV341: " + e.getLocalizedMessage());
        }
    }

    void openRawFolder() {

        try {
            Desktop.getDesktop().open(new File(getSourceFolderName()));
        } catch (IOException ex) {
            More.response("Error BV345: " + ex.getMessage());
        }
    }

    void onPreviewClicked() {
        
        if (getSpriteSheetCheckBox() == true) {
            if (getSpriteSheetImageToggle().equals("Hide Image")) {
                spriteSheetTasks(null, null, 5);
            } else {
                spriteSheetTasks(null, null, 0);
            }
        } else if (getFinalSpriteSheetCheckBox() == true) {
//            View.cancel.setEnabled(true);
            spriteSheetTasks(null, null, 4);
////            View.cancel.setEnabled(false);
            Singleton.progress(0, "hide", 0);
        }
        if (getSpriteSheetCheckBox() == true && getFinalSpriteSheetCheckBox() == true) {
 //          View.cancel.setEnabled(true);
            if (getSpriteSheetImageToggle().equals("Hide Image")) {
                spriteSheetTasks(null, null, 5);
            } else {
                spriteSheetTasks(null, null, 0);
            }

            threadChecker();
   //         View.cancel.setEnabled(false);
            Singleton.progress(0, "hide", 0);
        }

    }

    public void threadChecker() {
        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (!t1.isAlive()) {
                                break;
                                //                  if(!t2.isAlive()){
                                //                     
                                //                     System.out.println("thread 2 is not running");
                                //                     break;
                                //                  }else{
                                //                     
                                //                     System.out.println("thread 2 is running");
                                //                  }

                            } else {
                            }
                            sleep(1000);
                        } catch (Exception e) {
                        }


                    }
                }
            ;
            };
            t.start();
            t.join();
            spriteSheetTasks(null, null, 4);
        } catch (InterruptedException ex) {
            Singleton.response("Error 419: " + ex.getLocalizedMessage());
        }

    }

    private void spriteSheetTasks(final String saveSpriteSheetIn, final String ext, final int view) {
        SpriteSheet.snapIt = view;

        disableComponents();


        t1 = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (true) {

                    try {
                        if (SpriteSheet.progress[0] == SpriteSheet.progress[1] - 1) {
                            if (SpriteSheet.snapIt == 1 || getTotalSprites() <= 0) {
                                Singleton.response("Spritesheet has been created successfully",0);
//                                View.cancel.setEnabled(false);
                            }
                            Singleton.progress(0, "hide", 0);
                            View.progressBar.setToolTipText("No Tasks");
                            enableComponents();
                            break;
                        }
                        sleep(20);
                    } catch (InterruptedException ex) {
                    } catch (NullPointerException ex) {
                        More.response("Error BV427: " + ex.getLocalizedMessage());
                        enableComponents();
                    } finally {
                        try {
                            if(getTotalSprites() <= 0) {
                                break;
                            }
                           // System.out.println(SpriteSheet.progress[0]);
                            Singleton.progress(SpriteSheet.progress[0], "show", SpriteSheet.progress[1] - 1);
                            double p = (double) SpriteSheet.progress[0] / (SpriteSheet.progress[1] - 1) * 100;
                            View.progressBar.setToolTipText((int) p + "% Completed");
                        } catch (NullPointerException e) {
                            onPreviewClicked();
                        }
                    }
                    i++;
                }

            }
        };

        t2 = new Thread() {
            @Override
            public void run() {
                SpriteSheet orginal = new SpriteSheet();
                SpriteSheet.snapIt = view;
                switch (view) {
                    case 0:
                    case 5:
                        spriteSheetView.showIt(1);
                        SpriteSheet.canvasWidth = 285;
                        SpriteSheet.canvasHeight = 130;

                        break;
                    case 4:
                        SpriteSheet.canvasWidth = 470;
                        SpriteSheet.canvasHeight = 205;

                        break;

                }
                orginal.initSpriteSheet(getRawFolderName() + More.RAWFOLDER);
                orginal.setData(getSpritesheetWidth(), getSpritesheetHeight(), lm, getSpriteSpaceLeft(), getSpriteSpaceBottom(), getSpritesheetRow());
                orginal.spriteSettings(getSpriteWidth(), getSpriteHeight(), getPaddingTop(), getPaddingBottom(), getPaddingLeft(), getPaddingRight(), getSelectedItem(), getSelectedImageItem(), getSelectedPaddingItem());
                orginal.saveIn(saveSpriteSheetIn, null);
                BufferedImage bi = orginal.saveSprites();
                orginal.paintAgain();

                try {
                    if (view == 0 || view == 5) {
                        spriteSheetView.image(bi);
                    }
                    if (view == 4) {
                        finalSpriteSheet.setImage(bi);
                    }
                } catch (IllegalArgumentException e) {
                    enableComponents();
                    More.response("Error BV479: Don't Insert Data that program can't handle.\nMust be number above 0");
                    setSpriteHeight("100");
                    setSpriteWidth("100");
                    setSpritesheetRow("1");
                    setTotalSprites(more.getString(lm.size()));
                    onChange();
                }

            }
        };

        t2.start();
        t1.start();

    }

//    void spriteDemoToggleClicked() {
//        if(getSpriteDemoToggle().equals("Hide Background")){
//            setSpriteDemoToggle("Show Background");
//            spriteDemoToggle = 1;
//            onChange();
//        }else{
//          setSpriteDemoToggle("Hide Background");
//          spriteDemoToggle = 0;
//            onChange();
//        }
//    }
//    void showImageToggle() {
//        if(getShowImageToggle().equals("Hide Image")){
//            setShowImageToggle("Show Image");
//            showImageToggle = 1;
//            onChange();
//        }else{
//          setShowImageToggle("Hide Image");
//          showImageToggle = 0;
//            onChange();
//        }
//    }
    void saveTheSpriteSheet() {
        
        if(lm.size() <= 0){
           
          Singleton.response("Error 555: No any files to generate SpriteSheet");
        }else{
//        View.cancel.setEnabled(true);
        String x = more.fileChooser(context, 2, lm, "c:/");
        if (x.endsWith(".png") || x.endsWith(".PNG")) {
            spriteSheetTasks(x, "png", 1);

        } else {
            spriteSheetTasks(x + ".png", "png", 1);

        }
//       View.cancel.setEnabled(false);
       Singleton.progress(0, "hide", 0);
      }
    }

    void spriteSheetImageToggle() {
        if (getSpriteSheetImageToggle().equals("Show Image")) {
            setSpriteSheetImageToggle("Hide Image");
            spriteSheetTasks(null, null, 5);
        } else {
            setSpriteSheetImageToggle("Show Image");
            spriteSheetTasks(null, null, 0);
        }
    }

    void spriteImageToggle() {
        try {
            if (getSpriteImageToggle().equals("Show Image")) {
                setSpriteImageToggle("Hide Image");
                demo.takeImage(View.setSourceFiles.getSelectedValue().toString(), 0);
                onChange();

            } else {
                setSpriteImageToggle("Show Image");
                demo.takeImage(View.setSourceFiles.getSelectedValue().toString(), 5);
                onChange();

            }
        } catch (NullPointerException e) {
            More.response("Error BV552: Please Select File");
        }
    }

    void addFile() {
        sourceFolderTask(1);
    }

    void clearFiles() {
        if (getImportType() == 0) {
            delItem(1);
            resetInfo();
        } else {
            lm.clear();
            View.setSourceFiles.setModel(lm);
            resetInfo();
        }
    }

    private class More extends Singleton {

        void newWidthAndHeight(int a, int b, int c, int d, int e, int f, int[] g) {
            detectWidthAndHeight(a, b, c, d, e, f, g);
            setSpritesheetHeight(getString(getNewSpriteSheetHeight()));
            setSpritesheetWidth(getString(getNewSpriteSheetWidth()));
        }
    }

    class Menu {

        public Menu() {
        }

        void onListItemDoubleClick() {
            onListItemDoubleClicked();
        }

        void mAddFile() {
            addFile();
        }

        void sourceFolder() {
            sourceFolderClicked();
        }

        void toggleImageType() {
            if (getImageType() == 0) {
                setImageType(1);
            } else {
                setImageType(0);
            }
        }

        void toggleImportType() {
            if (getImportType() == 0) {
                setImportType(1);
            } else {
                setImportType(0);
            }
        }

        void togglePaddingType() {
            if (getPaddingType() == 0) {
                setPaddingType(1);
            } else {
                setPaddingType(0);
            }
        }

        void mApplyToSprite() {
            applyToSprite();
        }

        void priview() {
            onPreviewClicked();
        }

        void generateSpriteSheet() {
            saveTheSpriteSheet();
        }

        void rawFolder() {
            rawFolderClicked();
        }

        void mSourceFolder() {
            sourceFolderClicked();
        }

        void showImage() {
            spriteImageToggle();
        }

        void clearAllFiles() {
            delItem(1);
        }

        void removeFile() {
            delItem(0);
        }

        void loadOldFiles() {
            sourceFolderTask(2);
        }

        void clearOldItem() {
            clearFiles();
        }

        void colorChooser(String x) {
             Color initialcolor=Color.RED; 
             Color color=JColorChooser.showDialog(context,"Select a color",initialcolor);
             int r,g,b;
             r = color.getRed();
             g = color.getGreen();
             b = color.getBlue();
             ColorChooser cc =  new ColorChooser();
             if(cc.isThere()){
              cc.readColor();
             }else{
              cc.makeColor();
              cc.readColor();
             }
               try{    
            switch (x) {
                case "padding":
                    {
                        ColorChooser.padding = cc.getColorValue(cc.getColor(color));
                        context.colorPadding.setBackground(color);
                        context.colorPadding1.setBackground(color);
                        
                        break;
                    }
                case "margin":
                    {
                        ColorChooser.margin = cc.getColorValue(cc.getColor(color));
                        context.colorMargin.setBackground(color);
                        break;
                    }
                case "sprite":
                    {
                        ColorChooser.sprite = cc.getColorValue(cc.getColor(color));
                        context.colorSprite.setBackground(color);
                        context.colorSprite1.setBackground(color);
                        break;
                    }
            }
              
             
             cc.makeColor();
             onChange();
             onPreviewClicked();
           }
             catch(NullPointerException e){}
        }

        void enableComponent() {
           enableComponents();
        }
    }
}