/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output.printer;

import com.qt.common.ErrorLog;
import com.qt.model.modelTest.ErrorCode;
import com.qt.model.modelTest.ErrorcodeWithContestNameModel;
import com.qt.model.modelTest.contest.ContestDataModel;
import com.qt.model.modelTest.process.ProcessModel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

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
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        try {
            int y = 10;
            int yShift = 10;
            int headerRectHeight = 15;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
//            g2d.drawImage(icon.getImage(), 40, 0, 40, 40, null);
            drawString(g2d, y += headerRectHeight, "Sat hach vien: ");
            drawString(g2d, y += yShift * 3, "******************************");
            drawString(g2d, y += yShift, "  TRUNG TAM SHLX QUAN KHU 1");
            drawString(g2d, y += headerRectHeight, "------------------------------");
            drawString(g2d, y += yShift, "SBD: %s", this.processData.getId());
            drawString(g2d, y += yShift, "SO XE: %s", this.processData.getCarId());
            drawString(g2d, y += yShift, "HO TEN: %s", this.processData.getName());
            drawString(g2d, y += yShift, "NGAY THI: %s", this.processData.getStartTime());
            List<ErrorcodeWithContestNameModel> errorcodes = getErrorCode();
            if (!errorcodes.isEmpty()) {
                drawString(g2d, y += headerRectHeight, "CAC LOI:");
                for (ErrorcodeWithContestNameModel errorcode : errorcodes) {
                    drawString(g2d, y += yShift, "%s, -%s, %s",
                            errorcode.getDescription(),
                            errorcode.getScore(),
                            errorcode.getContestName());
                }
            }
            drawString(g2d, y += headerRectHeight, "------------------------------");
            drawString(g2d, y += yShift, "SO DIEM: %s/100", this.processData.getScore());
            drawString(g2d, y += yShift, "KET QUA: %s", getContestResult());
            drawString(g2d, y += yShift, "THI SINH KI TEN:   ");
            drawString(g2d, y += yShift * 2, "******************************");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
        return PAGE_EXISTS;
    }

    private Object getContestResult() {
        int rs = this.processData.getContestsResult();
        switch (rs) {
            case ProcessModel.PASS -> {
                return "DAT";
            }
            case ProcessModel.FAIL -> {
                return "KHONG DAT";
            }
            case ProcessModel.RUNNING -> {
                return "DANG THI";
            }
        }
        return rs;
    }

    protected List<ErrorcodeWithContestNameModel> getErrorCode() {
        //        ImageIcon icon = new ImageIcon(getClass().getResource("/bill_icon.png"));
        List<ErrorcodeWithContestNameModel> errorcodes = new ArrayList<>();
        if (!this.processData.getErrorCodes().isEmpty()) {
            for (ErrorCode errorCode : this.processData.getErrorCodes()) {
                errorcodes.add(new ErrorcodeWithContestNameModel(errorCode, ""));
            }
        }
        if (!this.processData.getContests().isEmpty()) {
            for (ContestDataModel contest : this.processData.getContests()) {
                if (contest == null || contest.getErrorCodes().isEmpty()) {
                    continue;
                }
                for (ErrorCode errorCode : contest.getErrorCodes()) {
                    errorcodes.add(new ErrorcodeWithContestNameModel(errorCode, contest.getContestName()));
                }
            }
        }
        return errorcodes;
    }

    private void drawString(Graphics2D g2d, int y, String format, Object... params) {
        g2d.drawString(String.format(format, params), 0, y);
    }

}
