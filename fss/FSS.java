package fss;

import algen.Individu;
import java.util.Random;

public class FSS {

    private int[][] jadwal;
    private int[][] waktuAkhir;
    private Individu[] population;
    private Random rand = new Random();
    private double F; // faktor skala
    private double CR; // peluang crossover

    public FSS(Individu[] population, int[][] jadwal, double F, double CR) {
        this.jadwal = jadwal;
        this.waktuAkhir = new int[this.jadwal.length][this.jadwal[0].length];
        this.population = population;
        this.F = F;
        this.CR = CR;
        mutatePopulation();
    }

    // method untuk melakukan mutasi pada populasi
    private void mutatePopulation() {
        for (int i = 0; i < population.length; i++) {

            // pilih 3 individu secara acak, pastikan r1, r2, r3 tidak sama dengan i dan
            // tidak sama satu sama lain
            int r1 = rand.nextInt(population.length);
            int r2 = rand.nextInt(population.length);
            int r3 = rand.nextInt(population.length);

            // looping untuk memastikan r1, r2, r3 tidak sama dengan i dan tidak sama satu
            // sama lain
            while (r1 == i || r2 == i || r3 == i || r1 == r2 || r1 == r3 || r2 == r3) {
                // pilih 3 individu secara acak menggunakan random number generator
                r1 = rand.nextInt(population.length);
                r2 = rand.nextInt(population.length);
                r3 = rand.nextInt(population.length);

                
                System.out.println("r1: " + r1 + " r2: " + r2 + " r3: " + r3); //Disini infinite loop


            }

            // Individu target, a, b, c
            Individu target = population[i];
            Individu a = population[r1];
            Individu b = population[r2];
            Individu c = population[r3];

            // algoritma differential evolution
            int[] newSolution = new int[target.getUrutanPekerjaan().length];
            // looping untuk mendapatkan individu baru dengan
            
            for (int j = 0; j < target.getUrutanPekerjaan().length; j++) {
                // Mutasi 2 individu
                int mutantGene = (int) (a.getUrutanPekerjaan()[j]
                        + F * (b.getUrutanPekerjaan()[j] - c.getUrutanPekerjaan()[j]));
                mutantGene = Math.floorMod(mutantGene, target.getUrutanPekerjaan().length);
                newSolution[j] = mutantGene;
            }

            // Crossover
            for (int j = 0; j < target.getUrutanPekerjaan().length; j++) {
                if (rand.nextDouble() > CR) {
                    newSolution[j] = target.getUrutanPekerjaan()[j];
                }
            }

            // Selection
            Individu trialIndividu = new Individu(newSolution, target.getUrutanMesin().length);
            if (calculateMakespan(trialIndividu) < calculateMakespan(target)) {
                population[i] = trialIndividu;
            }
        }
    }

    // metode untuk mendapatkan make
    // metode untuk mendapatkan make
    public int[] getMakespans() {
        int[] makespans = new int[population.length];
        for (int i = 0; i < population.length; i++) {
            makespans[i] = calculateMakespan(population[i]);
        }
        return makespans;
    }

    private int calculateMakespan(Individu individu) { // Method untukenghitung makespan
        int pekerjaan = -1;
        int mesin = -1;
        for (int i = 0; i < this.jadwal.length; i++) {
            for (int j = 0; j < this.jadwal[0].length; j++) {
                pekerjaan = individu.getUrutanPekerjaan()[j] - 1;
                mesin = individu.getUrutanMesin()[i] - 1;
                if (i == 0 && j == 0) {
                    this.waktuAkhir[i][j] = this.jadwal[mesin][pekerjaan];
                } else {
                    if (i == 0) {
                        this.waktuAkhir[i][j] = this.jadwal[mesin][pekerjaan] + this.waktuAkhir[i][j - 1];
                    } else {
                        if (j == 0) {
                            this.waktuAkhir[i][j] = this.jadwal[mesin][pekerjaan] + this.waktuAkhir[i - 1][j];
                        } else {
                            if (this.waktuAkhir[i - 1][j] <= this.waktuAkhir[i][j - 1]) {
                                this.waktuAkhir[i][j] = this.jadwal[mesin][pekerjaan] + this.waktuAkhir[i][j - 1];
                            } else {
                                this.waktuAkhir[i][j] = this.jadwal[mesin][pekerjaan] + this.waktuAkhir[i - 1][j];
                            }
                        }
                    }
                }
            }
        }
        return this.waktuAkhir[this.jadwal.length - 1][this.jadwal[0].length - 1];
    }
}
