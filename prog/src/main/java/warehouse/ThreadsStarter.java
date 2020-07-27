package warehouse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ThreadsStarter {
    private static List<Thread> ls = new ArrayList<Thread>();

    public static void main(String[] args) {
        Warehouse war = new Warehouse();
        try {
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {  //разделил создание потоков и их запуск
                ls.add(new Buyer(war));
            }
            runTh();
        } catch (NumberFormatException ex) {
            System.out.println("На вход нужно подать целое число - количество потоков");
        }
    }

    public static void runTh() {
        for (Thread th : ls) {
            System.out.println("Поток " + th.getName() + " запущен");
            th.start();
        }
    }
}
