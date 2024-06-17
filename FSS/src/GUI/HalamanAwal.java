/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import javax.swing.*;

import Main.Taillard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class HalamanAwal extends JFrame {
    private JPanel panelMain;
    private JTextField lokasiTxtField;
    private JTextField numJobsField;
    private JTextField numMachinesField;
    private JTextField maxIterationsField;
    private JTextField tabuTenureField;
    private JTextField banyakIndividuField;
    private JTextField peluangCOField;
    private JTextField peluangMutasiField;
    private JTextField soalTerpilihField;
    private JButton runTabuSearchButton;
    private JTextArea resultArea;

    public HalamanAwal() {
        setContentPane(panelMain);
        setTitle("Flow Shop Scheduling with Tabu Search");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        runTabuSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lokasiFile = lokasiTxtField.getText();
                int numJobs = Integer.parseInt(numJobsField.getText());
                int numMachines = Integer.parseInt(numMachinesField.getText());
                int maxIterations = Integer.parseInt(maxIterationsField.getText());
                int tabuTenure = Integer.parseInt(tabuTenureField.getText());
                int banyakIndividu = Integer.parseInt(banyakIndividuField.getText());
                int peluangCO = Integer.parseInt(peluangCOField.getText());
                int peluangMutasi = Integer.parseInt(peluangMutasiField.getText());
                int soalTerpilih = Integer.parseInt(soalTerpilihField.getText());

                Taillard taillard = new Taillard(lokasiFile, banyakIndividu, maxIterations, peluangCO, peluangMutasi, soalTerpilih, numJobs, numMachines, maxIterations, tabuTenure);
                taillard.runTabuSearch();

                resultArea.setText("Optimal Job Order: " + Arrays.toString(taillard.getOptimalSolution()) + "\n" +
                                   "Optimal Total Flow Time: " + taillard.getOptimalFlowTime());
            }
        });
    }

    public static void main(String[] args) {
        new HalamanAwal();
    }
}

