/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Transaction;
import java.util.ArrayList;

/**
 *
 * @author Dsilv
 */
public class TransactionStorage {
     private static TransactionStorage instance;
     private ArrayList<Transaction> transactions;
     
     private TransactionStorage(){
         this.transactions = new ArrayList<>();
     }
     
     public static TransactionStorage getInstance() {
        if (instance == null) {
            instance = new TransactionStorage();
        }
        return instance;
    }
     
     public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
     
     public boolean addTransaction(Transaction transaction){
        this.transactions.add(transaction);
        return true;
    }
}
