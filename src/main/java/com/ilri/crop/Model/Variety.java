package com.ilri.crop.Model;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variety {

    @Id
    private String id;
    private String cropName;
    private String subType;
    private String varietyName;
    private String seedRate;
    private BasicDBObject agronomicChar;
    private String yearRelease;
    private String yearRegistration;
    private String breeder;



}
