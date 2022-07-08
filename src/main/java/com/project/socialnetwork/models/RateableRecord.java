package com.project.socialnetwork.models;

import com.project.socialnetwork.logic_classes.json_converter.JsonConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class RateableRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String text;
    private Date date;
    private String jsonImagesNames;
    @Formula("(select count(*) from likes where rated_record_id = id)")
    private Long likesCount;
    @Formula("(select count(*) from dislikes where rated_record_id = id)")
    private Long dislikeCounts;
    @Transient
    private List<String> imagesNames = new ArrayList<>();
    @ManyToOne
    @JoinColumn
    private User user;
    @OneToMany(mappedBy = "ratedRecord")
    private Set<RecordRating> ratings;


    public void convertJsonToImagesNamesList(){
        imagesNames = JsonConverter.convertJsonToObject(jsonImagesNames);
    }

    public void convertImagesNamesListToJson(){
        jsonImagesNames = JsonConverter.convertObjectToJson(imagesNames);
    }
}
