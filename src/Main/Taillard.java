/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import java.util.Random;

import algen.ALGEN;
import algen.Individu;

/**
 *
 * @author Necron
 */
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
    private Individu[] populasi;
    private ALGEN[] algenArray;
    
    
    
    
    public Taillard(String lokasiFile, int banyakIndividu,int maksIterasi, int peluangCO, int peluangMutasi, int soalTerpilih) {
        this.batasAtas = new int[10];
        this.batasBawah = new int[10];
        this.kasusTaillard = new TestRead(lokasiFile);
        this.pemenangTiapSoal = new Individu[10];
        this.makespanPemenang = new int[10];
        this.soalTerpilih = soalTerpilih;
        this.algenArray = new ALGEN[10];


        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
             
             double random = rand.nextDouble();
             System.out.println("Random : " + random);
             System.out.println("Batas Atas : " + this.kasusTaillard.getBatasAtas()[i]);
            System.out.println("Batas Bawah : " + this.kasusTaillard.getBatasBawah()[i]);

            //
            int jmlPopulasi =  (int) ((batasBawah[i] + random * (this.kasusTaillard.getBatasAtas()[i]-this.kasusTaillard.getBatasBawah()[i])));
            
            System.out.println("Jumlah Populasi : " + jmlPopulasi);
            this.algenArray[i] = new ALGEN(jmlPopulasi, banyakIndividu, kasusTaillard.getKumpulanSoal()[i].getSoal(), maksIterasi, peluangCO, peluangMutasi, this.batasAtas[i], this.batasBawah[i]);
            this.pemenangTiapSoal[i] = this.algenArray[i].getPemenang();
            this.makespanPemenang[i] = this.algenArray[i].getMakespanPemenang();
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
        for (int i = 0; i < soalTerpilih+1; i++) {
            for (int j = 0; j < pemenangTiapSoal[i].getUrutanMesin().length; j++) {
                for (int k = 0; k < pemenangTiapSoal[i].getUrutanPekerjaan().length; k++) {
                    tampungUrutanKerja[j][k] = periksaSoal[i].getSoal()[j][this.daftarUrutanPekerjaan[i][k] - 1];
                }
            }
            this.tampungSoal[i].setSoal(tampungUrutanKerja);
        }
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
