package com.beingprogrammer.spritesheetmaker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Link extends Singleton{

    private String LINKFILE;
    private String rawFolder;
    File file;

    public Link() {
        LINKFILE = new File("links.ssm").getAbsolutePath();
        file = new File(LINKFILE);
        rawFolder = "First Select Raw Folder ....";

    }

    public String getRawFolder() {
        readFile();
        return rawFolder;
    }

    public void setRawFolder(String x) {
        try {
            writeFile(x, null);
        } catch (IOException ex) {
           response("Error L37: " + ex.getLocalizedMessage());
        }
    }

//    public String getSourceFolder() {
//        readFile();
//        return sourceFolder;
//    }
//
//    public void setSourceFolder(String x) {
//       sourceFolder = x;
//    }
    public void linkBase() {
        JFrame j = new JFrame();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Please choose the folder where program will save temp files");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int selected = chooser.showOpenDialog(j);
        if (selected == JFileChooser.APPROVE_OPTION) {
            String paths = chooser.getSelectedFile().toString();
            if (new File(paths).isDirectory()) {
                try {
                    writeFile(paths, "Set Raw Folder");
                } catch (Exception ex) {
                    response("Error L61: " + ex.getLocalizedMessage());
                }
            }
        } else {
            System.exit(0);
        }

    }

    public boolean isFileExists() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void writeFile(String raw, String source) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            try {
                dos.writeBytes(raw + "\n" + source);
            } catch (IOException ex) {
                response("Error L86: " + ex.getLocalizedMessage());
            }
        } catch (FileNotFoundException ex) {
           response("Error L89: " + ex.getLocalizedMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                response("Error L94: " + ex.getLocalizedMessage());
            }
        }

    }

    public void readFile() {
        if (isFileExists() == true) {
            try {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                String x;
                String[] Data = new String[2];
                int i = 0;
                while ((x = dis.readLine()) != null) {
                    Data[i] = x;
                    i++;
                }

                setRawFolder(Data[0]);
                rawFolder = Data[0];
//                  setSourceFolder(Data[1]);

            } catch (IOException ex) {
                response("Error L118: " + ex.getLocalizedMessage());
                linkBase();
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                response("Error L122: " + ex.getLocalizedMessage());
                linkBase();
            }

        } else {
            try {
                writeFile("Set Raw Folder", "Set Source Folder");
            } catch (IOException ex) {
                response("Error L130: " + ex.getLocalizedMessage());
            }
        }
    }
}
