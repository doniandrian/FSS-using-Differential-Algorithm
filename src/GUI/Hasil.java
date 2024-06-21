/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Main.Catatan;
import Main.Taillard;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Necron
 */
public class Hasil extends javax.swing.JFrame {

    private File file;
    private JFileChooser pilih;
    private Taillard taillard;
    private String tampung;

    /**
     * Creates new form Hasil
     */
    public Hasil() {
        initComponents();
        
    }

    public Hasil(Taillard taillard, int soalTerpilih) {
        initComponents();
        this.taillard = taillard;
        this.tampung = new String();
        this.pilih = new JFileChooser();

        tampung = "";
        tampung += "Urutan job terbaik : \n";
        for (int j = 0; j < this.taillard.getPemenangTiapSoal()[0].getUrutanPekerjaan().length; j++) {
            tampung += this.taillard.getPemenangTiapSoal()[soalTerpilih].getUrutanPekerjaan()[j] + " ";
        }
        tampung += "\n\nJadwal terbaik : \n";
        for (int i = 0; i < this.taillard.getPemenangTiapSoal()[0].getUrutanMesin().length; i++) {
            for (int j = 0; j < this.taillard.getPemenangTiapSoal()[0].getUrutanPekerjaan().length; j++) {
                tampung += this.taillard.getTampungSoal()[soalTerpilih].getSoal()[i][j] + " ";
            }
            tampung += "\n";
        }
        tampung += "\n";
        int makespan = this.taillard.getMakespanPemenang()[soalTerpilih];
        tampung += "Makespan : " + makespan + "\n\n";

         
        this.HasilTerurutTextArea.append(tampung);

        this.inisialisasi();
    }

    private void inisialisasi() {
        DefaultTableModel model = (DefaultTableModel) this.TabelHasil.getModel();
        String[] tampung = new String[this.taillard.getPemenangTiapSoal().length];
        for (int i = 0; i < this.taillard.getPemenangTiapSoal().length; i++) {
            tampung[i] = "";
            for (int j = 0; j < this.taillard.getPemenangTiapSoal()[i].getUrutanPekerjaan().length; j++) {
                tampung[i] += this.taillard.getPemenangTiapSoal()[i].getUrutanPekerjaan()[j];
                if (j != this.taillard.getPemenangTiapSoal()[i].getUrutanPekerjaan().length - 1) {
                    tampung[i] += " - ";
                }
            }
        }
        for (int i = 0; i < this.taillard.getPemenangTiapSoal().length; i++) {
            model.addRow(new Object[]{i + 1, this.taillard.getBatasAtas()[i], this.taillard.getBatasBawah()[i], this.taillard.getMakespanPemenang()[i], tampung[i]});
        }
    }

//    for (int i = 0; i < 10; i++) {
//            tampung = "";
//            tampung += "Urutan job terbaik : \n";
//            for (int j = 0; j < this.taillard.getPemenangTiapSoal()[0].getUrutanJob().length; j++) {
//                tampung += this.taillard.getPemenangTiapSoal()[i].getUrutanJob()[j] + " ";
//            }
//            tampung += "\n";
//            int makespan = this.taillard.getMakespanPemenang()[i];
//            tampung += "Makespan : " + makespan + "\n\n";
//            this.HasilTerurutTextArea.append(tampung);
//        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        HasilTerurutTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelHasil = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Catatan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HasilTerurutTextArea.setColumns(20);
        HasilTerurutTextArea.setRows(5);
        jScrollPane1.setViewportView(HasilTerurutTextArea);

        TabelHasil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. soal", "Batas atas", "Batas bawah", "Makespan", "Urutan pekerjaan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TabelHasil);

        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Hasil Eksperimen");

        Catatan.setText("Buat catatan");
        Catatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CatatanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Catatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(510, 510, 510)
                .addComponent(jLabel1)
                .addContainerGap(530, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(Catatan))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Masukan kembali = new Masukan();
        kembali.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void CatatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CatatanActionPerformed
        // TODO add your handling code here:
        String lokasiSimpan = "";
        boolean periksa = false;
        try {
            pilih.showSaveDialog(null);
            file = pilih.getSelectedFile();
            lokasiSimpan = file.getPath() + "";
            periksa = true;
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Batal simpan");
            periksa = false;
        }
        if(periksa == true){
            Catatan cat = new Catatan(this.tampung, lokasiSimpan);
        }
    }//GEN-LAST:event_CatatanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Hasil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hasil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hasil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hasil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hasil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Catatan;
    private javax.swing.JTextArea HasilTerurutTextArea;
    private javax.swing.JTable TabelHasil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
