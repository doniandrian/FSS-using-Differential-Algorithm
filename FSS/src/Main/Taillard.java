/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import algen.ALGEN;
import algen.Individu;
import fss.FSS;

public class Taillard {
    private TestRead kasusTaillard;
    private ALGEN hitungMS;
    private Individu[] pemenangTiapSoal;
    private int[] makespanPemenang;
    private int[] batasAtas;
    private int[] batasBawah;
    private Soal[] tampungSoal;
    private int[][] daftarUrutanPekerjaan;
    private int soalTerpilih;
    private FSS fss;
    private int maxIterations;
    private int tabuTenure;
    private int[] optimalSolution;
    private int optimalFlowTime;
    
    public Taillard(String lokasiFile, int banyakIndividu, int maksIterasi, int peluangCO, int peluangMutasi, int soalTerpilih, int numJobs, int numMachines, int maxIterations, int tabuTenure) {
        this.batasAtas = new int[10];
        this.batasBawah = new int[10];
        this.kasusTaillard = new TestRead(lokasiFile);
        this.pemenangTiapSoal = new Individu[10];
        this.makespanPemenang = new int[10];
        this.soalTerpilih = soalTerpilih;
        this.fss = new FSS(numJobs, numMachines);
        this.maxIterations = maxIterations;
        this.tabuTenure = tabuTenure;
        for (int i = 0; i < 10; i++) {
            this.hitungMS = new ALGEN(banyakIndividu, kasusTaillard.getKumpulanSoal()[i].getSoal(), maksIterasi, peluangCO, peluangMutasi);
            this.pemenangTiapSoal[i] = this.hitungMS.getPemenang();
            this.makespanPemenang[i] = this.hitungMS.getMakespanPemenang();
            this.batasAtas[i] = this.kasusTaillard.getBatasAtas()[i];
            this.batasBawah[i] = this.kasusTaillard.getBatasBawah()[i];
        }
        this.daftarUrutanPekerjaan = new int[10][this.pemenangTiapSoal[0].getUrutanPekerjaan().length];
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < this.pemenangTiapSoal[0].getUrutanPekerjaan().length; k++) {
                this.daftarUrutanPekerjaan[i][k] = this.pemenangTiapSoal[i].getUrutanPekerjaan()[k];
            }
        }
        this.getJadwalPemenang(soalTerpilih);
    }
    
    public Soal[] getTampungSoal() {
        return tampungSoal;
    }

    private void getJadwalPemenang(int soalTerpilih) {
        Soal[] periksaSoal = this.kasusTaillard.getKumpulanSoal();
        this.tampungSoal = new Soal[periksaSoal.length];
        for (int i = 0; i < this.tampungSoal.length; i++) {
            this.tampungSoal[i] = new Soal(pemenangTiapSoal[0].getUrutanMesin().length, pemenangTiapSoal[0].getUrutanPekerjaan().length);
        }
        int[][] tampungUrutanKerja = new int[this.kasusTaillard.getBanyakMesin()][this.kasusTaillard.getBanyakPekerjaan()];
        for (int i = 0; i < soalTerpilih + 1; i++) {
            for (int j = 0; j < pemenangTiapSoal[i].getUrutanMesin().length; j++) {
                for (int k = 0; k < pemenangTiapSoal[i].getUrutanPekerjaan().length; k++) {
                    tampungUrutanKerja[j][k] = periksaSoal[i].getSoal()[j][this.daftarUrutanPekerjaan[i][k] - 1];
                }
            }
            this.tampungSoal[i].setSoal(tampungUrutanKerja);
        }
    }

    public void runTabuSearch() {
        optimalSolution = fss.tabuSearch(maxIterations, tabuTenure);
        optimalFlowTime = fss.calculateTotalFlowTime(optimalSolution);
    }

    public int[] getOptimalSolution() {
        return optimalSolution;
    }

    public int getOptimalFlowTime() {
        return optimalFlowTime;
    }

    public Individu[] getPemenangTiapSoal() {
        return pemenangTiapSoal;
    }

    public int[] getMakespanPemenang() {
        return makespanPemenang;
    }

    public int[] getBatasAtas() {
        return batasAtas;
    }

    public int[] getBatasBawah() {
        return batasBawah;
    }
}