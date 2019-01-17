package com.example.david.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.Model.Chain3jService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.chain3j.crypto.CipherException;
import org.chain3j.crypto.ECKeyPair;
import org.chain3j.crypto.Keys;
import org.chain3j.crypto.Wallet;
import org.chain3j.crypto.WalletFile;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Numeric;

import java.io.File;
import java.lang.Exception;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity{

    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    private Button button;
    private TextView textView;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        verifyStoragePermissions(this);

       button.setOnClickListener(new View.OnClickListener() {
           @Override

           public void onClick(View v){
               textView.setText("this is a test");
                try{
                    ButtonClick();
//                    GenerateWallet wallet = new GenerateWallet();
//                    wallet.generateWallet();
                    System.out.println("generate wallet");
                    generateWallet("test123");
                    System.out.println("You already create a wallet");
                }catch (Exception e){
                    System.out.print(e);
                }
           }
       });

    }

    public static void generateWallet(String password) {

        new Thread(new Runnable() {
            @Override
            public void run()  {
                try {
                    String walletFileName="";
                    String walletFilePath="";
                    String passwrod="test123";
                    walletFileName
//                    WalletFile walletFile;
//                    ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//                    walletFile = Wallet.createStandard(password, ecKeyPair);
//                    System.out.println("Address: " + walletFile.getAddress());
//                    ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
//                    String jsonStr = objectMapper.writeValueAsString(walletFile);
//                    System.out.println("keystore json file " + jsonStr);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void ButtonClick() throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Chain3j chain3j = Chain3j.build(new HttpService("http://10.0.2.2:8545"));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //

    //Check the Read and Write permissions of the file.
    public static void verifyStoragePermissions(Activity activity){
        try {
            System.out.println("Already have permissions");
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED){
                System.out.println("no permission yet");
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //test network
    public void testNetwork(View view){
        Observable.create((ObservableOnSubscribe<Chain3ClientVersion>) e -> {
            Chain3ClientVersion send = Chain3jService.getInstance().chain3ClientVersion().send();
            e.onNext(send);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(web3ClientVersion -> {
                    String version = web3ClientVersion.getChain3ClientVersion();
                    Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
                    Log.i("chain3j",version);
                }, throwable -> throwable.printStackTrace());
    }

    //Generate Wallet

//    public void ButtonClick() throws Exception{
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Chain3j chain3j = Chain3j.build(new HttpService(
//                            "http://10.0.2.2:8545"));  // Use local MOAC server;
//
//                    System.out.println("Out Connected to MOAC client version: "
//                            + chain3j.chain3ClientVersion().send().getChain3ClientVersion());
//
//                    //
//                    File f = new File("/storage/self/primary/Download/UTC--2018-11-14T16-59-28.398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");
//                    if (!f.exists()){
//                        System.out.println("the file is not exist");
//                    }else{
//                        System.out.println("the file is exist");
////                        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS)){
////
////                        }
//                    }
//
//                    Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
//                    String src = credentials.getAddress();
//                    System.out.println("Load address: " + src);
//
//                    // Get the TX count from network and build the TX
//                    BigInteger srcNonce = chain3j.mcGetTransactionCount(src, DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
//                    System.out.println("MOAC testnet account TX count: "
//                            + srcNonce.toString());
//                    System.out.println("MOAC testnet account balance: "
//                            + chain3j.mcGetBalance(src, DefaultBlockParameter.valueOf("latest")).send().getBalance());
//
//                    BigInteger sendValue = BigInteger.valueOf(1000000000000L);
//                    String des = "0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B";
//                    RawTransaction rawTx  = createTX(srcNonce, des, sendValue);
//
//                    // Sign the TX with Credential
//                    byte[] signedTX = TransactionEncoder.signTxEIP155(rawTx, 100, credentials);
//                    String signedRawTx = Numeric.toHexString(signedTX);
//
//                    System.out.println("Signed RawTX: "+signedRawTx);
//
//                    // Send the TX to the network and wait for the results
//                    System.out.println("MOAC TX send: " + chain3j.mcSendRawTransaction(signedRawTx).send());
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//            }
//        }).start();
//    }

//    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
//        return WalletUtils.loadCredentials(
//                password, "/storage/self/primary/Download/UTC--2018-11-14T16-59-28.398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");
//
//    }
//
//    private RawTransaction createTX(BigInteger nonce, String des, BigInteger sendValue) {
//        // nonce, gasPrice, gasLimit, des, amount to send in Sha
//        return RawTransaction.createMcTransaction(
//                nonce,
//                BigInteger.valueOf(20000000000L),
//                BigInteger.valueOf(21000), des,
//                sendValue);
//    }

}
