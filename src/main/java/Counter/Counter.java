package Counter;

import Database.DatabaseImp;
import Device.Input.ConsoleScannerMock;
import Device.Input.InputDevice;
import Device.Input.RandomScannerMock;
import Device.Output.LCDprint;
import Device.Output.OutputDevice;
import Device.Output.PrinterMock;
import Exceptions.ExitException;
import Exceptions.InvalidInputException;
import Product.Product;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

//  Counter class implementing main processes of the counter: proceeding the product
//  (getting the scanner input, sending output to printer or LCD display, counting input prices or bills etc.)
public class Counter {

    private String counterId;                            //counter name used for identifying the counter in memory (history of receipts)
    private LinkedHashMap<Product, Double> products;    //Map of products bought by one client(key) with the quantity of items from one scan(value)
    private LinkedList<Double> bills;                   //List used for counting totall sum of bills from one counter
    private boolean randomScannMode;                    //boolean variable to set way of mocking scanner process(random or enter data manually)
    private InputDevice scannerMock;
    private OutputDevice printer;
    private OutputDevice lcdPrint;

    //Counter constructor
    public Counter(String counterId, boolean randomScannMode) {
        this.counterId = counterId;
        this.products = new LinkedHashMap<Product, Double>();
        this.bills = new LinkedList<>();
        this.lcdPrint = new LCDprint();
        this.printer = new PrinterMock();
        this.scannerMock = (this.randomScannMode = randomScannMode) ? new RandomScannerMock() : new ConsoleScannerMock();
    }

    //Method 'processClients' - processing clients, method 'exit()' separates successive clients
    public void processClient(){
        lcdPrint.execute("Welcome new client! :)");
            while(true){
                try {
                    processProduct();
                } catch (ExitException e) {
                    exit();
                    break;
                }
            }
    }

    //Method 'processProduct' - processing single product (from scanning to adding to receipt (map 'products'), if possible)
    public void processProduct() throws ExitException {
        try {
            lcdPrint.execute("Scan new product: ");
            String barcode = this.scannerMock.execute();                        //in scannerMock barcode is randomly generated or entered manually
            Product temp = DatabaseImp.getInstance().getProduct(barcode);
            if (temp == null) {
                lcdPrint.execute("Product not found");
            } else {
                Double quantity = getQuantity();               //quantity is the amount of scanned item
                String name = temp.getName();              //(instead of scanning couple of times the same product,
                Double price = temp.getPrice();            //it may be scanned once with just choosing its quantity)
                Double itemPrice = quantity * price;       //itemPrice is the price for all items from one scanning
                itemPrice = (double) Math.round(itemPrice * 100d) / 100d;
                String text = name + ", Price=" + price + "  x" + quantity + "----> TOTAL: " + itemPrice + " PLN";
                this.products.put(temp, quantity);         //Adding item to receipt(map 'products')
                lcdPrint.execute(text);
            }
        } catch (InvalidInputException e) {
            this.lcdPrint.execute(e.getMessage());  //handling the exception of barcode invalid format
        }
    }

    //Method getQuantity - setting the number of repetitions of singly scanned product
    private Double getQuantity() {
        this.lcdPrint.execute("(Optionally type quantity)");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();                     //get the value of item repetitions from user
        if (number.equals("")) {
            return 1d;                                          //if user gives no number, the default value is 1
        } else return Double.valueOf(number);
    }

    //Method getReceiptContent - formatting content of the receipt from 'products' map
    private String getReceiptContent(LinkedHashMap<Product, Double> products) {
        String result = "";
        Double bill = getSummedBill(products);                              //bill is the total price client has to pay
        for (Map.Entry<Product, Double> entry : products.entrySet()) {
            result = result + entry.getKey().toString() + "  x" + entry.getValue() + "       = " + getItemPrice(entry) + " PLN" + "\n";
        }
        return result + "TOTAL BILL: " + bill + "PLN\n";
    }

    //Method getSummedBill - counting the bill (total price client has to pay)
    private double getSummedBill(LinkedHashMap<Product, Double> products) {
        Double bill = 0d;
        for (Map.Entry<Product, Double> entry : products.entrySet()) {
            Double itemPrice = getItemPrice(entry);                         //itemPrice is the price of all items from single scan
            bill = bill + itemPrice;
        }
        bill = (double) Math.round(bill * 100d) / 100d;
        return bill;
    }

    //Method getItemPrice - counting the price of all items from single scan
    private double getItemPrice(Map.Entry<Product, Double> entry) {
        Double itemPrice = 0d;
        itemPrice = ((entry.getValue()) * (entry.getKey().getPrice()));
        itemPrice = (double) Math.round(itemPrice * 100d) / 100d;
        return itemPrice;
    }

    //Method exit - ends processing of the single client, sending receipt to be printed and adding it to the counter memory file
    public void exit() {
        lcdPrint.execute("TOTAL BILL: " + getSummedBill(products)+ "PLN\n");
        String receipt = getReceiptContent(products);
        printer.execute(receipt);
        addToCounterMemory(receipt);
        products.clear();
    }

    //Method addToCounterMemory - adding the receipt to the counter memory and counting the overall amount of money the counter 'earned'
    public void addToCounterMemory(String text) {
        this.bills.add(getSummedBill(this.products));           //adding the last bill to the billslist
        double overall = 0;
        for (double number : bills) {
            overall = overall + number;                         //summing all the bills to count the overall value
        }
        overall = (double) Math.round(overall * 100d) / 100d;
        bills.clear();                                          //clearing the bills list to add the overall value
        bills.add(overall);                                     //to avoid unnecessary summing all previous values again
        LocalDateTime time = LocalDateTime.now();
        text = text + "----------------FOR: " + time + " OVERALL IS: " + overall + " ----------------\n"; //gathering information about cash register status at specific time(after printing the receipt)
        try {
            String filePath = "src/main/resources/CounterMemoryMock/" + this.counterId + "_receipts_history.txt"; //the file takes its name from the counter name
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
            bufferedWriter.write(text);             //the latest receipt with cash register status and date-time information is written in the counter memory (CounterMemoryMock>counter_name_receipts_history.txt)
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}