# Evolution Generator

Projekt wykonany na Programowanie Obiektowe. W folderze SimulationFiles/CSVFiles zapisywane są po wciśniejciu przycisku "Export Data". Pliki tam zawierają różne informacje na temat symulacji w poszczególnych jej dniach. 
Z kolei w folderze SimulationFiles/ConfigFiles znajdują się pliki konfiguracyjne, które użytkownik sam może stworzyć. W zależności od wyborów symulacja przebiegnie w inny sposób. 

Aplikacja uruchamiana z poziomu pliku App w src/main/java/gui/app

## Ważna informacja!
Czasem do uruchomienia App należy wejść w Run -> Edit Configuration, tam wybrać Java 17 (dokładniej Java 17.0.4), a  następnie wejść w Modify Options -> Add VM options i dodać tam następującą linijkę: 
* --module-path "\path\to\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml

gdzie : "\path\to\javafx-sdk-19\lib" jest ścieżką do folderu lib w Javafx-sdk-19 w systemie użytkownika

## Obsługa i opisy elementów

###  Możliwe wybory w konfiguracji 

* Map height -> wysokość mapy,
* Map width -> Szerokość mapy,
* Map type -> Typ mapy (Earth, Hell),
* Starting grass -> Początkowa ilość trawy,
* Plant profit -> Ilość energii dostaraczana przez trawę,
* Daily grass -> dzienna ilość nowej trawy,
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


### Przyciski w oknie symulacji

* Start -> Wznowienie symulacji,
* Stop -> Zatrzymanie symulacji,
* Export Current Chart Data -> Zapisz dane do pliku .csv

### Co podczas symulacji

Po kliknięciu na zwierzę możemy śledzić jego pozycje i informacje o nim, najlepiej robić to podczas gdy symulacja jest zastopowana. Do tego Wyświetlane są informacje o najpopularaniejszym genotypie i które zwierzę aktualnie go posiada.

### Jak uruchomić symulację?

Aby uruchomić symulacje w już włączonej aplikacji nalezy wybrać konfigurację. Jeśli chcesz ją stworzyć musisz wprowadzić dane, zapisać a na końcu odświerzyć, aby
uzyskać dostęp do nowego pliku. Jeśli chcesz możesz też uruchomić kilka symulacji na raz i każda z nich osobno stopować, zapisywać jej dane.


