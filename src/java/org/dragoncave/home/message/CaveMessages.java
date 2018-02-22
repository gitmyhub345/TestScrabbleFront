/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.dragoncave.home.comp.TestPropFile;
/**
 *
 * @author Rider1
 */
public class CaveMessages {
    private HashMap<Integer,String> hash;
    
    public CaveMessages(){
        hash = new HashMap<Integer,String>();
        retrieveDictionary();
    }
    
    private void retrieveDictionary(){
        TestPropFile propFile = new TestPropFile();
        try{
            propFile.openfile("testDict.txt");
            byte[] dict = new byte[500];
            boolean endOfFile = false;
            while (!(endOfFile = propFile.readFile(dict))){
                String str = new String(dict).trim();
                String[] strArray = str.split("\r\n");
                for(String s: strArray){
                    String[] keyProp = s.split(":");
                    hash.put(Integer.parseInt(keyProp[0]), keyProp[1]);
                }
            }
            propFile.closeFile();
        } catch (FileNotFoundException ex){
            System.out.println(ex);
        } catch (IOException e){
            System.out.println("error reading file");
        }
    }
    
    public String getMessage(int messageNum){
        return hash.get((Integer)messageNum);
    }
    
}
