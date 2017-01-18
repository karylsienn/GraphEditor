/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.xml;

import com.fxgraph.graph.Model;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
/**
 *
 * @author Karol
 */
public class ModelXML {
    
    public void write(Model model, String filename) throws Exception {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(filename)))) {
            encoder.writeObject(model);
        }
    }
    
    public Model read(String filename) throws FileNotFoundException {
        Model o;
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)))) {
            o = (Model) decoder.readObject();
        }
        return o;
        
    }
    
}
