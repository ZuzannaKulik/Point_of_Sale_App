package Product;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private String name;
    private Double Price;
    private String barcode;
    private LocalDateTime addingTime; //the variable enables putting 'the same' Product into map several times

    public Product(String name, Double price, String barcode) {
        this.name = name;
        Price = price;
        this.barcode = barcode;
        this.addingTime = LocalDateTime.now();//addingTime is in fact is the date and time of creating the instance of Product;

    }

    @Override
    public String toString() {
        return name + ", Price=" + Price;
    }

    @Override
    //products with the same barcode are in real life the same products, but for the app needs:
    //products with the same barcode, but scanned at different times are interpreted as two different products.
    //It enables both: putting 'the same' Product into map several times and generating proper scanning history on the receipt
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(barcode, product.barcode) &&
                Objects.equals(addingTime, product.addingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, addingTime);
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return Price;
    }

    public String getBarcode() {
        return barcode;
    }
}
