package com.forest.forest_server.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSelecter {
    public static <T> List<T> select(List<T> list, int n) {
        if (list == null || list.isEmpty() || n <= 0) {
            return new ArrayList<>();
        }

        if (n >= list.size()) {
            return new ArrayList<>(list);
        }

        List<T> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList, new Random());

        return shuffledList.subList(0, n);
    }
}
