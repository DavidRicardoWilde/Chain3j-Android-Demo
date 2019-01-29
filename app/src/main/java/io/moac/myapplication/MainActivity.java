package io.moac.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Numeric;

import java.io.File;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {
    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v){
                System.out.println("this is a test");
                textView.setText("this is a test");
                try{
                    ButtonClick();
                }catch (Exception e){
                    System.out.print(e);
                }
            }
        });

    }



    public void ButtonClick() throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Chain3j chain3j = Chain3j.build(new HttpService(
                            "http://10.0.2.2:8545"));  // Use local MOAC server;

                    System.out.println("Out Connected to MOAC client version: "
                            + chain3j.chain3ClientVersion().send().getChain3ClientVersion());


                    //generate wallet

//                    String walletFileName = null;
//                    String walletFilePath = "/storage/self/primary/Download/";
//                    String passwrod = "test123";
//
//                    File file = new File(walletFilePath);
//                    if(!file.exists()){
//                        file.mkdirs();
//                    }else {
//                        System.out.println("The file is exist. And the path is "+walletFilePath);
//                    }
//
//                    walletFileName = WalletUtils.generateNewWalletFile(passwrod, new File(walletFilePath), false);
//                    System.out.println("The wallet is "+walletFileName);

//                    String fileExist = "/storage/self/primary/Download/"+ walletFileName;
//                    File f = new File(fileExist);
//                    File f = new File("/storage/self/primary/Download/UTC--2019-01-28T01-33-43.546000000Z--fa036de8102f15782427c2c293ff04040c0fd24c.json");
//                    if (!f.exists()){
//                        System.out.println("the file is not exist");
//                    }else{
//                        System.out.println("the file is exist");
////                        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS)){
////
////                        }
//                    }

                        //unlock the wallet file
                    Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
                    String src = credentials.getAddress();
                    System.out.println("Load address: " + src);
//
                    // Get the TX count from network and build the TX
                    BigInteger srcNonce = chain3j.mcGetTransactionCount(src, DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
                    System.out.println("MOAC testnet account TX count: "
                            + srcNonce.toString());
                    System.out.println("MOAC testnet account balance: "
                            + chain3j.mcGetBalance(src, DefaultBlockParameter.valueOf("latest")).send().getBalance());

                    BigInteger sendValue = BigInteger.valueOf(1000000000000L);
                    String des = "0x1bC165d9015229c99b9F984a9104B57da5bF39B0";
                    RawTransaction rawTx  = createTX(srcNonce, des, sendValue);

                    // Sign the TX with Credential
                    byte[] signedTX = TransactionEncoder.signTxEIP155(rawTx, 100, credentials);
                    String signedRawTx = Numeric.toHexString(signedTX);

                    System.out.println("Signed RawTX: "+signedRawTx);

                    // Send the TX to the network and wait for the results
                    System.out.println("MOAC TX send: " + chain3j.mcSendRawTransaction(signedRawTx).send());
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();
    }

    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "/storage/self/primary/Download/UTC--2019-01-28T01-33-43.546000000Z--fa036de8102f15782427c2c293ff04040c0fd24c.json");

    }

    private RawTransaction createTX(BigInteger nonce, String des, BigInteger sendValue) {
        // nonce, gasPrice, gasLimit, des, amount to send in Sha
        return RawTransaction.createMcTransaction(
                nonce,
                BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000), des,
                sendValue);
    }
}
