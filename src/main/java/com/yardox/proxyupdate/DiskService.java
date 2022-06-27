package com.yardox.proxyupdate;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.model.ProxyType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiskService {
    private String fileName = "C:\\workspace\\proxyupdate\\good.txt";
    public void saveFile(List<MyProxy> myProxies, String nameOfSavedFile){
        try {
            FileOutputStream outStream = new FileOutputStream(nameOfSavedFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(myProxies);
            outStream.close();
            System.out.println("save successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(List<MyProxy> myProxies){
        saveFile(myProxies, fileName);
    }
    public ArrayList<MyProxy> read(){
        FileInputStream freader;
        ArrayList<MyProxy> result = new ArrayList<>();
        try {
            freader = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            result = (ArrayList<MyProxy>) objectInputStream.readObject();

//            System.out.println("The name is " + result);
            System.out.println("read successful");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
