/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output.printer;

import com.alibaba.fastjson2.JSONObject;
import com.qt.common.ErrorLog;
import com.qt.common.MyObjectMapper;
import com.qt.controller.logTest.FileTestService;
import com.qt.controller.ProcessModelHandle;
import com.qt.model.modelTest.process.ProcessModel;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
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

//    private PrintService printService;
    private final FileTestService fileTestService;
    private final ProcessModel processModel;

    public Printer() {
        this.fileTestService = FileTestService.getInstance();
        this.processModel = ProcessModelHandle.getInstance().getProcessModel();
    }

//    public boolean connectToDefault() {
//        this.printService = PrintServiceLookup.lookupDefaultPrintService();
//        return this.printService != null;
//    }
//
//    public boolean connect(String name) {
//        this.printService = findThermalPrinter(name);
//        return this.printService != null;
//    }

    public boolean print(Printable printable) {
        if (printable == null) {
            return false;
        }
        try {
            PrinterJob pj = PrinterJob.getPrinterJob();
            if (pj.getPrintService() == null) {
                return false;
            }
            pj.setPrintable(printable, getPageFormat(pj));
            pj.print();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorLog.addError(this, ex);
            return false;
        }
    }

//    private PrintService findThermalPrinter(String name) {
//        if (name == null || name.isBlank()) {
//            return null;
//        }
//        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
//        for (PrintService service : printServices) {
//            if (service.getName().toLowerCase().contains(name)) {
//                return service;
//            }
//        }
//        return null;
//    }

    public boolean printTestResult() {
        try {
            String data = this.fileTestService.getDataOf(this.processModel.getId());
            if (data == null || data.isBlank() || data.charAt(0) != '{') {
                return false;
            }
            JSONObject json = JSONObject.parseObject(data);
            ProcessModel model = MyObjectMapper.convertValue(json, ProcessModel.class);
//            StringBuilder builder = new StringBuilder("\tKết quả\r\n");
//            builder.append("sbd: ").append(model.getId()).append("\r\n");
//            builder.append("số xe: ").append(model.getCarId()).append("\r\n");
//            builder.append("họ tên: ").append(model.getName()).append("\r\n");
//            builder.append("ngày sinh: ").append(model.getDateOfBirth()).append("\r\n");
//            builder.append("số điểm: ").append(model.getScore()).append("\r\n");
//            return print(new FileInputStream("D:\\Java-project\\DAT\\log\\test\\2024-07-22\\0\\jsonLog.json"),
//                    DocFlavor.CHAR_ARRAY.TEXT_PLAIN);
            return print(new BillPrintable(model));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }
    Double bHeight = 0.0;

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }
}
