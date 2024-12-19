/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.logTest;

import com.nextone.common.ErrorLog;
import com.nextone.common.Util;
import com.nextone.controller.api.ApiService;
import com.nextone.main.Core;
import java.io.File;

/**
 *
 * @author Admin
 */
public class BackupLogHandle implements Runnable {

    private File backupDir;
    private final ApiService apiService;

    public BackupLogHandle() {
        this.apiService = new ApiService();
    }

    public void setBackupDir(File backupDir) {
        this.backupDir = backupDir;
    }

    @Override
    public void run() {
        File[] files;
        while (true) {
            if (!Core.getInstance().getModeManagement().isOffLineMode()) {
                try {
                    if (backupDir != null) {
                        files = backupDir.listFiles();
                        if (files != null && files.length > 0) {
                            for (File file : files) {
                                if (file != null && upload(file)) {
                                    file.delete();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorLog.addError(this, e);
                }
            }
            Util.delay(10000);
        }
    }

    private boolean isBackupOk(File dir) {
        if (!dir.isDirectory()) {
            return true;
        }
        if (!dir.getName().matches("^.+\\-.+\\-.+$")) {
            Util.deleteFolder(dir);
            return true;
        } else {
            boolean rs = true;
            File[] dirs = dir.listFiles();
            for (File idDir : dirs) {
                if (idDir == null) {
                    continue;
                }
                if (idDir.isFile()) {
                    idDir.delete();
                } else if (!upload(idDir)) {
                    rs = false;
                }
            }
            return rs;
        }
    }

    private boolean upload(File idDir) {
        File[] files = idDir.listFiles();
        File jsonF = null;
        File imgF = null;
        for (File file : files) {
            if (file.isDirectory()) {
                Util.deleteFolder(file);
            } else if (file.getName().equalsIgnoreCase(FileTestService.IMAGEPNG)) {
                imgF = file;
            } else if (file.getName().equalsIgnoreCase(FileTestService.JSON_LOGJSON)) {
                jsonF = file;
            }
        }
        if (jsonF == null || this.apiService.sendData(jsonF, imgF) == ApiService.PASS) {
            Util.deleteFolder(idDir);
            return true;
        }
        return false;
    }

}
