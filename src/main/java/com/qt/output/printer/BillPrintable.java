/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output.printer;

import com.qt.common.ErrorLog;
import com.qt.model.modelTest.process.ProcessModel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class BillPrintable implements Printable {

    private final ProcessModel processData;

    public BillPrintable(ProcessModel processData) {
        this.processData = processData;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (this.processData == null || pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        ImageIcon icon = new ImageIcon(getClass().getResource("/bill_icon.png"));
        Graphics2D g2d = (Graphics2D) graphics;
        double width = pageFormat.getImageableWidth();
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        try {
            int y = 40;
            int yShift = 10;
            int headerRectHeight = 15;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
            g2d.drawImage(icon.getImage(), 40, 0, 40, 40, null);
            drawString(g2d, y += yShift, "------------------------------");
            drawString(g2d, y += yShift, "     Sát hạch lãi xe ô tô     ");
            drawString(g2d, y += yShift, "  %s", this.processData.getModeName());
            drawString(g2d, y += headerRectHeight, "------------------------------");
            drawString(g2d, y += yShift, "     SBD: %s", this.processData.getId());
            drawString(g2d, y += yShift, "   Số Xe: %s", this.processData.getCarId());
            drawString(g2d, y += yShift, "  Họ Tên: %s", this.processData.getName());
            drawString(g2d, y += yShift, "Năm Sinh: %s", this.processData.getDateOfBirth());
            drawString(g2d, y += yShift, " Số điểm: %s", this.processData.getScore());
            drawString(g2d, y += yShift, " Bắt đầu: %s", this.processData.getStartTime());
            drawString(g2d, y += yShift, "Kết thúc: %s", this.processData.getEndTime());
            drawString(g2d, y += headerRectHeight, "******************************");
            drawString(g2d, y += yShift, "   Điện tử Thái Nguyên TNE.   ");
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
