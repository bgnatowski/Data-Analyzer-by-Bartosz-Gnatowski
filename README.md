
# Data Analyzer by Bartosz Gnatowski 

### PL

Data Analyzer jest aplikacją w ramach projektu inżynierskiego na Akademii Górniczo Hutniczej w Krakowie.

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
  * A-Eberle – dane standardowe dla sieci i dane zawartości harmonicznych wraz z THD, w osobnych plikach „.csv” eksportowanych z programu WinPQ mobil (wersja 6.1.2.1). Możliwa obsługa w języku angielskim.
* Podgląd wyżej wymienione dane z zapisem do pliku tekstowego w różnych formatach liczbowych (tj. format zmiennoprzecinkowy oraz naukowy).
* Prosty i zysk kreator wykresów liniowych z zaimportowanych danych wraz z ich zapisaniem.
* Generator w formacie „. docx” w języku polskim zawiera ocenę zgodności parametrów w zestawieniu o Rozporządzenia Ministra Gospodarki z dnia 4 maja 2007 r. w sprawie wpływu warunków funkcjonowania systemu elektroenergetycznego, Dziennik Ustaw Nr 93, poz. 623.

Aby wygenerować raport ważny, oba typy danych zostały zaimportowane. Dodatkowo w ich skład wchodzi minimum:
* Uśrednione wartości skuteczne napięć fazowych (każdej z faz: L1 , L2 , L3 ),
* Współczynnik asymetrii mocy składowej przeciwnej,
* Współczynnik uciążliwości migotania światła P_lt dla każdej z 3 faz (zbierany przez analizator Sonel, wyliczany ze współczynnika P_st dla analizatorów firmy A-Eberle),
* Sprawdź współczynnik zawartości harmonicznych THD dla każdej z 3 faz,
* Sprawdź poziom każdej z harmonicznych mocy (min. do 40) dla każdej z 3 faz.
  W przypadku wystąpienia raportu będzie niepełny, zostanie wygenerowany nieprawidłowo lub nie będzie możliwe wygenerowanie i zapisanie raportu.
## Licencja

[APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)



## Instalacja

Aplikacje można zainstalować z pliku instalacyjnego do pobrania pod linkiem:
* https://tinyurl.com/DataAnalyzerInstaller

Uruchomić plik *DataAnalyzer_setup.exe*. Włączy on pakiet instalacyjny, który przeprowadzi przez proces instalacji.
Plik instalacyjny może wzbudzić alert aplikacji antywirusowej, jednakże po przeskanowaniu alert powienin zniknąc. 


### PL