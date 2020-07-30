package warehouse;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Buyer extends Thread {
    private int productsPerBuy = 0;  //Стартовое значение. Сколько товара будет забирать за покупку
    private int quantityPurchase = 0;  //Количество совершенных покупок
    private int sumPurchase = 0;   //Сколько всего товара скупил
    private Warehouse war;      //Ссылка на склад
    private int balance = 0;    // Взятый остаток со склада

    public Buyer(Warehouse war) {
        this.war = war;
    }

    @Override
    public void run() {
        try {
            while (war.getProduct() > 0) {
                ThreadsStarter.cyclicBarrier.await(); //Сообщает о готовности и ждет других участников

                productsPerBuy = 1 + new Random().nextInt(10);  //рандом значение. Сколько будет брать товара за раз
                int thisPurchase = war.change(productsPerBuy); //берем товар со склада. Раздел на две переменные, чтобы вывести в inform количество взятого товара и остаток, который забрали
                sumPurchase += thisPurchase;

                //Если получили меньше того, что запрашивали - записываем в остаток.
                if (thisPurchase != 0) {
                    quantityPurchase++;
                    if (thisPurchase < productsPerBuy)
                        balance = thisPurchase;
                }

                //Сбрасываем барьер, генерим всем остальным покупателям в ожидании BrokenBarrierException
                //ThreadsStarter.cyclicBarrier.reset();
                //ThreadsStarter.exiter = true;
                //throw new BrokenBarrierException();
                ThreadsStarter.cyclicBarrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            //При сбросе барьера. Вызывается при cyclicBarrier.reset() у тех, кто ждал на cyclicBarrier.await()
            System.out.println("=== [ОТЛАДКА {" + getName() + "} Вышел из ожидания из-за сломанного барьера] ===");
        } finally {
            inform(); //Выполнится и при завершении кода и при BrokenBarrierException
        }
    }

    public void inform() {

        String answer = "{[" + this.getName() + "] Сделал покупок [" + quantityPurchase + "] раз/ Товара скупил: [" + sumPurchase;
        if (balance != 0) {
            answer += "] Забрал остаток [" + balance + "]}";
        } else {
            answer += "]}";
        }
        System.out.println(answer);
    }
}
