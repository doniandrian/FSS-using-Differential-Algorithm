package algen;

import java.util.Random;


public class Individu {
    private int[] urutanPekerjaan;
    private int[] urutanMesin;

    public Individu(int banyakPekerjaan, int banyakMesin) {
        Random rand = new Random();
        this.urutanPekerjaan = new int[banyakPekerjaan];
        this.urutanMesin = new int[banyakMesin];
        for (int i = 0; i < urutanPekerjaan.length; i++) {
            int hslRandom = rand.nextInt(banyakPekerjaan) + 1;
            if (i == 0) {
                this.urutanPekerjaan[i] = hslRandom;
            } else {
                while (this.periksaUrutanPekerjaan(i, hslRandom) == false) {
                    hslRandom = rand.nextInt(banyakPekerjaan) + 1;
                }
                this.urutanPekerjaan[i] = hslRandom;
            }
        }
        for (int i = 0; i < urutanMesin.length; i++) {
            urutanMesin[i] = i + 1;
        }
    }

    public Individu(int[] urutanJob, int banyakMesin) {
        this.urutanPekerjaan = urutanJob;
        this.urutanMesin = new int[banyakMesin];
        for (int i = 0; i < urutanMesin.length; i++) {
            urutanMesin[i] = i + 1;
        }
    }

    // Tambahkan konstruktor yang menerima dua array
    public Individu(int[] urutanJob, int[] urutanMesin) {
        this.urutanPekerjaan = urutanJob;
        this.urutanMesin = urutanMesin;
    }

    private boolean periksaUrutanPekerjaan(int penunjuk, int hslRandom) {
        for (int i = 0; i < penunjuk; i++) {
            if (this.urutanPekerjaan[i] == hslRandom) {
                return false;
            }
        }
        return true;
    }

    public int[] getUrutanPekerjaan() {
        return urutanPekerjaan;
    }

    public int[] getUrutanMesin() {
        return urutanMesin;
    }

    // Setter untuk urutanPekerjaan
    public void setUrutanPekerjaan(int[] urutanPekerjaan) {
        this.urutanPekerjaan = urutanPekerjaan;
    }

    // Setter untuk urutanMesin
    public void setUrutanMesin(int[] urutanMesin) {
        this.urutanMesin = urutanMesin;
    }
}
