package com.example.david.myapplication;

import android.os.Environment;

import com.example.david.utils.KeyStoreUtils;

import org.chain3j.crypto.ECKeyPair;
import org.chain3j.crypto.Keys;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.utils.Numeric;

import java.io.File;

public class GenerateWallet {
    public void generateWallet(){
//    if(/*EditText-password*/.length() == 0){
//        etPassword.setError("请输入password");
//            return;
//    }

//        String password = /*EditText-password*/.getText().toString();
        String password = "test123";

        try {
            File fileDir = new File(Environment.getExternalStorageDirectory().getPath() + "MoacWallet");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            ECKeyPair ecKeyPair = Keys.createEcKeyPair();

            //Generate the file in external card
            String filename = WalletUtils.generateWalletFile(password, ecKeyPair, fileDir, false);
            KeyStoreUtils.genKeyStore2Files(ecKeyPair);

            String msg = "fileName:\n" + filename
                    + "\nprivateKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPrivateKey())
                    + "\nPublicKey:\n" + Numeric.encodeQuantity(ecKeyPair.getPublicKey());

            System.out.println("the path is: "+msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
