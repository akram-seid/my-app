package com.ilri.crop.Repository;

import com.ilri.crop.Model.Variety;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VarietyRepository extends MongoRepository<Variety, String> {
@Query(value = "{'agronomicChar.adaptationArea.altitude.min':{$gte: ?0}}")
    List<Variety> findByMinAltitude(int i);
}
