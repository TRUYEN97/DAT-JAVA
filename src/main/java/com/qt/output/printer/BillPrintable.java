/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.output.printer;

import com.qt.common.CarConfig;
import com.qt.common.ErrorLog;
import com.qt.model.modelTest.Errorcode;
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

    private int y = 0;

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (this.processData == null || pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        try {
            y = 0;
            int yShift = 10;
            String centerName = CarConfig.getInstance().getCenterName();
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
//            g2d.drawImage(icon.getImage(), 40, 0, 40, 40, null);
            drawString(g2d, yShift, 27, "Sát hạch viên ký tên: ");
            drawString(g2d, yShift * 4, 27, "***************************");
            drawString(g2d, yShift, 27, centerName);
            drawString(g2d, yShift, 27, "---------------------------");
            drawString(g2d, yShift, 27, "Số báo danh: %s", this.processData.getId());
            drawString(g2d, yShift, 27, "Số xe: %s", this.processData.getCarId());
            drawString(g2d, yShift, 27, "Họ tên: %s", this.processData.getName());
            drawString(g2d, yShift, 27, "Ngày thi: %s", this.processData.getStartTime());
            List<ErrorcodeWithContestNameModel> errorcodes = getErrorCode();
            if (!errorcodes.isEmpty()) {
                drawString(g2d, yShift, 27, "---------------------------");
                drawString(g2d, yShift, 27, "Các lỗi:");
                for (ErrorcodeWithContestNameModel errorcode : errorcodes) {
                    drawString(g2d, yShift, 27, " - %s, %s, %s",
                            errorcode.getErrName(),
                            errorcode.getErrPoint(),
                            errorcode.getContestName());
                }
            }
            drawString(g2d, yShift, 27, "---------------------------");
            drawString(g2d, yShift, 27, "Điểm: %s/100", this.processData.getScore());
            drawString(g2d, yShift, 27, "Kết quả: %s", getContestResult());
            drawString(g2d, yShift, 27, "Thí sinh ký tên:   ");
            drawString(g2d, yShift * 4, 27, "***************************");
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
                return "Đạt";
            }
            case ProcessModel.FAIL -> {
                return "Không đạt";
            }
            case ProcessModel.RUNNING -> {
                return "Đang thi";
            }
        }
        return rs;
    }

    protected List<ErrorcodeWithContestNameModel> getErrorCode() {
        //        ImageIcon icon = new ImageIcon(getClass().getResource("/bill_icon.png"));
        List<ErrorcodeWithContestNameModel> errorcodes = new ArrayList<>();
        try {
            if (!this.processData.getErrorCodes().isEmpty()) {
                for (Errorcode errorCode : this.processData.getErrorCodes()) {
                    errorcodes.add(new ErrorcodeWithContestNameModel(errorCode, ""));
                }
            }
            List<ContestDataModel> contestModels = this.processData.getContests();
            if (contestModels != null && !contestModels.isEmpty()) {
                ContestDataModel contestModel;
                for (int i = 0; i < contestModels.size(); i++) {
                    contestModel = contestModels.get(i);
                    if (contestModel == null || contestModel.getErrorCodes().isEmpty()) {
                        continue;
                    }
                    for (Errorcode errorCode : contestModel.getErrorCodes()) {
                        errorcodes.add(new ErrorcodeWithContestNameModel(errorCode, contestModel.getContestName()));
                    }
                }
            }
            return errorcodes;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
            return errorcodes;
        }
    }

    private void drawLine(Graphics2D g2d, String line, int dis, int lineNum) {
        y += dis;
        if (line.length() > lineNum) {
            g2d.drawString(line.substring(0, lineNum), 0, y);
            drawLine(g2d, line.substring(lineNum), dis, lineNum);
        } else {
            g2d.drawString(line, 0, y);
        }
    }

    private void drawString(Graphics2D g2d, int dis, int lineNum, String format, Object... params) {
        try {
            String line = String.format(format, params);
            drawLine(g2d, line, dis, lineNum);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorLog.addError(this, e);
        }
    }

}
