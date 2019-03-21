package Database;

import Database.SQLite.SQLite;
import Product.Product;

public class DatabaseImp implements Database {
    private static DatabaseImp instance;

    private DatabaseImp() {
    }

    //DatabaseImp instance is created only when needed. It is one object for whole app (singleton)
    public static DatabaseImp getInstance() {
        if(instance == null){
            instance = new DatabaseImp();
        }
        return instance;
    }


    //Method getProduct as a param takes a String, which is a product's barcode. Then it selects Product (from database 'productsDB'), which has the same barcode.
    public Product getProduct(String text) {
        return SQLite.selectProduct(text);
    }
}
