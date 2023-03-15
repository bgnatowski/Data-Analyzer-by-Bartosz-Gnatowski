# Data Analyzer by Bartosz Gnatowski 

### PL

Data Analyzer jest aplikacją w ramach projektu inżynierskiego na Akademii Górniczo-Hutniczej w Krakowie.

**Tytuł:**  
PL: *Aplikacja do obsługi danych pomiarowych w oparciu o JavaFX*\
EN:	*Application for management of measurement data based on JavaFX.*\
**Autor:**\
Bartosz Gnatowski\
**Promotor pracy:**\
dr inż. Krzysztof Piątek\
**Jednostka organizacyjna:**\
Wydział Elektrotechniki, Automatyki, Informatyki i Inżynierii Biomedycznej

## Cechy

* Import danych standardowych i danych wyższych harmonicznych wyeksportowanych w plikach ".csv" z analizatorów sieci elektroenergetycznych firm:
  * Sonel – eksport z programu *Sonel Analiza* wersja *4.6.x* (min. wersja 4.6.0). Możliwa obsługa w języku angielskim i polskim.
  * A-Eberle – eksport z programu *WinPQ mobil* wersja *6.1.2.1*. Możliwa obsługa w języku angielskim.\
  **WAŻNE**. Przy eksporcie z *WinPQ mobil* zaznaczyć opcję "Bez nagłówka" (*"Suppress Headers"*).
* Podgląd zaimportowanych danych wraz z zapisem do pliku tekstowego w formacie zmiennoprzecinkowym i naukowym.
* Prosty i użyteczny kreator wykresów liniowych umożliwiający ich zapis do plików ".png".
* Generator raportu umożliwiający automatyczne utworzenie dokumentu w języku polskim w formacie „.docx” zawierającego ocenę zgodności parametrów zgodnie z Rozporządzeniem Ministra Gospodarki z dnia 4 maja 2007 r. w sprawie wpływu warunków funkcjonowania systemu elektroenergetycznego, Dziennik Ustaw Nr 93, poz. 623.

Aby wygenerować pełny raport ważne jest, aby oba typy danych zostały zaimportowane. Dodatkowo w ich skład muszą wchodzić parametry:
* Uśrednione wartości skuteczne napięć fazowych (każdej z faz: L1 , L2 , L3),
* Współczynnik asymetrii mocy składowej przeciwnej,
* Współczynnik uciążliwości migotania światła Plt dla każdej z 3 faz (zbierany przez analizator Sonel, wyliczany ze współczynnika Pst dla analizatorów firmy A-Eberle),
* Współczynnik zawartości harmonicznych THD dla każdej z 3 faz,
* Wartość poziomu każdej z harmonicznych napięcia (min. do 25) dla każdej z 3 faz (SonelAnaliza — export danych w %, WinPQ — export danych w V).

W przypadku braku któregoś z parametru wygenerowany raport będzie niepełny.

Dodatkowo:
* Opcja przeciągnij i upuść pliki csv w obrębie listy, aby zaimportować.
* Podgląd danych możliwy w osobnym oknie poprzez kliknięcie menu "Podgląd danych" prawym przyciskiem myszy, po czym wybranie opcji "Otwórz w nowym oknie".
* Okno statystyk całkowitych możliwe do włączenia poprzez kliknięcie menu "Podgląd danych" prawym przyciskiem myszy, po czym wybranie opcji "Otwórz okno statystyk".
* Możliwość zapisu do csv w innym formacie reprezentacji liczb (naukowy, zmiennoprzecinkowy) poprzez przycisk "zapisz jako..." w menu podglądu danych.

Poprawki w przyszłej wersji do korekty (nie wpływa na ogólną funkcjonalność):
* mieszanie kolorów serii przy przełączaniu pomiędzy wykresami;
* w momencie, gdy jest więcej niż jedna pusta lista serii w kreatorze (zakładka Oś X), utworzenie wykresu następuje dopiero po wybraniu serii w każdej z nich. 

## Licencja

[APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Ikony aplikacji użyte i nieedytowane zgodnie z licencjami:
* small-n-flat icon pack by Paomedia (https://www.iconfinder.com/iconsets/small-n-flat)
na licencji [Creative Commons (Attribution 3.0 Unported) (CC BY 3.0)](https://creativecommons.org/licenses/by/3.0/)
* exit icon by Alessandro Rei (https://www.iconfinder.com/icons/6035/exit_icon#) na licencji [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)

## Biblioteki i narzędzia

* JavaFX
* SpringBoot
* Project Lombok
* OpenCSV
* Poi-tl

## Instalacja

Aplikacje można pobrać pod linkiem:

* https://github.com/bgnatowski/Data-Analyzer-by-Bartosz-Gnatowski/releases

Są dwie opcje:

* Uruchomić plik wykonywalny *DataAnalyzer_setup.exe*. Włączy on pakiet instalacyjny, który przeprowadzi przez proces instalacji.
Plik instalacyjny może wzbudzić alert aplikacji antywirusowej, jednakże po przeskanowaniu alert znika. Uruchom zainstalowaną aplikację.

lub

* Po pobraniu pliku *.jar* uruchomić za pomocą komendy:
```
java -jar DataAnalyzer_v2.2.jar
```
z folderu gdzie wypakowano plik.\
Konieczne jest posiadanie JRE 1.7+. (dołączone do JDK 17+, możliwość pobrania na https://www.oracle.com/pl/java/technologies/downloads/#java17)


### EN

Data Analyzer is an application as part of an engineering project at the AGH University of Science and Technology in Cracow.

**Title:**  
PL: *Aplikacja do obsługi danych pomiarowych w oparciu o JavaFX*\
EN: *Application for management of measurement data based on JavaFX.*\
**Author:**\
Bartosz Gnatowski\
**Supervisor:**\
dr inż. Krzysztof Piątek\
**Organizational unit:**\
Faculty of Electrical Engineering, Automatics, Computer Science and Biomedical Engineering

## Features

* Import of standard and higher harmonic data exported in ".csv" files from power network analyzers of the companies:
  * a)	Sonel - export from the program *Sonel Analysis* version *4.6.x* (min. version 4.6.0). Possible import in English and Polish.
  * b)	A-Eberle – export from *WinPQ mobil* program version *6.1.2.1*. Possible import in English. **IMPORTANT**. When exporting from *WinPQ mobil*, check the option *"Suppress Headers"*.
* Preview imported data with saving to text file in floating point and scientific format.
* A simple and useful line chart creator allowing saving charts to ".png" .
* A report generator that allows automatic creation of a document in Polish in ".docx" format containing an assessment of parameter compliance in accordance with the "Rozporządzenie Ministra Gospodarki z dnia 4 maja 2007 r. w sprawie wpływu warunków funkcjonowania systemu elektroenergetycznego, Dziennik Ustaw Nr 93, poz. 623"

To generate a full report, it is important that both types of data are imported. In addition, they must include parameters:
* Average RMS values of phase voltages (each phase: L1 , L2 , L3),
* Power asymmetry factor of the opposite component,
* Flicker Plt for each of the 3 phases (collected by Sonel analyzer, calculated from Pst factor for A-Eberle analyzers),
* The value of the harmonic distortion factor THD for each of the 3 phases,
* Value of the level of each of the voltage harmonics (min. up to 25) for each of the 3 phases (SonelAnalysis - data export in %, WinPQ - data export in V).

Extra:
* Drag and drop csv files to the list to import. 
* Data can be previewed in a separate window by clicking the "Data Preview" menu with the right mouse button and then selecting the "Open in new window" option.
* Total statistics window can be enabled by right-clicking the "Data View" menu and selecting "Open Statistics".
* The ability to save to ".csv" in a different format of representation of numbers (scientific, floating point) via the "save as..." button in the data preview menu.

Fixes in a future version to be corrected (does not affect overall functionality):
* mixing series colors when switching between charts;
* when there is more than one empty series list in the wizard (X axis tab), the chart is created only after selecting a series in each of them.

## License

[APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Application icons used and unedited according to the licenses:
* small-n-flat icon pack by Paomedia (https://www.iconfinder.com/iconsets/small-n-flat)
  na licencji [Creative Commons (Attribution 3.0 Unported) (CC BY 3.0)](https://creativecommons.org/licenses/by/3.0/)
* exit icon by Alessandro Rei (https://www.iconfinder.com/icons/6035/exit_icon#) na licencji [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)

## Technologies and libraries

* JavaFX
* SpringBoot
* Project Lombok
* OpenCSV
* Poi-tl

## Installation

The applications can be downloaded from:

* https://github.com/bgnatowski/Data-Analyzer-by-Bartosz-Gnatowski/releases

You have two ways:

* Run the executable *DataAnalyzer_setup.exe* file. It will enable the installation package, which will guide you through the installation process.
The installation file may raise an antivirus application alert, however, after scanning the alert disappears.

or 

* After you download the jar file, you can run it using
```
java -jar DataAnalyzer_v2.2.jar
```
from the directory you uncompressed it to.\
You have to have JRE 1.7+. (bundled with JDK 17+, download at https://www.oracle.com/pl/java/technologies/downloads/#java17)
