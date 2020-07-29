package warehouse;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class ThreadsStarter {
    static Phaser phaser;

    public static void main(String[] args) {
        Warehouse war = new Warehouse();
        try {
            Integer numberOfBuyers = Integer.parseInt(args[0]);
            phaser= new Phaser(numberOfBuyers);
            ExecutorService pool = Executors.newFixedThreadPool(numberOfBuyers);
            for (int i = 0; i < numberOfBuyers; i++) {  //разделил создание потоков и их запуск
                Thread thread = new Buyer(war);
                pool.execute(thread);
            }
            pool.shutdown();
        } catch (IllegalArgumentException ex) {
            System.out.println("На вход нужно подать целое число больше нуля - количество потоков");
        }
    }

}
