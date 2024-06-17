/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.Arrays;

import javax.swing.*;

public class Hasil {
    private JTextArea resultArea;

    public void displayResults(int[] optimalSolution, int optimalFlowTime) {
        resultArea.setText("Optimal Job Order: " + Arrays.toString(optimalSolution) + "\n" +
                           "Optimal Total Flow Time: " + optimalFlowTime);
    }
}

