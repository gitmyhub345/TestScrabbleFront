/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.service;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Rider1
 */
public class RandID {
    
    private final Random rand;
    
    public RandID(){
        rand = new Random(new Date().getTime());
    }
    
    public long getRandAppID(){
        return getRandomNumber(100000000);
    }
    
    public long getRandFieldID(){
        return getRandomNumber(10000000);
    }
    
    private long getRandomNumber(long number){
        long l = rand.nextLong()%number;
        while (l < number/10 || l > number){
            l = Math.abs(rand.nextLong()%number);
        }
        return l;
    }
}
