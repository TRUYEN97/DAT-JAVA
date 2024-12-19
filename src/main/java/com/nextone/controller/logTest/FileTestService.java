/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.logTest;

import com.nextone.common.CarConfig;
import com.nextone.common.ErrorLog;
import com.nextone.common.FileService.FileService;
import com.nextone.common.Setting;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private final CarConfig carConfig;

    private FileTestService() {
        this.fileService = new FileService();
        this.backupDir = new File(Setting.getInstance().getBackUpLogDir());
        this.logDir = new File(Setting.getInstance().getLogDir());
        this.backupLogHandle = new BackupLogHandle();
        this.backupLogHandle.setBackupDir(this.backupDir);
        this.carConfig = CarConfig.getInstance();
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

    public boolean saveImg(String id, BufferedImage image) {
        try {
            return saveImg(image, createLogPathString(this.carConfig.getExamId(), id, IMAGEPNG));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public boolean saveLogJson(String id, String jsonString) {
        try {
            String filePathString = createLogPathString(this.carConfig.getExamId(), id, JSON_LOGJSON);
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

    public File getFileJsonPath(String examID, String id) {
        String pathString = createLogPathString(examID, id, JSON_LOGJSON);
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
        String pathString = createLogPathString(this.carConfig.getExamId(), id, IMAGEPNG);
        if (pathString == null || pathString.isBlank()) {
            return null;
        }
        File file = new File(pathString);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    private String createLogPathString(String examId, String id, String fileName) {
        return createPathString(logDir, examId, id, fileName);
    }

    private String createBackupLogPathString(String id, String fileName) {
        String filePathString = String.format("%s/%s/%s",
                backupDir,
                id,
                fileName);
        return filePathString;
    }

    public String createPathString(File root, String examId, String id, String fileName) {
        String filePathString = String.format("%s/logTest/ExamId-%s/%s/%s",
                root,
                examId,
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
        return getDataOf(this.carConfig.getExamId(), id);
    }

    public String getDataOf(String examId, String id) {
        return this.fileService.readFile(getFileJsonPath(examId, id));
    }
}
