/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Necron
 */
public class Catatan {

    public Catatan(String isiTxt, String lokasiSimpan) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(lokasiSimpan);
            writer.print(isiTxt);
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Catatan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
    
}
