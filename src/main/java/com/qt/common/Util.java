/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Administrator
 */
public class Util {
    
    
    public static long getTestTime(long startMs, long endtMs) {
        if (startMs <= 0 ) {
            return 0;
        }
        if (endtMs >= startMs) {
            return (endtMs - startMs);
        }
        return (System.currentTimeMillis() - startMs);
    }
    
    public static int getGearBoxVal(boolean s1, boolean s2, boolean s3, boolean s4) {
        if (s3) {
            if (s1) {
                return 3;
            }
            if (s2) {
                return 4;
            }
            if (s4) {
                return 5;
            }
        }
        if (s1) {
            return 1;
        }
        if (s2) {
            return 2;
        }
        return 0;
    }

    public static void copyFile(Path source, Path target, CopyOption... options) throws IOException {
        if (target.getParent() != null) {
            target.getParent().toFile().mkdirs();
        }
        Files.copy(source, target, options);
    }

    public static void deleteFolder(File root) {
        if(!root.exists()){
           return;
        }
        File[] files = root.listFiles();
        if (files != null && files.length > 0) {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                }
                file.delete();
            }
        }
        root.delete();
    }

    public static String md5File(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return DigestUtils.md5Hex(bytes);
    }

    public static String md5File(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String md5File(String filePath) {
        try ( FileInputStream input = new FileInputStream(filePath)) {
            return md5File(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        return resizeImg(img, width, height);
    }

    private static ImageIcon resizeImg(Image img, int width, int height) {
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
        }
    }
}
