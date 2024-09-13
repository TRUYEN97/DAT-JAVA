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
        try {
            destroy();
            String newCommand = params.length == 0 ? command : String.format(command, params);
            if (isWindowsOs) {
                this.builder.command("cmd.exe", "/c", newCommand);
            } else {
                this.builder.command("sh", "-c", newCommand);
            }
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
    private static final String PING_LINUX = "ping -c 2 %s";
    private static final String PING_WINDOWS = "ping %s -n 1";

    public boolean ping(String addr, int cycle) {
        if (addr == null || addr.isBlank()) {
            return false;
        }
        String command;
        if (isWindowsOs()) {
            command = String.format(PING_WINDOWS, addr);
        } else {
            command = String.format(PING_LINUX, addr);
        }
        for (int i = 0; i < cycle; i++) {
            if (sendCommand(command)) {
                String response = readAll().trim();
                if (response.contains("TTL=") || response.contains("ttl=")) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }
}
