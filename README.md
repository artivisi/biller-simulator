Biller Simulator
================


Deskripsi
----------------

Aplikasi ini merupakan simulator yang digunakan di ArtiVisi untuk melakukan tes terhadap aplikasi payment gateway kami.
Fitur aplikasi ini :

*  manajemen pelanggan
*  manajemen dummy data tagihan
*  Gateway [ISO-8583](http://en.wikipedia.org/wiki/ISO_8583) untuk melakukan pembayaran tagihan untuk pelanggan yang ada di database.


Teknologi yang digunakan
------------------------

*  Java SDK 1.6
*  Spring Framework 3.0.5
*  Hibernate 3.6.0
*  MySQL 5.1
*  Jetty 6.1.26
*  Maven 2.2.1
*  jPOS 1.7.0

Cara menyiapkan database
-----------------------------
Aplikasi ini menggunakan Hibernate, sehingga secara teoritis mendukung [semua database yang didukung oleh Hibernate](http://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/session-configuration.html#configuration-optional-dialects). Walaupun demikian, development dan test dilakukan menggunakan MySQL.

Konfigurasi koneksi database dilakukan di beberapa tempat, yaitu :
*  pom.xml di root folder : digunakan untuk drop dan create database, dijalankan pada saat automated test dieksekusi
*  jdbc.properties di folder biller-simulator-config/src/main/resources : digunakan oleh aplikasi pada saat dijalankan

Untuk konfigurasi di pom.xml, berikan username MySQL yang memiliki privileges untuk drop dan create database. Biasanya saya menggunakan user root saja supaya gampang.
Untuk konfigurasi di jdbc.properties, usernamenya cukup memiliki akses ke database yang digunakan di aplikasi saja.

Berikut sintaks SQL untuk membuat username, password, dan database di MySQL. Perintah ini dijalankan dengan user root

```sql
create database ppob_simulator;
grant all on ppob_simulator.* to simulator@localhost identified by 'simulator';
```

Cara menjalankan aplikasi web
-----------------------------

Aplikasi web digunakan untuk mengelola data pelanggan dan tagihan.

1. Buka command prompt, lalu masuk ke folder tempat source code aplikasi
2. Jalankan mvn clean install
3. Masuk ke folder biller-simulator-ui-jsf
4. Jalankan mvn clean jetty:run
5. Arahkan browser ke http://localhost:8080/biller-simulator-ui-jsf/faces/index.xhtml

Cara menjalankan gateway pln
-----------------------------

Gateway PLN digunakan untuk melakukan transaksi inquiry dan payment tagihan listrik melalui protokol ISO-8583

1. Buka command prompt, lalu masuk ke folder tempat source code aplikasi
2. Jalankan mvn clean install
3. Masuk ke folder biller-simulator-gateway-pln
4. Jalankan mvn exec:java -Dexec.mainClass="com.artivisi.biller.simulator.gateway.pln.Launcher"
5. ISO-8583 gateway siap menunggu di port 11111

Cara kontribusi
---------------
Anda ingin berkontribusi? Baguslah kalau begitu.

Ada beberapa kontribusi yang bisa Anda lakukan, diantaranya :

*  Melakukan testing
*  Membuatkan dokumentasi
*  Mengedit source code, baik untuk fix bugs maupun menambah fitur

Untuk kontribusi testing, berikut caranya :

1. Jalankan aplikasinya, yang web atau yang ISO-8583 terserah.
2. Test fitur-fiturnya.
3. Kalau ada error/bug yang ditemukan, silakan langsung [membuat issue baru](https://github.com/artivisi/biller-simulator/issues/new).
Mohon mengikuti [panduan melaporkan bug](http://endy.artivisi.com/blog/java/tips-melaporkan-error/), supaya mudah ditindak lanjuti oleh programmer.
Bug report maupun request fitur boleh ditulis dalam bahasa Indonesia atau Inggris.

Untuk kontribusi dokumentasi, sementara kami akan menyiapkan dulu template dokumentasinya.
Bila sudah ada, kami akan update bagian ini dengan langkah-langkah untuk kontribusi.

Untuk kontribusi source code, berikut caranya :

1. Fork repository ini menjadi repository Anda sendiri
2. Clone ke local untuk diedit
3. Editlah sesuka hati
4. Commit dan push ke repository Anda sendiri
5. Kirim pull request ke saya supaya bisa saya merge ke repository saya

