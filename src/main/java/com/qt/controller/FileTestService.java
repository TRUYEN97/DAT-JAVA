/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.FileService.FileService;
import com.qt.common.mylogger.MyLogger;
import com.qt.common.timer.TimeBase;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class FileTestService {

    private static final String IMAGEPNG = "image.png";
    private static final String JSON_LOGJSON = "jsonLog.json";
    private static volatile FileTestService instance;
    private final FileService fileService;
    private final TimeBase timeBase;
    private final Properties properties;
    private String date;

    private FileTestService() {
        this.fileService = new FileService();
        this.timeBase = new TimeBase();
        this.date = this.timeBase.getDate();
        this.properties = new Properties();
        try {
            properties.load(ErrorLog.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
    }

    public static FileTestService getInstance() {
        FileTestService ins = instance;
        if (ins == null) {
            synchronized (FileTestService.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new FileTestService();
                }
            }
        }
        return ins;
    }

    public void updateDate() {
        this.date = this.timeBase.getDate();
    }

    public boolean saveImg(String id, BufferedImage image) {
        try {
            if (image == null) {
                return false;
            }
            File file = new File(createStringPath(id, IMAGEPNG));
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return ImageIO.write(image, "PNG", file);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean saveLogJson(String id, String jsonString) {
        try {
            String filePathString = createStringPath(id, JSON_LOGJSON);
            return this.fileService.writeFile(filePathString, jsonString,
                    false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public String getCarId() {
        String carid = this.fileService.readFile(new File("log/carId.txt"));
        return carid == null ? "0" : carid.trim();
    }

    public void writeCarId(String carId) {
        if (carId == null || carId.isBlank()) {
            return;
        }
        this.fileService.writeFile("log/carId.txt", carId, false);
    }

    public File getFileJsonPath(String id) {
        File file = new File(createStringPath(id, JSON_LOGJSON));
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    public File getFileImagePath(String id) {
        File file = new File(createStringPath(id, IMAGEPNG));
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    private String createStringPath(String id, String fileName) {
        String filePathString = String.format("%s/logTest/%s/%s/%s",
                this.properties.getOrDefault(ConstKey.DIR_LOG, "log"),
                this.date, id, fileName);
        return filePathString;
    }

    public String getDataOf(String id) {
        return this.fileService.readFile(getFileJsonPath(id));
    }
}
