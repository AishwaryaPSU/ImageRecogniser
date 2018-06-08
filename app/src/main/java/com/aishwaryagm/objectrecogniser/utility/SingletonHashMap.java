package com.aishwaryagm.objectrecogniser.utility;

import com.aishwaryagm.objectrecogniser.CallBackInterface;

import java.util.HashMap;

/**
 * Created by aishwaryagm on 6/4/18.
 */

public class SingletonHashMap {

    private static HashMap<String, CallBackInterface> hashMap;

    public static synchronized HashMap<String, CallBackInterface> getHashMap() {
            if (hashMap == null) {
                hashMap = new HashMap<>();
            }
        return hashMap;
    }
}
