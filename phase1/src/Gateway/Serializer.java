package Gateway;
import Controllers.*;
import UseCases.*;

import java.io.*;


public class Serializer {
    public UseCaseStorage Read() {
        try{
            FileInputStream fileIn = new FileInputStream("phase1/src/Storage/ucs.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            UseCaseStorage ucs = (UseCaseStorage) in.readObject();
            in.close();
            fileIn.close();
            return ucs;
        } catch (FileNotFoundException e) {
            System.out.println("Serializable file wasn't found, check the path.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Object was not reconstructable, initialized a fresh system: ");
            System.out.println(e);
            UseCaseFactory ucf = new UseCaseFactory();
            UseCaseStorage ucs = ucf.getUseCaseStorage();
            return ucs;
        } catch (Exception e){
            System.out.println(e);
            System.out.println("System failed to reboot from nmemmhorhee.");
        }
        return null;
    }

    public Boolean Write(UseCaseStorage ucs){
        try{
//            UseCaseStorage ucs = new UseCaseStorage(tm,rm,sm,am,im);
            FileOutputStream fileOut = new FileOutputStream("phase1/src/Storage/ucs.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ucs);
            out.close();
            fileOut.close();
            System.out.println("Serialized current state to ucs.ser");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    }
