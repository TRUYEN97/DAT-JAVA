/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.view.swing;

import com.qt.model.modelTest.ErrorcodeWithContestNameModel;
import com.qt.view.element.ErrorcodeItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 *
 * @author Admin
 */
public class ListErrorcode<T> extends JList<ErrorcodeWithContestNameModel> {

    private final DefaultListModel<ErrorcodeWithContestNameModel> model;
    private final List<ErrorcodeItem> errorcodeItems;

    public ListErrorcode() {
        this.model = new DefaultListModel();
        this.errorcodeItems = new ArrayList<>();
        super.setModel(model);
    }
    @Override
    public void setModel(ListModel<ErrorcodeWithContestNameModel> md) {
        for (int i = 0; i < md.getSize(); i++) {
            model.addElement(md.getElementAt(i));
        }
    }

    public void addItem(ErrorcodeWithContestNameModel item) {
        model.addElement(item);
    }

    @Override
    public ListCellRenderer<? super ErrorcodeWithContestNameModel> getCellRenderer() {
        return (JList<? extends ErrorcodeWithContestNameModel> list, ErrorcodeWithContestNameModel value, int index, boolean isSelected, boolean cellHasFocus) -> {
            if (value == null) {
                value = new ErrorcodeWithContestNameModel("", 0, "", "");
            }
            if (index >= errorcodeItems.size()) {
                errorcodeItems.add(new ErrorcodeItem(value.getName(),
                        value.getScore(), value.getContestName()));
            }
            ErrorcodeItem item = errorcodeItems.get(index);
            return item;
        };
    }

    public void clear() {
        this.model.clear();
        this.errorcodeItems.clear();
    }

    public int getCount() {
       return this.model.getSize();
    }
}
