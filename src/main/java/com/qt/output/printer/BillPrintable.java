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
import java.awt.print.Printable;
import java.awt.print.PrinterException;
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
        int result = NO_SUCH_PAGE;
        if (this.processData == null) {
            return result;
        }
        ImageIcon icon = new ImageIcon(getClass().getResource("/bill_icon.png"));
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;
            double width = pageFormat.getImageableWidth();
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

            //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
            try {
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                g2d.drawImage(icon.getImage(), 50, 20, 90, 30, null);
                y += yShift + 30;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("         info1        ", 12, y);
                y += yShift;
                g2d.drawString("         info2        ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += headerRectHeight;
                g2d.drawString(" Item Name                           ", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 10, y);
                y += headerRectHeight;
                g2d.drawString("-------------------------------------", 10, y);
                y += yShift;
                g2d.drawString("*************************************", 10, y);

            } catch (Exception e) {
                e.printStackTrace();
                ErrorLog.addError(this, e);
            }
            result = PAGE_EXISTS;
        }
        return result;
    }

}
