import java.util.Scanner;


public class PengelolaanKeuangan {
    // Konstanta untuk jumlah maksimal transaksi yang dapat dicatat
    private static final int maks_transaksi = 100;

    // Variabel untuk menyimpan saldo, total penadapatan, dan total pengeluaran
    private static double saldo = 0;
    private static double totalPendapatan = 0;
    private static double totalPengeluaran = 0;

    // Array untuk menyimpan riwayat transaksi (jenis, jumlah, dan keterangan)
    private static String[][] riwayat = new String[maks_transaksi][3];
    private static int jumlahTransaksi = 0; // Penanda jumlah transaksi yang telah di catat

    private static Scanner input = new Scanner(System.in); // Scanner untuk input pengguna
    private static String username; // Variabel untuk menyimpan username
    private static String password; // Variabel untuk menyimpan password

    // Warna ANSI untuk tampilan terminal
    final static String RESET = "\u001B[0m";
    final static String RED = "\u001B[31m";
    final static String GREEN = "\u001B[32m";
    final static String YELLOW = "\u001B[33m";
    final static String BLUE = "\u001B[34m";
    final static String PURPLE = "\u001B[35m";
    final static String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        // Memulai proses registrasi akun
        registrasi();

        // Melakukan login sampai berhasil
        while (!login()) {
            System.out.println(RED + "Silahkan coba lagi!\n" + RESET);
        }

        // Menu utama aplikasi
        while (true) {
            tunjukanOpsi(); // Menampilkan pilihan menu
            int pilihan = input.nextInt(); // Membaca input pilihan
            input.nextLine(); // Membersihkan buffer input

            // Menjalankan kode berdasarkan pilihan pengguna
            switch (pilihan) {
                case 1:
                    tambahPendapatan();
                    break;
                case 2:
                    tambahPengeluaran();
                    break;
                case 3:
                    lihatRingkasan();
                    break;
                case 4:
                    lihatRiwayat();
                    break;
                case 5:
                    System.out.println(PURPLE + "Terima Kasih telah menggunakan aplikasi ini!\n" + RESET);
                    return;
                default:
                    System.out.println(RED + "Pilih di antara 1 - 5 ya bro, diluar itu gadak!" + RESET);
            }
        }
    }


    public static void registrasi() {
        // proses registrasi akun baru
        System.out.println("-------------------------------------------");
        System.out.println("---         REGISTRASI AKUN BARU        ---");
        System.out.println("-------------------------------------------");
        

        while (true) {
            System.out.print("Buat username: ");
            username = input.nextLine().trim(); // Membaca dan menghapus spasi dari input username
            if (username.isEmpty()) { // Validasi username tidak boleh kosong
                System.out.println(RED + "Username tidak boleh kosong. Silahkan coba lagi!" + RESET);
                continue;
            }

            System.out.print("Buat password (min.8 karakter, harus mengandung huruf besar, huruf kecil, angka, dan simbol): ");
            password = input.nextLine().trim(); // Membaca password

            // Validasi password
            if (!PasswordBenar(password)) {
                System.out.println(RED + "Password tidak valid. Pastikan password minimal 8 karakter dan mengandung:\n" + 
                                    "- Huruf besar (A-Z)\n" +
                                    "- Huruf kecil (a-z)\n" +
                                    "- Angka (0-9)\n" +
                                    "- Simbol (!@#$%^&*-+)" + RESET);
                continue;
            }

            System.out.print("Konfirmasi password: ");
            String konfirmasipw = input.nextLine().trim(); // Membaca konfirmasi password
            if (!password.equals(konfirmasipw)) { // Validasi password harus cocok
                System.out.println(RED + "Password tidak cocok. Silakan coba lagi." + RESET);
                continue;
            }

            System.out.println(GREEN + "\nAkun berhasil dibuat! Silahkan login.\n" + RESET);
            break; // Keluar dari loop registrasi
        }
    }

    
    // Fungsi untuk memvalidasi apakah password memenuhi syarat
    private static boolean PasswordBenar(String password) {
        // Minimal panjang 8 karakter
        if (password.length() < 8) return false;

        // Harus mengandung huruf besar
        if (!password.matches(".*[A-Z].*")) return false;

        // Harus mengandung huruf kecil
        if (!password.matches(".*[a-z].*")) return false;
        
        // Hasrus mengandung angka
        if (!password.matches(".*\\d.*")) return false;

        // Harus mengandung simbol khusus
        if (!password.matches(".*[!@#$%^&+=].*")) return false;

        return true; // Password valid
    }
    

    public static boolean login() {
        // Proses login pengguna
        System.out.println("-------------------------------------------");
        System.out.println("---              L O G I N              ---");
        System.out.println("-------------------------------------------");


        while (true) {
            System.out.print("Username: ");
            String masukkanusername = input.nextLine().trim(); // Membaca input username
            if (masukkanusername.isEmpty()) { // Validasi username tidak boleh kosong
                System.out.println(RED + "Username tidak boleh kosong. Silahkan coba lagi!" + RESET);
                continue;
            }

            System.out.print("Password: ");
            String masukkanpw = input.nextLine().trim(); // Membaca input password

            if (masukkanusername.equals(username) && masukkanpw.equals(password)) {
                // Jika username dan password sesuai
                System.out.println(GREEN + "\nLogin berhasil!\n" + RESET);
                System.out.println(BLUE + "Selamat datang di Aplikasi Pengelolaan Keuangan" + RESET);
                return true;
            } else {
                // Jika username atau password salah
                System.out.println(RED + "Username atau password salah!\n" + RESET);
                System.out.println(RED + "Silahkan coba lagi!\n" + RESET);
            }
        }
    }

    public static void tunjukanOpsi() {
        // Menampilkan daftar menu pilihan kepada pengguna
        System.out.println("\n.:: APLIKASI PENGELOLAAN KEUANGAN PRIBADI ::.");
        System.out.println("1. Tambah Pendapatan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Lihat Ringkasan Keuangan");
        System.out.println("4. Lihat Riwayat Transaksi");
        System.out.println("5. Keluar\n");
        System.out.print("Pilih opsi: ");
    }

    public static void tambahPendapatan() {
        // Menambahkan pendapatan ke saldo dan mencatat ke riwayat transaksi
        System.out.print("Masukkan jumlah pendapatan: ");
        double pendapatan = input.nextDouble(); // Membaca jumlah pendapatan dari pengguna
        saldo += pendapatan; // Menambahkan pendapatan ke saldo
        totalPendapatan += pendapatan; // Menambahkan ke total pendapatan

        input.nextLine(); // Membersihkan buffer input
        System.out.print("Pendapatan apa nih? (cth: gaji, transferan, hadiah, dll): ");
        String keterangan = input.nextLine(); // Membaca keterangan pendapatan

        simpanRiwayat("Pendapatan", pendapatan, keterangan); // Menyimpan transaksi ke riwayat
        System.out.println(GREEN + "\nYeay... Pendapatan berhasil ditambahkan!" + RESET);
    }

    public static void tambahPengeluaran() {
        // Menambahkan pengeluaran jika saldo mencukupi, dan mencatat ke riwayat
        System.out.print("Masukkan jumlah pengeluaran: ");
        double pengeluaran = input.nextDouble(); // Membaca jumlah pengeluaran dari pengguna

        if (saldo >= pengeluaran) {
            // Jika saldo cukup, pengeluaran diproses
            saldo -= pengeluaran; // Mengurangi saldo
            totalPengeluaran += pengeluaran; // Menambahkan ke total pengeluaran

            input.nextLine(); // Memberiskan buffer input
            System.out.print("Hmm... Pengeluaran apa ini sobat? (cth: makan, transportasi, shopping, dll): ");
            String keterangan = input.nextLine(); // Membaca keterangan pengeluaran

            simpanRiwayat("Pengeluaran", pengeluaran, keterangan); // Menyimpan transasi ke riwayat
            System.out.println(GREEN + "\nPengeluaran berhasil ditambahkan!" + RESET);
        } else {
            // Jika saldo tidak mencukupi
            System.out.println(RED + "Saldo anda tidak mencukupi\n" + RESET);
        }
    }

    public static void lihatRingkasan() {
        // Menampilkan ringkasan keuangan (total pendapatan, total pengeluaran, dan saldo saat ini)
        System.out.println("\n=== Ringkasan Keuangan ===");
        System.out.printf("Total Pendapatan: " + GREEN + "Rp%.2f\n" + RESET, totalPendapatan);
        System.out.printf("Total Pengeluaran: " + RED + "Rp%.2f\n" + RESET, totalPengeluaran);
        System.out.printf("Saldo Saat Ini: " + YELLOW + "Rp%.2f\n" + RESET, saldo);
    }

    public static void lihatRiwayat() {
        // Menampilkan riwayat transaksi yang telah tercatat
        if (jumlahTransaksi == 0) {
            // Jika belum ada transaksi
            System.out.println(RED + "Belum ada transaksi yang tercatat." + RESET);
        } else {
            // Header tabel riwayat transaksi
            System.out.print("""
                ╔════════════════════════════════════════════════════════════════╗
                ║                       RIWAYAT TRANSAKSI                        ║
                ╠═════╦══════════════╦════════════════════╦══════════════════════╣
                ║ No. ║    Jenis     ║       Jumlah       ║      Keterangan      ║
                ╠═════╬══════════════╬════════════════════╬══════════════════════╣
                """);


            // Menampilkan setiap transaksi
            for (int i = 0; i < jumlahTransaksi; i++) {
                System.out.printf("║ %-3d ║ %-12s ║ Rp%-16s ║ %-20s ║\n",
                        (i + 1), riwayat[i][0], riwayat[i][1], riwayat[i][2]);
                if (i < jumlahTransaksi - 1) {
                    System.out.println("╠═════╬══════════════╬════════════════════╬══════════════════════╣");
                }
            }

            // Footer tabel riwayat transaksi
            System.out.println("╚═════╩══════════════╩════════════════════╩══════════════════════╝");
        }
    }

    private static void simpanRiwayat(String jenis, double jumlah, String keterangan) {
        // Menyimpan transaksi ke array riwayat jika belum mencapai batas maksimum
        if (jumlahTransaksi < maks_transaksi) {
            riwayat[jumlahTransaksi][0] = jenis; // Menyimpan jenis transaksi (Pendapatan/Pengeluaran)
            riwayat[jumlahTransaksi][1] = String.format("%.2f", jumlah); // Menyimpan jumlah transaksi
            riwayat[jumlahTransaksi][2] = keterangan; // Menyimpan keterangan transaksi
            jumlahTransaksi++; // Menambah jumlah transaksi
        } else {
            // Jika riwayat sudah penuh
            System.out.println(RED + "Riwayat transaksi penuh, tidak dapat menyimpan lebih banyak data." + RESET);
        }
    }
}