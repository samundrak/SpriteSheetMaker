package com.beingprogrammer.spritesheetmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class Singleton {

    JFileChooser chooser;
    private static BufferedImage img1;
    private static Image img2;
    static String RAWFOLDER = "/spritemakerrawfolder";
    static String ASSETS = "/assets";
    static String TEMP = "/temp";
    static String[] IMAGEFORMAT = {"jpg", "png", "jpeg", "gif"};
    public static String TRANSPARENT = "src/spritemaker/transparent.png";
    private int spriteSheetWidth;
    private int spriteSheetHeight;
    public static int cancel;
    public static   ArrayList<String> innerFiles = new ArrayList();
    
    protected String addInClassPath(String get) {
        String x = "src/spritemaker/" + get;
        return x;
    }

    protected String fileChooser(View v, int type, DefaultListModel model, String open) {
        //0 = File; 1 = Directory;


        chooser = new JFileChooser();
        int selected = 20;
        FileFilter filter;
        chooser.setCurrentDirectory(new java.io.File(open));
        switch (type) {
            case 0:
                chooser.setDialogTitle("Please choose Image file");
                filter = new FileNameExtensionFilter("Only Image file", new String[]{"png", "jpg", "jpeg", "gif"});
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(filter);
                chooser.addChoosableFileFilter(filter);
                selected = chooser.showOpenDialog(v);
                break;
            case 1:
                chooser.setDialogTitle("Please choose Image folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                selected = chooser.showOpenDialog(v);
                break;
            case 2:
                chooser.setDialogTitle("Select Folder to save File");
                filter = new FileNameExtensionFilter("only Image File", new String[]{"png", "jpg", "jpeg", "gif"});
                chooser.setFileFilter(filter);
                chooser.addChoosableFileFilter(filter);
                selected = chooser.showSaveDialog(v);
                break;
        }
        if (selected == JFileChooser.APPROVE_OPTION) {
            String paths = chooser.getSelectedFile().toString();
            if (!paths.isEmpty()) {
                File f = new File(paths);
                if (f.isFile()) {
                    model.addElement(paths);
                }
                return paths;
            }
        }
        return null;
    }

    protected static void response(String msg) {
        javax.swing.JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
    protected static void response(String msg,int i) {
        javax.swing.JOptionPane.showMessageDialog(null, msg,"Success",JOptionPane.INFORMATION_MESSAGE);
    }
    protected void checkFile(String file) {
        try {
            File files = new File(file);
            if (!files.exists() && !files.canExecute() && !files.canRead() && !files.isFile()) {
                response("Error S93: Problem in IOException , Program going to be close.");
                //System.exit(0);
            }
        } catch (Exception e) {
            Singleton.progress(0, "hide", 0);
        }
    }

    protected String getFileName(String file) {
        checkFile(file);
        File files = new File(file);
        return files.getName();
    }

    protected String getDimension(String file, int type) {
        checkFile(file);
        String x = "0";
        File files;
        BufferedImage img;
        try {

            files = new File(file);
            img = ImageIO.read(files);

            switch (type) {
                case 0:
                    //width
                    x = getString(img.getWidth());
                    break;

                case 1:
                    //height
                    x = getString(img.getHeight());
                    break;
                case 2:
                    //size
                    int i = (int) (files.length() / 1024);
                    x = getString(i);
                    break;
                case 3:
                    //file name
                    x = files.getName();
                    break;
            }

        } catch (NullPointerException ex) {
             
            //response("Error S143: " + "No File Found");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response("Error S144: " + file);
        }
        return x;
    }

    protected ImageIcon iconImage(String file, JLabel j) {
        try {
            BufferedImage img = ImageIO.read(new File(file));
            System.out.println(j.getWidth() + "" + j.getHeight());

            Image img2 = img.getScaledInstance(j.getWidth(), j.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img2);
            j.setIcon(new ImageIcon(img2));
            return icon;
        } catch (IOException ex) {
            Logger.getLogger(Singleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected String getString(int i) {
        try {
            return Integer.toString(i);
        } catch (Exception e) {
            response("Error S163: Integer Conversion");
        }
        return null;
    }

    protected int getInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }

    }

    protected Image setBackgroundEngine(String path, int w, int h) {
        try {
            img1 = ImageIO.read(new File(path));
            img2 = img1.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return img2;
        } catch (Exception ex) {
            //Logger.getLogger(Singleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected void detectWidthAndHeight(int inColumn, int total, int width, int height, int left, int bottom, int[] p) {
        int row;
        try {
            row = (int) total / inColumn;
        } catch (Exception ex) {
            row = 0;
        }
        int newWidth = width + left;// + p[2] + p[3];
        int newHeight = height + bottom;// + p[0] + p[1];
        int SpriteSheetWidth = newWidth * inColumn;
        int SpriteSheetHeight = newHeight * row;
        setSpriteSheetHeight(SpriteSheetHeight);
        setSpriteSheetWidth(SpriteSheetWidth);
        //System.out.printf("row: %s \n new height : %s \n new ss: %s \n total: %s \n inColumn : %s",row,newHeight,SpriteSheetHeight,total,inColumn);
    }

    protected void detectSpriteInRow(int total) {
    }

    protected int getNewSpriteSheetWidth() {
        return spriteSheetWidth;
    }

    protected int getNewSpriteSheetHeight() {
        return spriteSheetHeight;
    }

    private void setSpriteSheetHeight(int SpriteSheetHeight) {
        spriteSheetHeight = SpriteSheetHeight;
    }

    private void setSpriteSheetWidth(int SpriteSheetWidth) {
        spriteSheetWidth = SpriteSheetWidth;
    }

    protected void makeAlphaBackground(Graphics g, int w, int h) {
        int white = 0;
        for (int i = 0; i <= h; i += 30) {

            for (int x = 0; x <= w; x += 30) {
                if (white == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x, i, 30, 30);
                    white = 1;
                } else {
                    Color color = new Color(204, 204, 204);
                    g.setColor(color);
                    g.fillRect(x, i, 30, 30);
                    white = 0;
                }
                if (x >= w) {
                    break;
                }
            }
        }
        white = (white == 0) ? 1 : 0;
    }

    public static void progress(int value, String x, int total) {
        double p = 0;
        try {
            p = (double) value / total * 100;
        } catch (ArithmeticException e) {
            p = 0;
        }
        int v = (int) p;
        if (x.equals("show")) {
            View.progressBar.setEnabled(true);
            View.progressBar.setValue(v);

        } else {
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(500);
                    } catch (Exception e) {
                    } finally {
                        View.progressBar.setValue(0);
                        View.progressBar.setEnabled(false);

                    }
                }
            }.start();

        }
    }

    public void searchFile(String dir, DefaultListModel lm, File directory) {
        //select directory
        if (cancel != 1) {
            Path paths = Paths.get(dir);
            if (Files.isDirectory(paths)) {
                try {
                    //System.out.print("this is path");
                    DirectoryStream<Path> stream = Files.newDirectoryStream(paths);

                    // System.out.print(stream.);

                    for (Path file : stream) {
                        if(innerFiles.size() >= 500){
                               break;
                        }
                        if (Files.isRegularFile(file)) {
                            if (file.toString().endsWith("png") || file.toString().endsWith("jpeg") || file.toString().endsWith("jpg")) {
                               innerFiles .add(file.toString());
                                if (BackView.progressOfImportingFiles[0] >= 100) {
                                    BackView.progressOfImportingFiles[0] = 0;
                                }
                                BackView.progressOfImportingFiles[0]++;
                            }
                        } else if (Files.isDirectory(file)) {
                            searchFile(file.toString(), lm, directory);
                        }

                    }
                   View.setSourceFiles.setSelectedIndex(0);

                } catch (IOException ex) {
                    response("Error S306: "+ex.getLocalizedMessage());
                }

            }
        }
        //BackView.progressOfImportingFiles[1] = 1000;
    }
}
