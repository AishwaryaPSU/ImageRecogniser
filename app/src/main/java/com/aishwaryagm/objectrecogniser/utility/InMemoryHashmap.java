package com.aishwaryagm.objectrecogniser.utility;

import com.aishwaryagm.objectrecogniser.CallBackInterface;

import java.util.HashMap;

/**
 * Created by aishwaryagm on 6/3/18.
 */

public class InMemoryHashmap  {
    private static HashMap<String,CallBackInterface> hashMap = new HashMap<>();

    public static void putValue(String key,CallBackInterface callBackInterfaceValue){
        hashMap.put(key,callBackInterfaceValue);
    }

    public static CallBackInterface getValue(String key){
        return hashMap.get(key);
    }

    public static String stringify() {
        return hashMap.toString();
    }
}
