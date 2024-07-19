/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.controller.FileTestService;
import com.qt.controller.ProcessModelHandle;
import com.qt.model.modelTest.process.ProcessModel;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

/**
 *
 * @author Admin
 */
public class Printer {

    private PrintService printService;
    private final PrintRequestAttributeSet attrs;
    private final FileTestService fileTestService;
    private final ProcessModel processModel;

    public Printer() {
        this.attrs = new HashPrintRequestAttributeSet();
        this.fileTestService = FileTestService.getInstance();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
    }

    public boolean connectToDefault() {
        this.printService = PrintServiceLookup.lookupDefaultPrintService();
        return this.printService != null;
    }

    public boolean connect(String name) {
        this.printService = findThermalPrinter(name);;
        return this.printService != null;
    }

    public boolean print(byte[] bytes) {
        if (this.printService == null) {
            return false;
        }
        try {
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(bytes, flavor, null);
            attrs.add(new Copies(1));
            DocPrintJob job = printService.createPrintJob();
            job.print(doc, attrs);
            return true;
        } catch (PrintException ex) {
            ex.printStackTrace();
            ErrorLog.addError(this, ex);
            return false;
        }
    }

    private PrintService findThermalPrinter(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : printServices) {
            if (service.getName().toLowerCase().contains(name)) {
                return service;
            }
        }
        return null;
    }

    public boolean printTestResult() {
        try {
            String data = this.fileTestService.getDataOf(this.processModel.getId());
            if (data == null || data.charAt(0) != '{') {
                return false;
            }
            JSONObject json = JSONObject.parseObject(data);
            ProcessModel model = MyObjectMapper.convertValue(json, ProcessModel.class);
            StringBuilder builder = new StringBuilder("\t\tKết quả\r\n");
            builder.append("Sbd: ").append(model.getId()).append("\r\n");
            builder.append("số xe: ").append(model.getId()).append("\r\n");
            builder.append("họ tên: ").append(model.getName()).append("\r\n");
            builder.append("ngày sinh: ").append(model.getDate_of_birth()).append("\r\n");
            builder.append("số điểm: ").append(model.getDate_of_birth()).append("\r\n");
            return print(builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }
}
