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
                
                     this.individu[i] = this.crossover(this.individu[randomInduk1], this.individu[randomInduk2],peluangCO);
                
                    periksaPeluang = rand.nextInt(100);
                    if (periksaPeluang <= peluangMutasi) {
                        this.individu[i] = this.mutasi(this.individu[i], pemenang, pemenang, randomInduk2);
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

    public Individu crossover(Individu target, Individu mutan, double CR) {
        Random rand = new Random();
        int[] targetPekerjaan = target.getUrutanPekerjaan();
        int[] mutanPekerjaan = mutan.getUrutanPekerjaan();
        int[] trialPekerjaan = new int[targetPekerjaan.length];
    
        int jRand = rand.nextInt(targetPekerjaan.length); // Memilih indeks acak jRand
    
        for (int i = 0; i < targetPekerjaan.length; i++) {
            if (rand.nextDouble() < CR || i == jRand) {
                trialPekerjaan[i] = mutanPekerjaan[i];
            } else {
                trialPekerjaan[i] = targetPekerjaan[i];
            }
        }
    
        return new Individu(trialPekerjaan, target.getUrutanMesin().length);
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


    public boolean periksaCrossover(int[] urutanPekerjaanBaru, int penunjuk, int urutanPekerjaan1) {
        for (int i = 0; i < penunjuk; i++) {
            if (urutanPekerjaanBaru[i] == urutanPekerjaan1) {
                return false;
            }
        }
        return true;
    }
    public Individu mutasi(Individu base, Individu ind1, Individu ind2, double F) {
        int[] basePekerjaan = base.getUrutanPekerjaan();
        int[] ind1Pekerjaan = ind1.getUrutanPekerjaan();
        int[] ind2Pekerjaan = ind2.getUrutanPekerjaan();
    
        int[] mutanPekerjaan = new int[basePekerjaan.length];
    
        for (int i = 0; i < basePekerjaan.length; i++) {
            // Menerapkan mutasi sesuai formula DE: base + F * (ind1 - ind2)
            int diff = ind1Pekerjaan[i] - ind2Pekerjaan[i];
            mutanPekerjaan[i] = basePekerjaan[i] + (int)(F * diff);
    
            // Memastikan bahwa hasil mutasi tidak keluar dari batas yang valid
            mutanPekerjaan[i] = Math.floorMod(mutanPekerjaan[i], basePekerjaan.length);
        }
    
        // Menciptakan individu baru dengan urutan pekerjaan yang telah dimutasi
        return new Individu(mutanPekerjaan, base.getUrutanMesin().length);
    }
    
    
}
