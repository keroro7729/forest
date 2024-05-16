package com.forest.forest_server.Record;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.StringTokenizer;

@Getter
@Setter
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Integer quizCat;
    private String timeStamp;
    private Boolean correct;
    private String quizData;

    private transient Long imgId = (long)0;

    private void setTransient(){
        StringTokenizer st = new StringTokenizer(quizData, "=,");
        while(st.hasMoreTokens()){
            switch(st.nextToken()){
                case "imgId": imgId = Long.parseLong(st.nextToken()); break;
                default: System.out.println("Record: Wrong quizData: "+quizData);
            }
        }
    }

    public Long getImgId(){
        if(imgId == 0) setTransient();
        return imgId;
    }
}
