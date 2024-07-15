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

/**
 *
 * @author Admin
 */
public class ErrorLog {

    private static final MyLogger loger = new MyLogger();

    static {
        String filePath = String.format("Log\\ErrorLog\\%s.txt",
                new TimeBase().getDate());
        ErrorLog.loger.setFile(new File(filePath));
        ErrorLog.loger.setSaveMemory(true);
    }

    public static void addError(String error) {
        try {
            ErrorLog.loger.addLog(error + "\r\n//////////////////////////////////////");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addError(Object object, Exception e) {
        File f = ErrorLog.loger.getFile();
        if (e == null || f == null) {
            return ;
        }
        try {
            ErrorLog.loger.add(String.format("%s:   [%s] ",
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
