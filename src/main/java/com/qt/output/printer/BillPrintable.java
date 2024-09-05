/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output.printer;

import com.qt.common.ErrorLog;
import com.qt.controller.ErrorcodeHandle;
import com.qt.model.modelTest.ErrorcodeWithContestNameModel;
import com.qt.model.modelTest.process.ProcessModel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BillPrintable implements Printable {

    private final ProcessModel processData;
    private final List<ErrorcodeWithContestNameModel> errorcodes;

    public BillPrintable(ProcessModel processData) {
        this.processData = processData;
        this.errorcodes = ErrorcodeHandle.getInstance().getEwcnms();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (this.processData == null || pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
//        ImageIcon icon = new ImageIcon(getClass().getResource("/bill_icon.png"));
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        try {
            int y = 40;
            int yShift = 10;
            int headerRectHeight = 15;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
//            g2d.drawImage(icon.getImage(), 40, 0, 40, 40, null);
            drawString(g2d, y += headerRectHeight, "******************************");
            drawString(g2d, y += yShift, "  TRUNG TAM SHLX QUAN KHU 1");
            drawString(g2d, y += yShift, "  %s", this.processData.getModeName());
            drawString(g2d, y += headerRectHeight, "------------------------------");
            drawString(g2d, y += yShift, "SBD: %s", this.processData.getId());
            drawString(g2d, y += yShift, "SO XE: %s", this.processData.getCarId());
            drawString(g2d, y += yShift, "HO TEN: %s", this.processData.getName());
            drawString(g2d, y += yShift, "NGAY THI: %s", this.processData.getStartTime());
            drawString(g2d, y += yShift, "SO DIEM: %s/100", this.processData.getScore());
            drawString(g2d, y += yShift, "KET QUA: %s",
                    this.processData.getContestsResult() == ProcessModel.PASS ? "DAT" : "KHONG DAT");
            if (!this.errorcodes.isEmpty()) {
                drawString(g2d, y += headerRectHeight, "CAC LOI:");
                for (ErrorcodeWithContestNameModel errorcode : errorcodes) {
                    drawString(g2d, y += yShift, "%s, -%s", errorcode.getDescription(), errorcode.getScore());
                }
            }
            drawString(g2d, y += headerRectHeight, "------------------------------");
            drawString(g2d, y += yShift, "THI SINH KI TEN:   ");
            drawString(g2d, y += yShift, "******************************");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
        return PAGE_EXISTS;
    }

    private void drawString(Graphics2D g2d, int y, String format, Object... params) {
        g2d.drawString(String.format(format, params), 0, y);
    }

}
