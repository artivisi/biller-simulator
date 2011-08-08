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
Caranya gampang,

1. Fork repository ini menjadi repository Anda sendiri
2. Clone ke local untuk diedit
3. Editlah sesuka hati
4. Commit dan push ke repository Anda sendiri
5. Kirim pull request ke saya supaya bisa saya merge ke repository saya

