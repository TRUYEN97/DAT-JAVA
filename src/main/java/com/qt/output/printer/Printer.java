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

    public boolean printTestResult() {
        return printTestResult(this.processModel.getId());
    }

    public boolean printTestResult(String id) {
        try {
            String data = this.fileTestService.getDataOf(id);
            if (data == null || data.isBlank() || data.charAt(0) != '{') {
                return false;
            }
            JSONObject json = JSONObject.parseObject(data);
            ProcessModel model = MyObjectMapper.convertValue(json, ProcessModel.class);
            return print(new BillPrintable(model));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return false;
        }
    }

    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight = 15.5;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(5.8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 0, width, height);
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        pf = pj.validatePage(pf);
        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }
}
