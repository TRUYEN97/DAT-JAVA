/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qt.interfaces;

import com.qt.interfaces.IStarter;

/**
 *
 * @author Admin
 */
interface IProcess extends Runnable, IStarter {

    void begin();

    void test();

    void end();

    boolean isRunning();
}
