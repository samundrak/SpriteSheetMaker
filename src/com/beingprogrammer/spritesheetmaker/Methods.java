/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beingprogrammer.spritesheetmaker;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Acer
 */
abstract class Methods extends Link {

    View context;

    Methods(View v) {
        super();
        context = v;
    }

    public int getImageType() {
        return context.getImageType.getSelectedIndex();
    }

    public int getPaddingType() {
        return context.paddingType.getSelectedIndex();
    }

    public int getPaddingBottom() {
        return Extra.getInt(context.getPaddingBottom.getText());
    }

    public int getPaddingLeft() {
        return Extra.getInt(context.getPaddingLeft.getText());
    }

    public int getPaddingRight() {
        return Extra.getInt(context.getPaddingRight.getText());
    }

    public int getPaddingTop() {
        return Extra.getInt(context.getPaddingTop.getText());
    }

    @Override
    public String getRawFolder() {
        return context.setRawFolder.getText();
    }

    public String getRawFolderName() {
        super.readFile();
        return super.getRawFolder();
    }

    public String getSourceFolder() {
        return context.getSourceFolder.getText();
    }

    public int getSpriteHeight() {
        return Extra.getInt(context.getSpriteHeight.getText());
    }

    public int getSpriteSpaceLeft() {
        return Extra.getInt(context.getSpriteSpaceLeft.getText());
    }

    public void setSpriteHeight(String s) {
        context.getSpriteHeight.setText(s);
    }

    public int getSpriteSpaceBottom() {
        return Extra.getInt(context.getSpriteSpaceRight.getText());
    }

    public int getSpriteWidth() {
        return Extra.getInt(context.getSpriteWidth.getText());
    }

    public void setSpriteWidth(String s) {
        context.getSpriteWidth.setText(s);
    }

    public int getSpritesheetRow() {
        return Extra.getInt(context.getSpriteRow.getText());
    }

    public int getTotalSprites() {
        return Extra.getInt(context.setTotalSprites.getText());
    }

    @Override
    public void setRawFolder(String s) {
        try {
            context.setRawFolder.setText(s);
            writeFile(s, null);
        } catch (IOException ex) {
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setSourceFolder(String s) {
        context.setSourceFolder.setText(new File(s).toString());
    }

    public String getSourceFolderName() {
        String x = context.setSourceFolder.getText();
        return x;
    }

    public void setGenerate() {
        //When generate button is clicked
    }

    public void setSetSourceFiles() {
        //
    }

    public void setSpritesheetHeight(String s) {
        context.setSpritesheetHeight.setText(s);
    }

    public void setSpritesheetRow(String s) {
        context.getSpriteRow.setText(s);
    }

    public void setSpritesheetWidth(String s) {
        context.setSpritesheetWidth.setText(s);
    }

    public void setTotalSprites(String s) {
        context.setTotalSprites.setText(s);
    }

    public void setSetpreview(JButton setpreview) {
        //when button is clicked;
    }

    public int getImportType() {
        int x = context.importType.getSelectedIndex();
        return x;
    }

    public void disableComponents() {
        context.getSpriteHeight.setEnabled(false);
        context.getSpriteWidth.setEnabled(false);
        context.getSpriteRow.setEnabled(false);
        context.getSpriteSpaceLeft.setEnabled(false);
        context.getSpriteSpaceRight.setEnabled(false);
        context.getPaddingBottom.setEnabled(false);
        context.getPaddingLeft.setEnabled(false);
        context.getPaddingTop.setEnabled(false);
        context.getPaddingRight.setEnabled(false);
        context.applySprite.setEnabled(false);
        context.setGenerate.setEnabled(false);
        context.getImageType.setEnabled(false);
        context.openRawFolder.setEnabled(false);
        context.preview.setEnabled(false);
        context.paddingType.setEnabled(false);
        //  context.imageTransform.setEnabled(false);
        context.spriteImageToggle.setEnabled(false);
        context.spriteSheetCheckBox.setEnabled(false);
        context.finalSpriteSheetCheckBox.setEnabled(false);
        View.setSourceFiles.setEnabled(false);
        context.addFile.setEnabled(false);
        context.clearFiles.setEnabled(false);
        // context.importType.setEnabled(false);
        context.delItem.setEnabled(false);

        context.menuAddFromFolder.setEnabled(false);
        context.menuAddFile.setEnabled(false);
        context.menuApplyToSprite.setEnabled(false);
        context.menuEditFileProperties.setEnabled(false);
        context.menuGenerateSpriteSheet.setEnabled(false);
        context.menuPriview.setEnabled(false);
        context.menuRawFolder.setEnabled(false);
        context.menuSourceFolder.setEnabled(false);
        context.menuToggleImageType.setEnabled(false);
        context.menuToggleImportType.setEnabled(false);
        context.menuTogglePaddingType.setEnabled(false);
        context.menuClearAllFile.setEnabled(false);
        context.menuRemoveFile.setEnabled(false);
        context.menuShowImage.setEnabled(false);
       // View.cancel.setEnabled(false);
        context.spriteSheetImageToggle.setEnabled(false);
        context.colorPadding.setEnabled(false);
        context.colorPadding1.setEnabled(false);
        context.colorMargin.setEnabled(false);
        context.colorSprite.setEnabled(false);
        context.colorSprite1.setEnabled(false);
    }

    public void enableComponents() {
        context.getSpriteHeight.setEnabled(true);
        context.getSpriteWidth.setEnabled(true);
        context.getSpriteRow.setEnabled(true);
        context.getSpriteSpaceLeft.setEnabled(true);
        context.getSpriteSpaceRight.setEnabled(true);
        context.getPaddingBottom.setEnabled(true);
        context.getPaddingLeft.setEnabled(true);
        context.getPaddingTop.setEnabled(true);
        context.getPaddingRight.setEnabled(true);
        context.applySprite.setEnabled(true);
        context.setGenerate.setEnabled(true);
        context.getImageType.setEnabled(true);
        context.openRawFolder.setEnabled(true);
        context.preview.setEnabled(true);
        context.paddingType.setEnabled(true);
//         context.imageTransform.setEnabled(true);
        context.spriteImageToggle.setEnabled(true);
        context.spriteSheetImageToggle.setEnabled(true);
        context.spriteSheetCheckBox.setEnabled(true);
        context.finalSpriteSheetCheckBox.setEnabled(true);
        View.setSourceFiles.setEnabled(true);
        context.addFile.setEnabled(true);
        context.clearFiles.setEnabled(true);
        context.importType.setEnabled(true);
        context.delItem.setEnabled(true);
        
        context.menuAddFromFolder.setEnabled(true);
        context.menuAddFile.setEnabled(true);
        context.menuApplyToSprite.setEnabled(true);
        context.menuEditFileProperties.setEnabled(true);
        context.menuGenerateSpriteSheet.setEnabled(true);
        context.menuPriview.setEnabled(true);
        context.menuRawFolder.setEnabled(true);
        context.menuSourceFolder.setEnabled(true);
        context.menuToggleImageType.setEnabled(true);
        context.menuToggleImportType.setEnabled(true);
        context.menuTogglePaddingType.setEnabled(true);
        context.menuClearAllFile.setEnabled(true);
        context.menuRemoveFile.setEnabled(true);
        context.menuShowImage.setEnabled(true);
        context.colorPadding.setEnabled(true);
        context.colorPadding1.setEnabled(true);
        context.colorMargin.setEnabled(true);
        context.colorSprite.setEnabled(true);
        context.colorSprite1.setEnabled(true);
    }

    public void setPaddingTop(String x) {
        context.getPaddingTop.setText(x);
    }

    public void setPaddingBottom(String x) {
        context.getPaddingBottom.setText(x);
    }

    public void setPaddingLeft(String x) {
        context.getPaddingLeft.setText(x);
    }

    public void setPaddingRight(String x) {
        context.getPaddingRight.setText(x);
    }

    public void resetInfo() {
        String x = "0";
        this.setSpriteHeight(x);
        this.setSpriteWidth(x);
        this.setSpritesheetHeight(x);
        this.setSpritesheetRow(x);
        this.setSpritesheetRow(x);
        this.setSpritesheetWidth(x);
        this.setTotalSprites(x);
        this.setPaddingBottom(x);
        this.setPaddingLeft(x);
        this.setPaddingRight(x);
        this.setPaddingTop(x);
        FinalSpriteSheet.reset();
        SourceSprite.reset();
        SpriteSheetView.reset();
        Singleton.innerFiles.clear();
        enableComponents();
    }

    public String getSelectedItem() {
        String x;
        try {
            x = View.setSourceFiles.getSelectedValue().toString();
        } catch (Exception e) {
            x = null;
        }
        return x;
    }

    public int getSelectedImageItem() {
        int x = context.getImageType.getSelectedIndex();
        return x;
    }

    public int getSelectedPaddingItem() {
        int x = context.paddingType.getSelectedIndex();
        return x;
    }

    public int getSpritesheetWidth() {
        int x = Extra.getInt(context.setSpritesheetWidth.getText());
        return x;
    }

    public int getSpritesheetHeight() {
        int x = Extra.getInt(context.setSpritesheetHeight.getText());
        return x;
    }

    public static void setSelectedIndex(int i) {
        View.setSourceFiles.setSelectedIndex(i);
    }
//public String getSpriteDemoToggle(){
//       String x =  context.spriteDemoToggle.getText();
//       return x;
//  }
//
//public void setSpriteDemoToggle(String s){
//      context.spriteDemoToggle.setText(s);
// }

    public String getSpriteImageToggle() {
        String x = context.spriteImageToggle.getText();
        return x;
    }

    public void setSpriteImageToggle(String s) {
        context.spriteImageToggle.setText(s);
    }

    public String getSpriteSheetImageToggle() {
        String x =  context.spriteSheetImageToggle.getText();
        return x;
    }

    public void setSpriteSheetImageToggle(String s) {
       context.spriteSheetImageToggle.setText(s);
    }

    public void setStatusLabel(String x) {
        context.statusLabel.setText(x);
    }

    public boolean getFinalSpriteSheetCheckBox() {
        return context.finalSpriteSheetCheckBox.isSelected();
    }

    public boolean getSpriteSheetCheckBox() {
        return context.spriteSheetCheckBox.isSelected();
    }

    public void setImageType(int i) {
        context.getImageType.setSelectedIndex(i);
    }

    public void setImportType(int i) {
        context.importType.setSelectedIndex(i);
    }

    public void setPaddingType(int i) {
        context.paddingType.setSelectedIndex(i);
    }
}
