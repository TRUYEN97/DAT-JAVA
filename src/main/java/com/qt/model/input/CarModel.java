/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.input;

import com.qt.common.Util;
import java.util.ArrayDeque;
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@ToString
public class CarModel {

    private int status;
    private double distance;
    private int rpm;
    private float speed;
    private float speed1;
    private boolean nt;
    private boolean np;
    private boolean at;
    private boolean pt;
    private boolean cm;
    private boolean t1;
    private boolean t2;
    private boolean t3;
    private boolean s1;
    private boolean s2;
    private boolean s3;
    private boolean s4;
    private int gearBoxValue = 0;
    
    public void mathGearBoxValue(){
        gearBoxValue = Util.getGearBoxVal(s1, s2, s3, s4);
    }
    private final Queue<Byte> remoteValues = new ArrayDeque<>();

    public void setRemoteValue(Byte value) {
        if (value == null || value == -1) {
            return;
        }
        this.remoteValues.add(value);
    }

    public byte peekRemoteVal() {
        return this.remoteValues.peek();
    }

    public byte popRemoteVal() {
        return this.remoteValues.poll();
    }

    public boolean haveRemoteValues() {
        return !this.remoteValues.isEmpty();
    }
}
