/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.common;

import com.qt.common.mylogger.MyLogger;
import com.qt.common.timer.TimeBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class ErrorLog {

    private static final MyLogger logger = new MyLogger();
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(ErrorLog.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorLog.addError("ApiService-constructor", ex);
        }
        String dir = properties.getProperty(ConstKey.DIR_LOG, "log");
        ErrorLog.logger.setFile(new File(dir, "error"));
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