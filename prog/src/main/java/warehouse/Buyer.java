package warehouse;

import java.util.Random;

public class Buyer extends Thread {
    private int buy = 0;
    private int buyer = 0;
    private int buys = 0;
    private Warehouse war;
    int ccc = 0;
    boolean ost = false;

    public Buyer(Warehouse war) {
        this.war = war;
        buy = 1 + new Random().nextInt(10);
        System.out.println("Запрос на " + buy + " в " + this.getName());
    }

    @Override
    public void run() {

        while (true) {
            buyer++;
            int cccf = war.change(buy);
            buys += cccf;
            if (cccf != buy && cccf!=0) {
                ccc = cccf;
                ost = true;
                System.out.println("Забор остатка " + cccf + " Пришло " +buy + " запрос ");
                //Main.destroyTh();
            }
            if(cccf==0) {
                break;
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        inform();
    }


    public void inform() {

        System.out.println( "{[" + this.getName() +"] Сделал покупок [" + buyer + "] раз/ Товара скупил: [" + buys + "] Был ли остаток [" + ost + "] Забрал остаток [" + ccc +"]}" );
    }
}
