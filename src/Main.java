import api.*;
import gui.StartingPage;

import java.io.*;


public class Main {
    static public void main(String[] args) {

        ManageUsers usersManager = new ManageUsers();
        ManageEvaluations evaluationsManager = new ManageEvaluations();
        ManageAccommodations accommodationsManager = new ManageAccommodations();


//        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("usersManager.bin"))) {
//            out.writeObject(usersManager);
//        } catch (IOException e1) {
//            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
//        }
//
//        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("evaluationsManager.bin"))) {
//            out.writeObject(evaluationsManager);
//        } catch (IOException e1) {
//            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
//        }
//
//        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accommodationsManager.bin"))) {
//            out.writeObject(accommodationsManager);
//        } catch (IOException e1) {
//            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
//        }

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream("usersManager.bin"))) {
            usersManager = (ManageUsers) out.readObject();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εισόδου");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream("evaluationsManager.bin"))) {
            evaluationsManager = (ManageEvaluations) out.readObject();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εισόδου");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream("accommodationsManager.bin"))) {
            accommodationsManager = (ManageAccommodations) out.readObject();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εισόδου");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        new StartingPage(usersManager, evaluationsManager, accommodationsManager);
    }
}
