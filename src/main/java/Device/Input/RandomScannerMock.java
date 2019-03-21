package Device.Input;

import Exceptions.ExitException;
import Exceptions.InvalidInputException;

import java.util.Random;

//RandomScannerMock mocks the real scanning process - barcodes are randomly chosen from the array of example scan inputs ('randomScanInputs')
public class RandomScannerMock implements InputDevice {
    public String execute() throws ExitException, InvalidInputException {
        String[] randomScanInputs = {"11111111", "33443344", "44330908", "44330901", "44330902", "44330903", "44330904", "44330905",
                "44330906", "44330907", "14330907", "24330907", "22222222", "33333333", "44444444", "55555555", "66666666", "77777777",
                "88888888", "exit", "EXIT", "ExiT", "eXiT", "", "123", "123456789", "abcda"};

        Random rand = new Random();
        int number = rand.nextInt(randomScanInputs.length);
        String barCode = randomScanInputs[number];
        if ((barCode.toLowerCase()).equals("exit")) {   //if the input is 'exit', the ExitException is thrown
            throw new ExitException("Exit input");
        }
        if (!(barCode.matches("\\d{8}"))) {        //if the input has invalid format, the InvalidInputException is thrown
            throw new InvalidInputException("Invalid bar-code");
        }
        return barCode;
    }
}
