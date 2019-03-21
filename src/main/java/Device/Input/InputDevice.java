package Device.Input;

import Exceptions.ExitException;
import Exceptions.InvalidInputException;

public interface InputDevice {
    String execute() throws ExitException, InvalidInputException;
}
