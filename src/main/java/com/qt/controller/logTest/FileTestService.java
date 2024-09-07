/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.controller.logTest;

import com.qt.common.ConstKey;
import com.qt.common.ErrorLog;
import com.qt.common.FileService.FileService;
import com.qt.model.modelTest.process.ProcessModel;
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

    public static final String IMAGEPNG = "image.png";
    public static final String JSON_LOGJSON = "jsonLog.json";
    private static volatile FileTestService instance;
    private final FileService fileService;
    private final BackupLogHandle backupLogHandle;
    private final File backupDir;
    private final File logDir;
    private ProcessModel processModel;

    private FileTestService() {
        this.fileService = new FileService();
        Properties properties = new Properties();
        this.backupLogHandle = new BackupLogHandle();
        try {
            properties.load(ErrorLog.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
        this.backupDir = new File(properties.getProperty(ConstKey.DIR_BACKUP_LOG, "logBackup"));
        this.logDir = new File(properties.getProperty(ConstKey.DIR_LOG, "log"));
        this.backupLogHandle.setBackupDir(this.backupDir);
        new Thread(backupLogHandle).start();
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

    public void setProcessModel(ProcessModel processModel) {
        this.processModel = processModel;
    }
    
    public boolean saveImg(String id, BufferedImage image) {
        try {
            return saveImg(image, createLogPathString(id, IMAGEPNG));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean saveLogJson(String id, String jsonString) {
        try {
            String filePathString = createLogPathString(id, JSON_LOGJSON);
            return this.fileService.writeFile(filePathString, jsonString,
                    false);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }
    
    public void saveBackupLog(String id, String jsonString, BufferedImage image) {
        try {
            this.fileService.writeFile(createBackupLogPathString(id, JSON_LOGJSON),
                    jsonString,
                    false);
            saveImg(image, createBackupLogPathString(id, IMAGEPNG));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

    public File getFileJsonPath(String id) {
        String pathString = createLogPathString(id, JSON_LOGJSON);
        if (pathString == null || pathString.isBlank()) {
            return null;
        }
        File file = new File(pathString);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    public File getFileImagePath(String id) {
        String pathString = createLogPathString(id, IMAGEPNG);
        if (pathString == null || pathString.isBlank()) {
            return null;
        }
        File file = new File(pathString);
        if (!file.exists()) {
            return null;
        }
        return file;
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

    private String createLogPathString(String id, String fileName) {
        return createPathString(logDir, id, fileName);
    }
    
    private String createBackupLogPathString(String id, String fileName) {
        String filePathString = String.format("%s/%s/%s",
                backupDir,
                id,
                fileName);
        return filePathString;
    }

    public String createPathString(File root, String id, String fileName) {
        String filePathString = String.format("%s/logTest/%s/%s/%s",
                root,
                this.processModel.getExamId(),
                id,
                fileName);
        return filePathString;
    }

    private boolean saveImg(BufferedImage image, String path) throws IOException {
        if (image == null) {
            return false;
        }
        File file = new File(path);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return ImageIO.write(image, "PNG", file);
    }

    public String getDataOf(String id) {
        return this.fileService.readFile(getFileJsonPath(id));
    }
}
