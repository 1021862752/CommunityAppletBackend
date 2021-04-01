package org.jeecg.modules.demo.activitylog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test1 {
    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger();
        System.out.println(index.get());
        index.getAndIncrement();
        System.out.println(index.get());
    }

    static boolean checkDataByArea(List<Map> list, String areaSn) {
        AtomicBoolean flag = new AtomicBoolean(false);
        list.forEach(
                data -> {
                    if (areaSn.equals(data.get("WA_AREA_SN"))) {
                        flag.set(true);
                        return;
                    }
                }
        );
        return flag.get();
    }
}
