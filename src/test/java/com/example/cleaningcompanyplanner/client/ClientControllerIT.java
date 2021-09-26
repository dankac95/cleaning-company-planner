package com.example.cleaningcompanyplanner.client;

public class ClientControllerIT {
    // MockMVC == robimy calle REST

    // Po zalozeniu klienta, moge go pobrac
    // Po zalozeniu klienta robimy getList (z sortowaniem id,DESC) i zakladamy ze dodany client jest na poczatku listy
    // Nie moge zalozyc klienta ze wzgledu na bledy walidacji (celowy bledny request + weryfikacja response)
    // Proba pobrania client o blednym ID
    // Aktualizacja clienta - przyupadek pozytywny - sprawdzamy czy zmienione pola sa odzwierciedlowe w GET
    // Aktualizacja - blad walidacji
    // Aktualizacja - bledne ID (404)
    // Usuniecie - przypadek pozytywny (getOne zwroci 404)
    // Usuniecie - bledne ID
}
