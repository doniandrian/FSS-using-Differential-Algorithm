/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.swing.JTextField;

public class Masukan {
    private JTextField numJobsField;
    private JTextField numMachinesField;
    private JTextField maxIterationsField;
    private JTextField tabuTenureField;

    public int getNumJobs() {
        return Integer.parseInt(numJobsField.getText());
    }

    public int getNumMachines() {
        return Integer.parseInt(numMachinesField.getText());
    }

    public int getMaxIterations() {
        return Integer.parseInt(maxIterationsField.getText());
    }

    public int getTabuTenure() {
        return Integer.parseInt(tabuTenureField.getText());
    }
}

