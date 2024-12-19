/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.mylogger;

import com.nextone.common.timer.TimeBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimeZone;

/**
 *
 * @author Administrator
 */
public class MyLogger {

    private File file;
    private File folder;
    private TimeBase timeBase;
    private StringBuilder log;
    private boolean saveMemory;
    private boolean dailyLog = false;
    private String oldDay = "";

    public MyLogger() {
        this(TimeBase.UTC7);
        this.folder = new File("log");
    }

    public void setFolder(File folder) {
        if (folder == null) {
            return;
        }
        this.folder = folder;
    }

    public File getFolder() {
        return folder;
    }

    public File getFile() {
        String nowDay = this.timeBase.getDateTime(TimeBase.YYYY_MM_DD);
        if (file == null || !file.exists() || (dailyLog && !oldDay.equalsIgnoreCase(nowDay))) {
            setFile(new File(folder, nowDay.concat(".txt")));
            oldDay = nowDay;
        }
        return file;
    }

    private void setFile(File file) {
        if (file == null) {
            return;
        }
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        this.file = file;
    }

    public MyLogger(TimeZone timeZone) {
        this.timeBase = new TimeBase(timeZone);
        this.log = new StringBuilder();
        this.saveMemory = false;
    }

    public void setSaveMemory(boolean saveMemory) {
        this.saveMemory = saveMemory;
        if (saveMemory) {
            this.log.delete(0, this.log.length());
        }
    }

    public void setDailyLog(boolean dailyLog) {
        this.dailyLog = dailyLog;
    }

    public void setFile(String fileStr) {
        setFile(new File(fileStr));
    }

    public synchronized void addLog(Object txt) throws IOException {
        if (txt == null) {
            add(String.format("%s:  null\r\n",
                    this.timeBase.getDateTime(TimeBase.DATE_TIME_MS)));
            return;
        }
        for (String line : txt.toString().split("\n")) {
            add(String.format("%s:   %s\r\n",
                    this.timeBase.getDateTime(TimeBase.DATE_TIME_MS), line.trim()));
        }
    }

    public synchronized void addLog(String str, Object... params) throws Exception {
        String strLog = String.format(str, params);
        addLog(strLog);
    }

    public synchronized void addLog(String key, Object str) {
        if (str == null) {
            add(String.format("%s:  [%s] null\r\n",
                    this.timeBase.getDateTime(TimeBase.DATE_TIME_MS), key));
            return;
        }
        for (String line : str.toString().split("\n")) {
            add(String.format("%s:   [%s] %s\r\n",
                    this.timeBase.getDateTime(TimeBase.DATE_TIME_MS), key, line.trim()));
        }
    }

    public synchronized void addLog(String key, String str, Object... params) {
        String strLog = String.format(str, params);
        addLog(key, strLog);
    }

    public synchronized void add(String log) {
        if (log == null) {
            return;
        }
        if (!saveMemory) {
            this.log.append(log);
        }
        File f = getFile();
        if (f != null) {
            try (FileWriter writer = new FileWriter(f, true)) {
                writer.write(log);
                writer.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public synchronized void setLog(String log) throws IOException {
        if (log == null) {
            return;
        }
        if (!saveMemory) {
            this.log.delete(0, this.log.length());
            this.log.append(log);
        }
        File f = getFile();
        if (f != null) {
            try (FileWriter writer = new FileWriter(f)) {
                writer.write(log);
                writer.flush();
            }
        }
    }

    public void clear() {
        try {
            setLog("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getLog() throws IOException {
        if (saveMemory) {
            if (this.file == null) {
                return null;
            }
            StringBuilder builder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\r\n");
                }
                return builder.toString();
            }
        } else {
            return log.toString();
        }
    }
}
