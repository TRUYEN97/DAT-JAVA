/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qt.common.communication.Communicate.Impl.Cmd;

import com.qt.common.PcInformation;
import com.qt.common.communication.Communicate.AbsCommunicate;
import com.qt.common.communication.Communicate.AbsStreamReadable;
import com.qt.common.communication.Communicate.IReadStream;
import com.qt.common.communication.Communicate.ISender;
import com.qt.common.communication.Communicate.ReadStreamOverTime;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Administrator
 */
public class Cmd extends AbsCommunicate implements ISender, IReadStream {

    private Process process;
    private final ProcessBuilder builder;
    private final boolean isWindowsOs;

    public Cmd() {
        this(new ReadStreamOverTime(), PcInformation.getInstance().isWindowsOs());
    }

    public Cmd(boolean isWindowsOs) {
        this(new ReadStreamOverTime(), isWindowsOs);
    }

    public boolean isWindowsOs() {
        return isWindowsOs;
    }

    public Cmd(AbsStreamReadable reader) {
        this(reader, true);
    }

    public Cmd(AbsStreamReadable reader, boolean isWindowsOs) {
        if (reader == null) {
            throw new NullPointerException("StreamReader == null");
        }
        this.isWindowsOs = isWindowsOs;
        this.input = reader;
        this.builder = new ProcessBuilder();
        this.builder.redirectErrorStream(true);
    }

    @Override
    public boolean insertCommand(String command, Object... params) {
        destroy();
        String newCommand = params.length == 0 ? command : String.format(command, params);
        if (isWindowsOs) {
            this.builder.command("cmd.exe", "/c", newCommand);
        } else {
            this.builder.command(newCommand);
        }
        try {
            this.process = builder.start();
            this.input.setReader(process.getInputStream());
            this.out = new PrintStream(process.getOutputStream());
            return true;
        } catch (IOException ex) {
            showException(ex);
            return false;
        }
    }

    public int waitFor() {
        try {
            return this.process.waitFor();
        } catch (InterruptedException ex) {
            return -1;
        }
    }

    public void destroy() {
        try {
            close();
        } catch (IOException e) {
            showException(e);
        }
    }

    @Override
    protected void closeThis() throws IOException {
        if (process != null) {
            process.destroy();
        }
    }

}
