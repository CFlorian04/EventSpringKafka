package com.cfa.objects.lettre;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "lettre")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Lettre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "treatmentDate")
    private Date treatmentDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lettre lettre = (Lettre) o;
        return this.id != null && Objects.equals(this.id, lettre.id);

    }

}
