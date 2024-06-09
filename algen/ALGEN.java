package algen;

import java.util.Random;
import fss.FSS;

public class ALGEN {

    private FSS penghasilMakespan;
    private int[][] jadwal;
    private Individu[] individu;
    private int[] perbandingan;
    private Individu pemenang;
    private int makespanPemenang;

    public ALGEN(int banyakpopulasi, int[][] jadwal, int maksIterasi, double peluangCO, double faktorSkala) {
        this.jadwal = jadwal;
        this.individu = new Individu[banyakpopulasi];
        this.perbandingan = new int[banyakpopulasi];
        for (int i = 0; i < this.individu.length; i++) {
            individu[i] = new Individu(jadwal[0].length, jadwal.length);
        }
        this.getJadwalTerbaik(maksIterasi, peluangCO, faktorSkala);
        this.setMakespanPemenang();
    }

    private void getJadwalTerbaik(int maksIterasi, double peluangCO, double faktorSkala) {
        Random rand = new Random();
        int penghitung = 1;
        int indexPemenang = this.getIndexPemenang();
        while (penghitung < maksIterasi) {
            FSS fss = new FSS(individu, jadwal, faktorSkala, peluangCO);
            if (indexPemenang == this.getIndexPemenang()) {
                penghitung++;
            } else {
                penghitung = 1;
            }
            indexPemenang = this.getIndexPemenang();
        }
        this.pemenang = this.individu[indexPemenang];
    }
    
    public void setMakespanPemenang() {
        int i = 0;
        Individu[] singleIndividuArray = new Individu[] { this.individu[i] };
        this.penghasilMakespan = new FSS(singleIndividuArray, this.jadwal, 0.8, 0.9);
        this.perbandingan[i] = this.penghasilMakespan.getMakespans()[0]; 
    }

    public int getMakespanPemenang() {
        return makespanPemenang;
    }

    public int getIndexPemenang() {
        int simpanan = 0;
        for (int i = 0; i < this.individu.length; i++) {
            Individu[] singleIndividuArray = new Individu[] { this.individu[i] };
            this.penghasilMakespan = new FSS(singleIndividuArray, this.jadwal, 0.8, 0.9);
            this.perbandingan[i] = this.penghasilMakespan.getMakespans()[0]; 
        }
        simpanan = Integer.MAX_VALUE;
        int indexPemenang = 0;
        for (int i = 0; i < this.perbandingan.length; i++) {
            if (simpanan >= this.perbandingan[i]) {
                simpanan = this.perbandingan[i];
                this.pemenang = this.individu[i];
                indexPemenang = i;
            }
        }
        return indexPemenang;
    }

    public Individu getPemenang() {
        return this.pemenang;
    }
}
