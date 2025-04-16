Laboratorium III - JPQL

Uwaga! Do wykonania zadan konieczne jest zaimplementowanie architektury warstwowej i testow z Laboratorium II !

Uzupelnij plik data.sql o dane niezbedne do realizacji nastepujacych zapytan:
1. Znajdz pacjentow po nazwisku
2. Znajdz wszystkie wizyty pacjenta po jego ID
3. znajdz pacjentow ktorzy mieli wiecej niz X wizyt (X jest parametrem wejsciowym)
4. Znajdz pacjentow po dodanym przez Ciebie polu - nie wyszukuj wprost po wartosci, uzyj zapytania typu wieksze/mniejsze/pozniej/wczesniej/zawiera, w zaleznosci od wybranego typu zmiennej.

Napisz testy do zapytan w nastepujacej formie:
1. do zapytania nr 1  - test DAO
2. do zapytania nr 2 - test serwisu
3. do zapytania nr 3 - test DAO
4. do zapytania nr 4 - test DAO

W PatientEntity, nad relacja do VisitEntity dodaj adnotacje

@Fetch(FetchMode.SELECT)

a fetchType zmien na EAGER
Uruchom test w ktorym pobierany jest Patient z wieloma wizytami. W logach zaobserwuj, jak wyglada pobieranie dodatkowych encji (ile i jakie sqle).
Nastepnie zmien adnotacje na

@Fetch(FetchMode.JOIN)

i powtorz test i obserwacje. Wnioski zapisz na dole tego pliku i skomituj.

Do wybranej encji dodaj wersjonowanie, oraz napisz test (w DAO) sprawdzajacy rownolegla modyfikacje (OptimisticLock)




1. Dla SELECT:

Pierwsze zapytanie SQL pobiera informacje o pacjencie wraz z jego adresem i lekarzem (poprzez join)


    select pe1_0.id, pe1_0.address_id, a1_0.id, a1_0.address_line1, ...
    from patient pe1_0
    join address a1_0 on a1_0.id=pe1_0.address_id
    left join doctor d1_0 on a1_0.id=d1_0.address_id
    where pe1_0.id=?


Oddzielne zapytanie SQL jest wykonywane w celu pobrania wszystkich wizyt dla pacjenta

    
    select v1_0.patient_id, v1_0.id, v1_0.description, v1_0.doctor_id, v1_0.time
    from visit v1_0
    where v1_0.patient_id=?

Charakterystyka zachowania:

To demonstruje problem "N+1", gdzie:

Pierwsze zapytanie jest wykonywane w celu uzyskania pacjenta
Dodatkowe zapytanie jest wykonywane w celu uzyskania wszystkich wizyt dla tego pacjenta

Wizyty są ładowane w oddzielnej instrukcji SELECT po załadowaniu pacjenta

2. Dla JOIN:

Wykonywane jest tylko jedno zapytanie w celu pobrania pacjenta wraz ze wszystkimi wizytami:

    select pe1_0.id, pe1_0.address_id, ..., v1_0.patient_id, v1_0.id, v1_0.description, v1_0.doctor_id, v1_0.time
    from patient pe1_0
    join address a1_0 on a1_0.id=pe1_0.address_id
    left join doctor d1_0 on a1_0.id=d1_0.address_id
    left join visit v1_0 on pe1_0.id=v1_0.patient_id
    where pe1_0.id=?

Zapytanie wykorzystuje LEFT JOIN, aby uwzględnić wszystkie wizyty pacjenta
Wszystkie powiązane dane (pacjent, adres, lekarz i wizyty) są pobierane w jednym zapytaniu


Wnioski: SELECT jest metodą bardziej wydajną, ponieważ wymaga tylko jednego zapytania do pobrania wszystkich potrzebnych informacji