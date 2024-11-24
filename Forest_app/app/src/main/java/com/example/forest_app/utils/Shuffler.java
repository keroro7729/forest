package com.example.forest_app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shuffler {
    public static List<Integer> get(int n){
        List<Integer> result = new ArrayList<>();
        for(int i=0; i<n; i++) result.add(i);
        Collections.shuffle(result);
        return result;
    }

    public static List<Integer> get(){ return get(4); }
}
