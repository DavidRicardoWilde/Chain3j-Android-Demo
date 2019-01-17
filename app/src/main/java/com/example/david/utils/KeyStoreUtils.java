package com.example.david.utils;

import android.util.Log;

import com.example.david.myapplication.App;

import org.chain3j.crypto.CipherException;
import org.chain3j.crypto.ECKeyPair;
import org.chain3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

public class KeyStoreUtils {
    public static final String DEFAULTKEY = "DEFAULT";
    public static final String KEYSTORE_PATH = App.getInstance().getFilesDir().getPath() + "/keystore";

    public static String genKeyStore2Files(ECKeyPair ecKeyPair){
        try {
            File file =getKeyStorePathFile();
            String s = WalletUtils.generateWalletFile(DEFAULTKEY, ecKeyPair, file, false);
        }catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getKeyStorePathFile(){
        File file = new File(KEYSTORE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        Log.e("files", file.getAbsolutePath());
        return file;
    }
}
