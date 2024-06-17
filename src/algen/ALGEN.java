/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algen;

import java.util.Random;
import fss.FSS;

/**
 *
 * @author Andi
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
            if (indexPemenang == this.getIndexPemenang()) {
                penghitung++;
            } else {
                penghitung = 1;
            }
            indexPemenang = this.getIndexPemenang();
        }
        this.pemenang = this.individu[indexPemenang];
    }
    
    public void setMakespanPemenang(){
        this.penghasilMakespan = new FSS(this.pemenang,this.jadwal);
        this.makespanPemenang = this.penghasilMakespan.getMakespan();
    }

    public int getMakespanPemenang() {
        return makespanPemenang;
    }

    public int getIndexPemenang() {
        int simpanan = 0;
        for (int i = 0; i < this.individu.length; i++) {
            this.penghasilMakespan = new FSS(this.individu[i],this.jadwal);
            this.perbandingan[i] = this.penghasilMakespan.getMakespan();
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

    public Individu crossover(Individu induk1, Individu induk2) {
        int[] urutanPekerjaanAnak = this.crossoverPekerjaan(induk1.getUrutanPekerjaan(), induk2.getUrutanPekerjaan());
        Individu indukBaru = new Individu(urutanPekerjaanAnak, induk1.getUrutanMesin().length);
        return indukBaru;
    }

    public int[] crossoverPekerjaan(int[] urutanPekerjaan1, int[] urutanPekerjaan2) {
        Random rand = new Random();
        int penghitung1 = 0;
        int penghitung2 = 0;
        int[] urutanPekerjaanBaru = new int[urutanPekerjaan1.length];
        for (int i = 0; i < urutanPekerjaanBaru.length; i++) {
            urutanPekerjaanBaru[i] = rand.nextInt(2);
            if (i == 0) {
                if (urutanPekerjaanBaru[i] == 0) {
                    urutanPekerjaanBaru[i] = urutanPekerjaan1[penghitung1];
                    penghitung1++;
                } else {
                    urutanPekerjaanBaru[i] = urutanPekerjaan2[penghitung2];
                    penghitung2++;
                }
            } else {
                if (urutanPekerjaanBaru[i] == 0) {
                    urutanPekerjaanBaru[i] = urutanPekerjaan1[penghitung1];
                    while (this.periksaCrossover(urutanPekerjaanBaru, i, urutanPekerjaan1[penghitung1]) == false) {
                        penghitung1++;
                        if (penghitung1 > urutanPekerjaan1.length) {
                            penghitung1 = 0;
                        }
                        urutanPekerjaanBaru[i] = urutanPekerjaan1[penghitung1];
                    }
                } else {
                    urutanPekerjaanBaru[i] = urutanPekerjaan2[penghitung2];
                    while (this.periksaCrossover(urutanPekerjaanBaru, i, urutanPekerjaan2[penghitung2]) == false) {
                        penghitung2++;
                        if (penghitung2 > urutanPekerjaan2.length) {
                            penghitung2 = 0;
                        }
                        urutanPekerjaanBaru[i] = urutanPekerjaan2[penghitung2];
                    }
                }
            }
        }
        return urutanPekerjaanBaru;
    }

//    public int[] crossoverMesin(int[] urutanMesin1, int[] urutanMesin2) {
//        Random rand = new Random();
//        int count1 = 0;
//        int count2 = 0;
//        int[] urutanMesinBaru = new int[urutanMesin1.length];
//        for (int i = 0; i < urutanMesinBaru.length; i++) {
//            urutanMesinBaru[i] = rand.nextInt(2);
//            if (i == 0) {
//                if (urutanMesinBaru[i] == 0) {
//                    urutanMesinBaru[i] = urutanMesin1[count1];
//                    count1++;
//                } else {
//                    urutanMesinBaru[i] = urutanMesin2[count2];
//                    count2++;
//                }
//            } else {
//                if (urutanMesinBaru[i] == 0) {
//                    urutanMesinBaru[i] = urutanMesin1[count1];
//                    while (this.checkCollisionCrossover(urutanMesinBaru, i, urutanMesin1[count1]) == false) {
//                        count1++;
//                        if(count1 > urutanMesin1.length){
//                            count1 = 0;
//                        }
//                        urutanMesinBaru[i] = urutanMesin1[count1];
//                    }
//                } else {
//                    urutanMesinBaru[i] = urutanMesin2[count2];
//                    while (this.checkCollisionCrossover(urutanMesinBaru, i, urutanMesin2[count2]) == false) {
//                        count2++;
//                        if(count2 > urutanMesin2.length){
//                            count2 = 0;
//                        }
//                        urutanMesinBaru[i] = urutanMesin2[count2];
//                    }
//                }
//            }
//        }
//        return urutanMesinBaru;
//    }
    public boolean periksaCrossover(int[] urutanPekerjaanBaru, int penunjuk, int urutanPekerjaan1) {
        for (int i = 0; i < penunjuk; i++) {
            if (urutanPekerjaanBaru[i] == urutanPekerjaan1) {
                return false;
            }
        }
        return true;
    }

    public Individu mutasi(Individu x) {
        int[] penyimpanPekerjaan = x.getUrutanPekerjaan();
        int[] penyimpanMesin = x.getUrutanMesin();
        Random rand = new Random();
        int randomPekerjaan1 = rand.nextInt(x.getUrutanPekerjaan().length);
        int randomPekerjaan2 = rand.nextInt(x.getUrutanPekerjaan().length);
        while (randomPekerjaan1 == randomPekerjaan2) {
            randomPekerjaan1 = rand.nextInt(x.getUrutanPekerjaan().length);
            randomPekerjaan2 = rand.nextInt(x.getUrutanPekerjaan().length);
        }
        int tampungPekerjaan = -1;
        tampungPekerjaan = penyimpanPekerjaan[randomPekerjaan1];
        penyimpanPekerjaan[randomPekerjaan1] = penyimpanPekerjaan[randomPekerjaan2];
        penyimpanPekerjaan[randomPekerjaan2] = tampungPekerjaan;
        return new Individu(penyimpanPekerjaan, penyimpanMesin.length);
    }
}
