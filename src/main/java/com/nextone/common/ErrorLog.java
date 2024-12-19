/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import com.nextone.common.mylogger.MyLogger;
import com.nextone.common.timer.TimeBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Admin
 */
public class ErrorLog {

    private static final MyLogger logger = new MyLogger();

    static {
        ErrorLog.logger.setFolder(new File(Setting.getInstance().getLogDir(), "error"));
        ErrorLog.logger.setDailyLog(true);
        ErrorLog.logger.setSaveMemory(true);
    }

    public static void addError(String error) {
        try {
            ErrorLog.logger.addLog(error + "\r\n//////////////////////////////////////");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addError(Object object, Exception e) {
        File f = ErrorLog.logger.getFile();
        if (e == null || f == null) {
            return;
        }
        try {
            ErrorLog.logger.add(String.format("%s:   [%s] ",
                    new TimeBase().getDateTime(TimeBase.DATE_TIME_MS), object));
            e.printStackTrace(new PrintStream(new FileOutputStream(f, true)));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void addError(Object object, String error) {
        String mess = String.format("error in %s : %s",
                object.getClass().getName(), error);
        addError(mess);
    }
}
