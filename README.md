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
----------------

*  Java SDK 1.6
*  Spring Framework 3.0.5
*  Hibernate 3.6.0
*  MySQL 5.1
*  Jetty 6.1.26
*  Maven 2.2.1


Cara menggunakan
----------------

1. Download source codenya dan extract
2. Buka command prompt, lalu masuk ke folder tempat aplikasi diextract
3. Jalankan mvn clean install
4. Masuk ke folder biller-simulator-ui-jsf
5. Jalankan mvn clean jetty:run
6. Arahkan browser ke http://localhost:8080/biller-simulator-ui-jsf/faces/index.xhtml

Cara kontribusi
---------------
Anda ingin menyumbangkan tulisan? Baguslah kalau begitu.
Caranya gampang,

1. Fork repository ini menjadi repository Anda sendiri
2. Clone ke local untuk diedit
3. Editlah sesuka hati
4. Commit dan push ke repository Anda sendiri
5. Kirim pull request ke saya supaya bisa saya merge ke repository saya

