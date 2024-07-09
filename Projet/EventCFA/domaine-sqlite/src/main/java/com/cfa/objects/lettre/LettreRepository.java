package com.cfa.objects.lettre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface LettreRepository extends JpaRepository<Lettre, Integer> {

    Lettre findById(int id);
    Collection<Lettre> findByCreationDate(Date creationDate);
    Collection<Lettre> findByTreatmentDate(Date treatmentDate);

}
