package algen;

import java.util.Random;
import fss.FSS;

/**
 *
 * Author: Andi
 */
public class ALGEN {

    private FSS penghasilMakespan;
    private int[][] jadwal;
    private Individu[] individu;
    private int[] perbandingan;
    private Individu pemenang;
    private int makespanPemenang;

    public ALGEN(int banyakIndividu, int[][] jadwal, int maksIterasi, int peluangCO, int peluangMutasi) {
        this.jadwal = jadwal;
        this.individu = new Individu[banyakIndividu];
        this.perbandingan = new int[banyakIndividu];
        for (int i = 0; i < this.individu.length; i++) {
            individu[i] = new Individu(jadwal[0].length, jadwal.length);
        }
        this.getJadwalTerbaik(maksIterasi, peluangCO, peluangMutasi);
        this.setMakespanPemenang();
    }

    private void getJadwalTerbaik(int maksIterasi, int peluangCO, int peluangMutasi) {
        Random rand = new Random();
        int penghitung = 1;
        int indexPemenang = this.getIndexPemenang();
        int periksaPeluang = 0;
        while (penghitung < maksIterasi) {
            for (int i = 0; i < this.individu.length; i++) {
                if (i != indexPemenang) {
                    int randomInduk1 = rand.nextInt(this.individu.length);
                    while (randomInduk1 == indexPemenang) {
                        randomInduk1 = rand.nextInt(this.individu.length);
                    }
                    int randomInduk2 = rand.nextInt(this.individu.length);
                    while (randomInduk2 == indexPemenang) {
                        randomInduk2 = rand.nextInt(this.individu.length);
                    }
                    periksaPeluang = rand.nextInt(100);
                    if (periksaPeluang <= peluangCO) {
                        this.individu[i] = this.crossover(this.individu[randomInduk1], this.individu[randomInduk2]);
                    }
                    periksaPeluang = rand.nextInt(100);
                    if (periksaPeluang <= peluangMutasi) {
                        this.individu[i] = this.mutasi(this.individu[i]);
                    }
                }
            }
            indexPemenang = this.getIndexPemenang();
            penghitung++;
        }
        this.pemenang = this.individu[indexPemenang];
    }

    private int getIndexPemenang() {
        int indexPemenang = 0;
        for (int i = 0; i < this.individu.length; i++) {
            this.penghasilMakespan = new FSS(this.individu[i], this.jadwal);
            this.perbandingan[i] = this.penghasilMakespan.getMakespan();
        }
        int nilaiMinimum = this.perbandingan[0];
        for (int i = 0; i < this.perbandingan.length; i++) {
            if (this.perbandingan[i] < nilaiMinimum) {
                nilaiMinimum = this.perbandingan[i];
                indexPemenang = i;
            }
        }
        return indexPemenang;
    }

    public void setMakespanPemenang() {
        this.makespanPemenang = this.penghasilMakespan.getMakespan();
    }

    public Individu getPemenang() {
        return this.pemenang;
    }

    public int getMakespanPemenang() {
        return this.makespanPemenang;
    }

    private Individu crossover(Individu induk1, Individu induk2) {
        // Implement crossover logic here
        return induk1; // Placeholder return statement
    }

    private Individu mutasi(Individu x) {
        // Implement mutation logic here
        return x; // Placeholder return statement
    }
}
