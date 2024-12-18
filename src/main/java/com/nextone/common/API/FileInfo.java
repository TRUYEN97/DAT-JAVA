/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.common.API;

import java.nio.file.Path;

/**
 *
 * @author Administrator
 */
public class FileInfo {

    public static enum type {
        FILE, BYTE, INPUT_STREAM
    };
    private Path name;
    private Object file;
    private String partName;
    private final type type;

    public FileInfo(type type) {
        this.type = type;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName == null ? "" : partName;
    }

    public type getType() {
        return type;
    }

    public String getName() {
        if (name == null) {
            return null;
        }
        return name.toString();
    }

    public void setName(Path name) {
        this.name = name;
    }

    public void setName(String name) {
        setName(Path.of(name));
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

}
