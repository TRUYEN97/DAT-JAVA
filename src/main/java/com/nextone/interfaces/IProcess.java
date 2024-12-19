/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nextone.interfaces;

import com.nextone.interfaces.IStarter;

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
