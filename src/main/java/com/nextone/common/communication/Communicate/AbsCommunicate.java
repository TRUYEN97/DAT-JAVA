/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common.communication.Communicate;

import com.nextone.common.timer.WaitTime.AbsTime;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Administrator
 */
public abstract class AbsCommunicate extends AbsShowException implements ISender, IReadStream, Closeable {

    protected AbsStreamReadable input;
    protected PrintStream out;

    protected AbsCommunicate() {
    }

    @Override
    public void stopRead() {
        if (input != null) {
            input.stopRead();
        }
    }

    @Override
    public boolean setStreamReadable(AbsStreamReadable readable) {
        try {
            close();
            this.input = readable;
            return true;
        } catch (IOException ex) {
            showException(ex);
            return false;
        }
    }

    @Override
    public void setDebug(boolean debug) {
        super.setDebug(debug);
        if (input != null) {
            this.input.setDebug(debug);
        }
    }

    @Override
    public synchronized boolean sendCommand(String command, Object... params) {
        if (input != null) {
            input.clearResult();
        }
        return insertCommand(command, params);
    }

    @Override
    public synchronized boolean sendCtrl_C() {
        try {
            char ctrlC = 99 & 0X1F;
            out.print(ctrlC);
            out.flush();
            return true;
        } catch (Exception ex) {
            showException(ex);
            return false;
        }
    }

    @Override
    public synchronized boolean insertCommand(String command, Object... params) {
        if (out == null) {
            return false;
        }
        try {
            if (params.length == 0) {
                out.println(command);
            } else {
                out.println(String.format(command, params));
            }
            out.flush();
            return true;
        } catch (Exception ex) {
            showException(ex);
            return false;
        }
    }

    @Override
    public String readLine() {
        if (input == null) {
            return null;
        }
        return input.readLine();
    }

    @Override
    public String readAll() {
        if (input == null) {
            return null;
        }
        return input.readAll();
    }

    @Override
    public String readAll(AbsTime tiker) {
        if (input == null) {
            return null;
        }
        return input.readAll(tiker);
    }

    @Override
    public String readUntil(String... regex) {
        if (input == null) {
            return null;
        }
        return input.readUntil(regex);
    }

    @Override
    public String readUntil(AbsTime tiker, String... regex) {
        if (input == null) {
            return null;
        }
        return input.readUntil(tiker, regex);
    }

    @Override
    public String readLine(AbsTime tiker) {
        if (input == null) {
            return null;
        }
        return input.readLine(tiker);
    }

    @Override
    public StringBuffer getStringResult() {
        if (input == null) {
            return null;
        }
        return this.input.getStringResult();
    }

    protected abstract void closeThis() throws IOException;

    @Override
    public void close() throws IOException {
        closeThis();
        if (input != null) {
            input.close();
        }
        if (out != null) {
            out.close();
        }
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
