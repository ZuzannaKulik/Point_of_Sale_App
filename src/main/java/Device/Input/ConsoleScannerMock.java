package Device.Input;

import Exceptions.ExitException;
import Exceptions.InvalidInputException;

import java.util.Scanner;

//ConsoleScannerMock mocks the real scanning process - barcodes are entered manually from keyboard
public class ConsoleScannerMock implements InputDevice {

    public String execute() throws ExitException, InvalidInputException {

        Scanner scanner = new Scanner(System.in);
        String barCode = scanner.nextLine();
        if ((barCode.toLowerCase()).equals("exit")) {               //if the input is 'exit', the ExitException is thrown
            throw new ExitException("Exit input");
        }
        if (!(barCode.matches("\\d{8}"))) {                   //if the input has invalid format, the InvalidInputException is thrown
            throw new InvalidInputException("Invalid bar-code");
        }
        return barCode;
    }
}
