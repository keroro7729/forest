package com.forest.forest_server.form;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HospitalList {
    List<Hospital> items;
    public HospitalList(){
        items = new ArrayList<>();
    }
    public List<Hospital> getItems(){ return items; }
}
