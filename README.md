Penyelesaian Flow Shop Scheduling Problem (FSP) dengan Differential Evolution (DE)

Differential Evolution (DE) adalah algoritma metaheuristic yang populer untuk menyelesaikan berbagai masalah optimasi, termasuk Flow Shop Scheduling Problem (FSP). Algoritma ini bekerja dengan cara iterasi, di mana pada setiap iterasi, individu-individu dalam populasi diolah untuk menghasilkan individu baru yang lebih optimal.

Berikut adalah langkah-langkah umum untuk menyelesaikan FSP dengan DE:

1. Inisialisasi Populasi:

Buat populasi awal yang terdiri dari beberapa individu.
Setiap individu dalam populasi mewakili solusi potensial untuk FSP, yang terdiri dari urutan pekerjaan pada mesin.
Inisialisasi populasi dapat dilakukan secara acak atau menggunakan heuristik.
2. Evaluasi Individu:

Hitung nilai makespan (waktu penyelesaian total) untuk setiap individu dalam populasi.
Makespan adalah metrik yang digunakan untuk mengevaluasi kualitas solusi FSP.
3. Crossover:

Pilih dua individu dari populasi secara acak (individu target dan individu donor).
Gunakan strategi crossover DE untuk menghasilkan individu baru (individu trial).
Crossover DE menggabungkan informasi dari individu target dan individu donor untuk menghasilkan solusi baru.
4. Mutasi:

Terapkan mutasi DE pada individu trial untuk menghasilkan individu mutan.
Mutasi DE mengubah beberapa komponen vektor individu dengan cara acak.
Mutasi membantu DE untuk keluar dari solusi lokal dan menjelajahi ruang pencarian yang lebih luas.
5. Seleksi:

Bandingkan makespan individu mutan dengan makespan individu target.
Jika individu mutan memiliki makespan yang lebih kecil, maka individu mutan dimasukkan ke dalam populasi.
Jika individu target memiliki makespan yang lebih kecil atau sama, maka individu target dipertahankan dalam populasi.
6. Pengulangan:

Ulangi langkah 2 hingga 5 hingga tercapai kondisi terminasi (misalnya, jumlah iterasi maksimum atau nilai makespan yang cukup optimal).
7. Solusi Terbaik:

Setelah iterasi selesai, pilih individu dalam populasi dengan makespan terkecil sebagai solusi terbaik untuk FSP.
