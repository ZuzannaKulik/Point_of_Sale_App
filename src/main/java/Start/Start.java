package Start;

import Counter.Counter;

public class Start {
    public static void main(String[] args) {

        boolean randomScannMode;
        if (args.length > 0 && args[0].equals("-c")) {
            randomScannMode = false;
        } else {
            randomScannMode = true;
        }

//        Counter counterFis1 = new Counter("Kasa_fiskalna_#1", false);
//        counterFis1.processClients();
//        Counter counterFis2 = new Counter("Kasa_fiskalna_#2", true);
//        counterFis2.processClients();
//        Counter counterFis3 = new Counter("Kasa_fiskalna_#3", randomScannMode);
//        counterFis3.processClients();
        Counter Kasa_fiskalna_tryb_console1 = new Counter("Kasa_fiskalna_tryb_console1", randomScannMode);
        while (true){
            Kasa_fiskalna_tryb_console1.processClient();
        }

    }
}
