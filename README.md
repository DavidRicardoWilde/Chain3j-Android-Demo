# Chain3j-Android-Demo

## 中文说明：

该例子使用的是JAVA 8

## 连接本地节点 
注： 需要使用10.0.2.2（127.0.0.1是虚拟机本地IP）（Windows 本地节点）
```Java
Chain3j chain3j = Chain3j.build(new HttpService(
                            "http://10.0.2.2:8545"));  // Use local MOAC server;
```

## 创建钱包
```Java
  walletFileName = WalletUtils.generateNewWalletFile(Password, WalletFilePath, false);
```
  创建的钱包文件会自动带上文件后缀。 可以取出，否则后期解锁或者使用Keystore时，加上后缀。
  
## 注：
  1. 需要使用10.0.2.2（127.0.0.1是虚拟机本地IP）（Windows 本地节点）
  2. 在安卓虚拟机使用Windows上创建的Keystore会造成内存溢出
  3. 安卓无法使用org.bouncycastle.* 请换成org.SpongyCastle.* (或者使用 chain3j的 android分支）
