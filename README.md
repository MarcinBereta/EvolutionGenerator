# Evolution Generator

Projekt wykonany na Programowanie Obiektowe. W folderze CSVFiles zapisywane są po wciśniejciu przycisku "Export Current Chart Data". Pliki tam zawierają różne informacje na temat symulacji w poszczególnych jej dniach.
Z kolei w folderze ConfigFiles znajdują się pliki konfiguracyjne, które użytkownik sam może stworzyć. W zależności od wyborów symulacja przebiegnie w inny sposób.

### Możliwe wybory w konfiguracji 

* Map height -> wysokość mapy,
* Map width -> Szerokość mapy,
* Map type -> Typ mapy (Earth, Hell),
* Starting grass -> Początkowa ilość trawy,
* Plant profit -> Ilość energii dostaraczana przez trawę,
* Grass growth rate -> dzienna ilość nowej trawy,
* Grass type -> Typ trawy (Toxic, Equator),
* Starting animals -> Startowa ilość zwierząt,
* Starting energy -> Startowa ilość energii u zwierząt,
* Required copulation energy -> Potrzebna energia do rozmnażania,
* Reproduction cost -> koszt rozmnażania,
* Genome type -> Typ mutacji (Fullrandom, Smallcorrection),
* Animal genome length -> Długość genów,
* Move type -> typ ruchu zwierząt (Fullpredestination, Bitofmadness),
* Day cost -> Dzienny koszt energii,
* Max gens changes -> Maksymalna ilość zmian w genach,
* Min gens Changes -> Minimalna ilość zmian w genach.

### Jak uruchomić symulację?

Aby uruchomić symulacje w już włączonej aplikacji nalezy wybrać konfigurację. Jeśli chcesz ją stworzyć musisz wprowadzić dane, zapisać a na końcu odświerzyć, aby
uzyskać dostęp do nowego pliku. Jeśli chcesz możesz też uruchomić kilka symulacji na raz i każda z nich osobno stopować, zapisywać jej dane.

## Przyciski w oknie symulacji

* Start -> Wznowienie symulacji,
* Stop -> Zatrzymanie symulacji,
* Export Data -> Zapisz dane do pliku .csv (Dostępny dopiero po zatrzymaniu symulacji)

## Oprogramowanie
Aplikacja oparta na języku Java a interface zbudowany w JavaFX. Wykorzystywano: Java 17 oraz JavaFX-sdk-19
