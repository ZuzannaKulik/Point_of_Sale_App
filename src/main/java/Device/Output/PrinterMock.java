package Device.Output;

import java.io.*;

//PrinterMock is responsible for mocking printing - data are sent to file (PrinterDeviceMock>receipt.txt)
public class PrinterMock implements OutputDevice {

    public void execute(String text) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter
                    (new OutputStreamWriter(new FileOutputStream("src/main/resources/PrinterDeviceMock/receipt.txt")));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
