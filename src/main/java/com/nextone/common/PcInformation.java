/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common;

import com.nextone.common.communication.Communicate.Impl.Cmd.Cmd;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class PcInformation {

    private static volatile PcInformation instance;
    private Map<String, List<String>> networkInfomation;

    private PcInformation() {
    }

    public static PcInformation getInstance() {
        PcInformation ins = PcInformation.instance;
        if (ins == null) {
            synchronized (PcInformation.class) {
                ins = PcInformation.instance;
                if (ins == null) {
                    PcInformation.instance = ins = new PcInformation();
                }
            }
        }
        return ins;
    }

    public boolean isChanged() {
        var nwi = scanHostIp();
        if (this.networkInfomation != null && nwi.equals(this.networkInfomation)) {
            return false;
        }
        this.networkInfomation = nwi;
        return true;
    }

    public String getPcName() {
        return getComputerName();
    }

    public List<String> getIps() {
        isChanged();
        List<String> rs = new ArrayList<>();
        for (List<String> ips : networkInfomation.values()) {
            rs.addAll(ips);
        }
        return rs;
    }

    public Collection<String> getMacs() {
        isChanged();
        return networkInfomation.keySet();
    }

    public Map<String, List<String>> getNetworkInfomation() {
        isChanged();
        return networkInfomation;
    }

    private Map<String, List<String>> scanHostIp() {
        Map<String, List<String>> networkInfo = new HashMap<>();
        try {
            for (Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces(); eni.hasMoreElements();) {
                final NetworkInterface ifc = eni.nextElement();
                if (ifc.isUp()) {
                    byte[] macBytes = ifc.getHardwareAddress();
                    if (macBytes != null) {
                        StringBuilder macAddress = new StringBuilder();
                        for (byte b : macBytes) {
                            macAddress.append(String.format("%02X", b));
                            macAddress.append(":");
                        }
                        if (macAddress.length() > 0) {
                            macAddress.setLength(macAddress.length() - 1);
                        }
                        List<String> ips = new ArrayList<>();
                        networkInfo.put(macAddress.toString(), ips);
                        for (Enumeration<InetAddress> ena = ifc.getInetAddresses(); ena.hasMoreElements();) {
                            InetAddress address = ena.nextElement();
                            if (address instanceof Inet4Address ipv4) {
                                ips.add(ipv4.getHostAddress());
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return networkInfo;
    }

    public String getComputerName() {
        String os = getSystemName();
        if (os.startsWith("Windows")) {
            return getWindowsHostName();
        } else if (os.startsWith("Mac")) {
            return getMacHostName();
        }
        return "";
    }

    private String getWindowsHostName() {
        try {
            if (InetAddress.getLocalHost() == null) {
                return "";
            }
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isWindowsOs() {
        String os = getSystemName();
        return os != null && os.trim().startsWith("Windows");
    }

    private String getMacHostName() {
        Cmd cmd1 = new Cmd(isWindowsOs());
        cmd1.sendCommand("networksetup -getcomputername");
        return cmd1.readAll();
    }

    public String getSystemName() {
        return System.getProperty("os.name");
    }

}
