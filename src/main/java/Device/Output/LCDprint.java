package Device.Output;

//LCDprint is responsible for displaying data on screen
public class LCDprint implements OutputDevice {
    public void execute(String text) {
        System.out.println(text);
    }

}
