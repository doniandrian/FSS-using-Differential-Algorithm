package algen;

import java.util.Random;
import fss.FSS;

public class ALGEN {
    
    private FSS penghasilMakespan;
    private int[][] jadwal;
    private Individu[] populasi;
    private int[] perbandingan;
    private Individu pemenang;
    private int makespanPemenang;
    private int panjangVektor;
    private int batasAtas;
    private int batasBawah;

    public ALGEN(int jmlPopulasi, int panjangVektor, int[][] jadwal, int maksIterasi, int peluangCO, int peluangMutasi, int batasAtas, int batasBawah) {
        this.panjangVektor = panjangVektor;
        this.batasBawah = batasBawah;
        this.batasAtas = batasAtas;
      
        

        this.populasi = new Individu[jmlPopulasi];

        this.jadwal = jadwal;
        
        inisialisasiPopulasi();
        getJadwalTerbaik(maksIterasi, peluangCO, peluangMutasi);
        setMakespanPemenang();
    }
    
    private void inisialisasiPopulasi() {
        Random rand = new Random();
        for (int i = 0; i < populasi.length; i++) {
            populasi[i] = new Individu(jadwal[0].length, jadwal.length);
            populasi[i].randomisasi();
        }
    }

    private void getJadwalTerbaik(int maksIterasi, int peluangCO, int peluangMutasi) {
        Random rand = new Random();
        int penghitung = 1;
        int indexPemenang = getIndexPemenang();
        int periksaPeluang = 0;

        while (penghitung < maksIterasi) {
            for (int i = 0; i < populasi.length; i++) {
                if (i != indexPemenang) {
                    int randomInduk1 = rand.nextInt(populasi.length);
                    while (randomInduk1 == indexPemenang) {
                        randomInduk1 = rand.nextInt(populasi.length);
                    }

                    int randomInduk2 = rand.nextInt(populasi.length);
                    while (randomInduk2 == indexPemenang) {
                        randomInduk2 = rand.nextInt(populasi.length);
                    }

                    Individu target = populasi[i];
                    Individu mutan = crossover(populasi[randomInduk1], populasi[randomInduk2], peluangCO);

                    periksaPeluang = rand.nextInt(100);
                    if (periksaPeluang <= peluangMutasi) {
                        populasi[i] = mutasi(target, populasi[randomInduk1], populasi[randomInduk2], rand.nextDouble());
                    } else {
                        populasi[i] = selection(populasi[i], mutan);
                    }
                }
            }
            if (indexPemenang == getIndexPemenang()) {
                penghitung++;
            } else {
                penghitung = 1;
            }
            indexPemenang = getIndexPemenang();
        }
        this.pemenang = populasi[indexPemenang];
    }

    public void setMakespanPemenang() {
        this.penghasilMakespan = new FSS(this.pemenang, this.jadwal);
        this.makespanPemenang = this.penghasilMakespan.getMakespan();
    }

    public int getMakespanPemenang() {
        return makespanPemenang;
    }

    public int getIndexPemenang() {
        int simpanan = Integer.MAX_VALUE;
        int indexPemenang = 0;
        this.perbandingan = new int[populasi.length];

        for (int i = 0; i < populasi.length; i++) {
            this.penghasilMakespan = new FSS(populasi[i], jadwal);
            this.perbandingan[i] = this.penghasilMakespan.getMakespan();
            if (simpanan >= this.perbandingan[i]) {
                simpanan = this.perbandingan[i];
                this.pemenang = populasi[i];
                indexPemenang = i;
            }
        }
        return indexPemenang;
    }

    public Individu getPemenang() {
        return pemenang;
    }

    public Individu crossover(Individu target, Individu mutan, double CR) {
        Random rand = new Random();
        int[] targetPekerjaan = target.getUrutanPekerjaan();
        int[] mutanPekerjaan = mutan.getUrutanPekerjaan();
        int[] trialPekerjaan = new int[targetPekerjaan.length];

        for (int i = 0; i < targetPekerjaan.length; i++) {
            if (rand.nextDouble() < CR) {
                trialPekerjaan[i] = mutanPekerjaan[i];
            } else {
                trialPekerjaan[i] = targetPekerjaan[i];
            }
        }

        return new Individu(trialPekerjaan, target.getUrutanMesin().length);
    }

    public Individu mutasi(Individu base, Individu ind1, Individu ind2, double F) {
        int[] basePekerjaan = base.getUrutanPekerjaan();
        int[] ind1Pekerjaan = ind1.getUrutanPekerjaan();
        int[] ind2Pekerjaan = ind2.getUrutanPekerjaan();

        int[] mutanPekerjaan = new int[basePekerjaan.length];

        for (int i = 0; i < basePekerjaan.length; i++) {
            int diff = ind1Pekerjaan[i] - ind2Pekerjaan[i];
            mutanPekerjaan[i] = basePekerjaan[i] + (int) (F * diff);
            mutanPekerjaan[i] = Math.floorMod(mutanPekerjaan[i], basePekerjaan.length);
        }

        return new Individu(mutanPekerjaan, base.getUrutanMesin().length);
    }

    private Individu selection(Individu target, Individu trial) {
        FSS fssTarget = new FSS(target, jadwal);
        FSS fssTrial = new FSS(trial, jadwal);

        int targetMakespan = fssTarget.getMakespan();
        int trialMakespan = fssTrial.getMakespan();

        return (trialMakespan < targetMakespan) ? trial : target;
    }
}
