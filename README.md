Potrzebuje od Ciebie abys mi pokazal jak kodujesz w srodowisku korporacyjnym.
Mini projekt ktory bedziesz musial tu wykonac bedzie musial byc zrobiony w gicie
i fajnie jakbys podzielil go na male zadania, w taki sposob aby glowny branch w gicie (master) byl finalnym studium kodu
ale zebym z historii mogl ogarnac jakies taski jakie robiles i zeby byly jakies merge - requesty do gita (nie musisz robic review)
feature branche maja sie nazywac wg wzrou: feature/TASK-00XX gdzie XX to kolejny numer zadania a w commit message musi byc opis po angielsku
co robiles w danym zadaniu. Calosc na koncu musi byc zdeployowana na HEROKU tak aby dalo sie cos poklikac. (czyli bedzie potrzebny jakis wystawiony swagger do testow)

Na czym polegac ma zadanie?
Bardzo prostu CRUD.
Wyobraz sobie ze prowadzisz firme sprzatajaca, gdzie przechowujesz dane o swoich pracownikach (imie, nazwisko, pesel, data zatrudnienia, telefon, email, miasto, max odleglosc dojazdu)
oraz masz jakis swoich klientow (nazwa klienta, adres, miasto, powierzchnia, cena za 1m2)
i chcesz po prostu przypisywac pracownikow do sprzatania powierzchni swoich klientow,
kazde przypisanie posiada date od, do, pracownika i klienta, przypisania nie moga nachodzic na siebie datami, tzn: pracownik jak jest przypisany do firmy A w okresie X-Y, to
w okresie X-Y nie moze byc przypisany nigdzie indziej. dodatkowo staramy sie dopasowac klientow, do pracownikow w ramach tego samego miasta,
CHYBA ZE, pracownik wyrazil zgode na dojazdy np: 30km od danego miasta, wtedy nalezy sie zintegrowac z jakims serwisem prostym so pozwoli wyciagnac odleglosc miedzy miastami
i jesli odleglosc jest mniejsza lub rowna max odleglosci dojazdu pracownika - przypisanie moze dojsc do skutku.
Klienta moze spotkac na raz kilku pracownikow, ale przyjmijmy ze nie wiecej niz 1 pracownik na kazde 200m2 powierzchni klienta, tzn: jak klient ma 1000m2 to max moze mu sprzetac 5ciu pracownikow.


Chce ladne rest api na
- CRUD pracownikow (z paginacja)
- CRUD klientow (z paginacja)
- CRUD przypisan (z paginacja)

dobre praktyki, testy jednostkowe, integracyjne, itp.
Co nie jasne: komunikujesz sie ;)


Czas: 3 tygodnie.
