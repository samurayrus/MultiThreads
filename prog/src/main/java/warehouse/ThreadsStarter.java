package warehouse;

import java.util.ArrayList;
import java.util.List;

public class ThreadsStarter {
    private static List<Thread> ls = new ArrayList<Thread>();

    public static void main(String[] args) {
        Warehouse war = new Warehouse();

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            ls.add(new Buyer(war));
        }
        runTh();
    }

    public static void runTh() {
        for (Thread th : ls) {
            System.out.println("Поток " + th.getName() + " запущен");
            th.start();
        }
    }
}
