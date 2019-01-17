package com.example.david.Model;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.http.HttpService;

public class Chain3jService {
    private static final Chain3j outInstance = Chain3j.build(new HttpService("http://http://10.0.2.2:8545"));

    public static Chain3j getInstance() {
        return outInstance;
    }

    private Chain3jService(){

    }
}
