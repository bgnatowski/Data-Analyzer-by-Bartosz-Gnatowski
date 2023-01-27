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

* Import danych wyeksportowanych z analizatorów sieci elektroenergetycznych firma:
  * Sonel – dane standardowe dla sieci i dane harmonicznych zawartości wraz z THD, w osobnych plikach „.csv” eksportowanych z programu Sonel Analiza wersja 4.6.x (min. wersja 4.6.0). Możliwa obsługa w języku angielskim i polskim.
  * A-Eberle – dane standardowe dla sieci i dane zawartości harmonicznych wraz z THD, w osobnych plikach „.csv” eksportowanych z programu WinPQ mobil (wersja 6.1.2.1). Możliwa obsługa w języku angielskim. WAŻNE. Przy eksporcie z WinPQ mobil zaznaczyć opcję *"Suppress Headers"*.
* Podgląd wyżej wymienione dane z zapisem do pliku tekstowego w różnych formatach liczbowych (tj. format zmiennoprzecinkowy oraz naukowy).
* Prosty i zysk kreator wykresów liniowych z zaimportowanych danych wraz z ich zapisaniem.
* Generator w formacie „. docx” w języku polskim zawiera ocenę zgodności parametrów w zestawieniu o Rozporządzenia Ministra Gospodarki z dnia 4 maja 2007 r. w sprawie wpływu warunków funkcjonowania systemu elektroenergetycznego, Dziennik Ustaw Nr 93, poz. 623.

Aby wygenerować raport ważny, oba typy danych zostały zaimportowane. Dodatkowo w ich skład wchodzi minimum:
* Uśrednione wartości skuteczne napięć fazowych (każdej z faz: L1 , L2 , L3 ),
* Współczynnik asymetrii mocy składowej przeciwnej,
* Współczynnik uciążliwości migotania światła P_lt dla każdej z 3 faz (zbierany przez analizator Sonel, wyliczany ze współczynnika P_st dla analizatorów firmy A-Eberle),
* Sprawdź współczynnik zawartości harmonicznych THD dla każdej z 3 faz,
* Sprawdź poziom każdej z harmonicznych mocy (min. do 40) dla każdej z 3 faz (SonelAnaliza — export danych w %, WinPQ — export danych w V).

W przypadku wystąpienia raportu będzie niepełny, zostanie wygenerowany nieprawidłowo lub nie będzie możliwe wygenerowanie i zapisanie raportu.

Dodatkowo:
* Opcja przeciągnij i upuść pliki csv w obrębie listy, aby zaimportować.
* Podgląd danych możliwy w osobnym oknie poprzez kliknięcie menu prawym przyciskiem myszy po czym "otwórz w nowym oknie".
* Możliwość zapisu do csv w innym formacie reprezentacji liczb (naukowy, zmiennoprzecinkowy) poprzez przycisk "zapisz jako..." w menu podglądu danych. 

## Licencja

[APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Ikony aplikacji użyte i nieedytowane zgodnie z licencjami:
* small-n-flat icon pack by Paomedia (https://www.iconfinder.com/iconsets/small-n-flat)
na licencji [Creative Commons (Attribution 3.0 Unported) (CC BY 3.0)](https://creativecommons.org/licenses/by/3.0/)
* exit icon by Alessandro Rei (https://www.iconfinder.com/icons/6035/exit_icon#) na licencji [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)

## Instalacja

Aplikacje można pobrać pod linkiem:

* https://github.com/bgnatowski/Data-Analyzer-by-Bartosz-Gnatowski/releases

Są dwie opcje:

* Uruchomić plik wykonywalny *DataAnalyzer_setup.exe*. Włączy on pakiet instalacyjny, który przeprowadzi przez proces instalacji.
Plik instalacyjny może wzbudzić alert aplikacji antywirusowej, jednakże po przeskanowaniu alert znika. Uruchom zainstalowaną aplikację.

lub

* Po pobraniu pliku *.jar* uruchomić za pomocą komendy:
```
java -jar DataAnalyzer_v1.1.jar
```
z folderu gdzie wypakowano plik.\
Konieczne jest posiadanie JRE 1.7+. (dołączone do JDK 17+, możliwość pobrania na https://www.oracle.com/pl/java/technologies/downloads/#java17)



### EN

Data Analyzer is an application as part of an engineering project at the AGH University of Science and Technology in Cracow.

**Title:**  
PL: *Aplikacja do obsługi danych pomiarowych w oparciu o JavaFX*\
EN:	*Application for management of measurement data based on JavaFX.*\
**Author:**\
Bartosz Gnatowski\
**Supervisor:**\
dr inż. Krzysztof Piątek\
**Organizational unit:**\
Faculty of Electrical Engineering, Automatics, Computer Science and Biomedical Engineering

## Features

* Import of data exported from power network analyzers of companies:
  * a)	Sonel - standard data for the network and harmonic content data with THD, in separate ".csv" files exported from the Sonel Analysis program version 4.6.x (at least version 4.6.0). Possible import in English and Polish.
  * b)	A-Eberle – standard network data and harmonic content data with THD, in separate ".csv" files exported from WinPQ mobil (version 6.1.2.1). Possible import in English. IMPORTANT. When exporting from WinPQ mobil, check the *"Suppress Headers "* option.
* Preview of the above-mentioned data with saving to a text file in various formats of representation of numbers (i.e. floating point and scientific format).
* A simple and functional creator of line charts from imported data with the option of saving them.
* Report generator in ".docx" in Polish, containing the assessment of compliance of parameters based on the Regulation of the Minister of Economy of May 4, 2007, on detailed conditions for the operation of the power system, Journal of Laws No. 93, item 623.


To generate the report it is important that both types of data have been imported. Additionally, they should include at least:
* Average RMS values of phase voltages (each phase: L1 , L2 , L3 ),
* Native sequence voltage unbalance factor,
* Light flicker nuisance coefficient P_lt for each of the 3 phases (collected by Sonel analyzers, calculated from the coefficient P_st for A-Eberle analyzers),
* The value of the harmonic distortion factor THD for each of the 3 phases,
* Value of the level of each of the voltage harmonics (min. up to 40) for each of the 3 phases (SonelAnalysis - data export in %, WinPQ - data export in V).

Extra:
* Drag and drop csv files to the list to import.
* Data can be previewed in a separate window by clicking the right mouse button in the menu and then "open in new window"
* The ability to save to csv in a different format of representation of numbers (scientific, floating point) via the "save as..." button in the data preview menu.

## License

[APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Application icons used and unedited according to the licenses:
* small-n-flat icon pack by Paomedia (https://www.iconfinder.com/iconsets/small-n-flat)
  na licencji [Creative Commons (Attribution 3.0 Unported) (CC BY 3.0)](https://creativecommons.org/licenses/by/3.0/)
* exit icon by Alessandro Rei (https://www.iconfinder.com/icons/6035/exit_icon#) na licencji [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)


## Installation

The applications can be downloaded from:

* https://github.com/bgnatowski/Data-Analyzer-by-Bartosz-Gnatowski/releases

You have two ways:

* Run the executable *DataAnalyzer_setup.exe* file. It will enable the installation package, which will guide you through the installation process.
The installation file may raise an antivirus application alert, however, after scanning the alert disappears.

or 

* After you download the jar file, you can run it using
```
java -jar DataAnalyzer_v1.1.jar
```
from the directory you uncompressed it to.\
You have to have JRE 1.7+. (bundled with JDK 17+, download at https://www.oracle.com/pl/java/technologies/downloads/#java17)
