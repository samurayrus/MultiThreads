package warehouse;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsStarter {
    static CyclicBarrier cyclicBarrier;
    static boolean exiter = false;
    public static void main(String[] args) {
        Warehouse war = new Warehouse();
        try {
            int numberOfBuyers = Integer.parseInt(args[0]);
            cyclicBarrier = new CyclicBarrier(numberOfBuyers);
            ExecutorService pool = Executors.newFixedThreadPool(numberOfBuyers);
            for (int i = 0; i < numberOfBuyers; i++) {  //разделил создание потоков и их запуск
                Thread thread = new Buyer(war);
                pool.execute(thread);
            }

            pool.shutdown();
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("На вход нужно подать целое число больше нуля - количество потоков");
        }
    }

}
