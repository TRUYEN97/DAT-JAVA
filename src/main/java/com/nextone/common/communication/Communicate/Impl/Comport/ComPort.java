/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nextone.common.communication.Communicate.Impl.Comport;

import com.nextone.common.communication.Communicate.IConnect;
import com.fazecast.jSerialComm.SerialPort;
import com.nextone.common.communication.Communicate.AbsCommunicate;
import com.nextone.common.communication.Communicate.IReadStream;
import com.nextone.common.communication.Communicate.ISender;
import com.nextone.common.communication.Communicate.ReadStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

public class ComPort extends AbsCommunicate implements ISender, IReadStream, IConnect {

    private SerialPort serialPort;
    private String name;

    public ComPort() {
        input = new ReadStream();
    }

    @Override
    public synchronized boolean connect(String port, int baudrate) {
        if (port == null) {
            return false;
        }
        for (String commPort : getCommPorts()) {
            if (commPort.equalsIgnoreCase(port)) {
                return openComm(port, baudrate);
            }
        }
        return false;
    }
    
    private boolean containPort(String port){
        if (port == null) {
            return false;
        }
        for (String commPort : getCommPorts()) {
            if (commPort.equalsIgnoreCase(port)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isConnect() {
        return serialPort != null && serialPort.isOpen();
    }

    private boolean openComm(String port, int baudrate) {
        try {
            close();
            this.serialPort = SerialPort.getCommPort(port);
            if (!serialPort.openPort() || !serialPort.isOpen() || !serialPort.setComPortParameters(
                    baudrate,
                    8, // data bits
                    SerialPort.ONE_STOP_BIT,
                    SerialPort.NO_PARITY)) {
                return false;
            }
            input.setReader(serialPort.getInputStream());
            out = new PrintStream(serialPort.getOutputStream());
            this.name = port;
            return true;
        } catch (IOException e) {
            showException(e);
            return false;
        }
    }

    public Set<String> getCommPorts() {
        Set<String> returnValue = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports != null && ports.length > 0) {
            for (SerialPort port : ports) {
                returnValue.add(port.getSystemPortName());
            }
        }
        return returnValue;
    }

    private void closePort() {
        if (isConnect()) {
            serialPort.closePort();
        }
    }

    private void closeOutput() throws IOException {
        if (out != null) {
            out.close();
        }
    }

    @Override
    protected void closeThis() throws IOException {
        closePort();
        closeOutput();
    }

}
