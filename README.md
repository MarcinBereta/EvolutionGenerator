# Evolution Generator

Projekt wykonany na Programowanie Obiektowe. W folderze CSVFiles zapisywane są po wciśniejciu przycisku "Export Current Chart Data". Pliki tam zawierają różne informacje na temat symulacji w poszczególnych jej dniach.
Z kolei w folderze ConfigFiles znajdują się pliki konfiguracyjne, które użytkownik sam może stworzyć. W zależności od wyborów symulacja przebiegnie w inny sposób.

## Ważna informacja!
Czasem do uruchomienia app należy wejść w Run -> Edit Configuration, tam wybrać Java 17 (dokładniej Java 17.0.04), a  następnie wejść w Modify Options -> Add VM options i dodać tam następującą linijkę: 
* --module-path "\path\to\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml

gdzie : "\path\to\javafx-sdk-19\lib" jest ścieżką do folderu lib w Javafx-sdk-19 w systemie użytkownika


### Możliwe wybory w konfiguracji 

* Map height -> wysokość mapy,
* Map width -> Szerokość mapy,
* Map variant -> Wariant mapy (Earth, Hell),
* Starting grass -> Początkowa ilość trawy,
* Plant profit -> Ilość energii dostaraczana przez trawę,
* Grass growth rate -> dzienna ilość nowej trawy,
* Grass variant -> Wariant trawy (Toxic, Equator),
* Starting animals -> Startowa ilość zwierząt,
* Starting energy -> Startowa ilość energii u zwierząt,
* Required copulation energy -> Potrzebna energia do rozmnażania,
* Reproduction cost -> koszt rozmnażania,
* Mutation variant -> Wariant mutacji (Fullrandom, Smallcorrection),
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
* Export Current Chart Data -> Zapisz dane do pliku .csv
