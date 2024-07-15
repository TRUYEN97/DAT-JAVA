/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.model.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@Builder
public class MCU_CONFIG_MODEL {
    //{"encoder":900,"distance_udtime":200,"speed_udtime":500,"rpm_udtime":250,"senddt_udtime":200,"speed":0.1}
    private int encoder;
    private int distance_udtime;
    private int rpm_udtime;
    private int senddt_udtime;
}
