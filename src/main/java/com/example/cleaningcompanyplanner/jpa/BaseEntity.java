package com.example.cleaningcompanyplanner.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // TODO-purban: 1. JsonIgnore bylby OK ale na DTO, gdzie masz warstwe DTO?
    //  Nie powinno zwracac encji z controllerow VVV
    private String uuid = UUID.randomUUID().toString();

    // TODO-purban: Jezeli juz masz ID + UUID, to na zewnatrz wystawiasz UUID a nie ID (wystawiasz to w zwracacnych
    //  DTOsach, tak samo na wejsciu kazdego endpointu zamiast ID przyjmujesz UUID)
}
