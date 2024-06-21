/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fss;

import algen.Individu;
import java.util.Scanner;

/**
 *
 * @author Andi
 */
public class FSS {

    private int[][] jadwal;
    private int[][] waktuAkhir;
    private Individu individu;

    public FSS(Individu individu,int[][] jadwal) {
        this.jadwal = jadwal;
        this.waktuAkhir = new int[this.jadwal.length][this.jadwal[0].length];
        this.individu = individu;
        this.hitungMakespan();
    }

    public int[][] getWaktuAkhir() {
        return this.waktuAkhir;
    }

    public int[][] getJadwal() {
        return jadwal;
    }

    public void setIndividu(Individu individu) {
        this.individu = individu;
    }

    public int getMakespan() {
        return this.waktuAkhir[this.waktuAkhir.length-1][this.waktuAkhir[0].length-1];
    }

    private void hitungMakespan() {
        for (int i = 0; i < this.jadwal.length; i++) {
            for (int j = 0; j < this.jadwal[0].length; j++) {
                // Validasi indeks sebelum penggunaan
                int indexPekerjaan = Math.floorMod(this.individu.getUrutanPekerjaan()[j] - 1, this.jadwal[0].length);
                int indexMesin = Math.floorMod(this.individu.getUrutanMesin()[i] - 1, this.jadwal.length);
    
                int currentJob = this.jadwal[indexMesin][indexPekerjaan];
                if (i == 0 && j == 0) {
                    this.waktuAkhir[i][j] = currentJob;
                } else if (i == 0) {
                    this.waktuAkhir[i][j] = currentJob + this.waktuAkhir[i][j - 1];
                } else if (j == 0) {
                    this.waktuAkhir[i][j] = currentJob + this.waktuAkhir[i - 1][j];
                } else {
                    this.waktuAkhir[i][j] = currentJob + Math.max(this.waktuAkhir[i-1][j], this.waktuAkhir[i][j-1]);
                }
            }
        }
    }
    
}