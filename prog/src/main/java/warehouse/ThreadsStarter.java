package warehouse;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class ThreadsStarter {
    static final Phaser phaser = new Phaser();

    public static void main(String[] args) {
        Warehouse war = new Warehouse();
        try {
            ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {  //разделил создание потоков и их запуск
                Thread thread = new Buyer(war);
                pool.execute(thread);
            }
            pool.shutdown();
        } catch (NumberFormatException ex) {
            System.out.println("На вход нужно подать целое число - количество потоков");
        }
    }

}
