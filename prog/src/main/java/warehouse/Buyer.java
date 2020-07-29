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
            //while (!ThreadsStarter.cyclicBarrier.isBroken()) - не пригодилось
            while (true) {
                ThreadsStarter.cyclicBarrier.await(); //Сообщает о готовности и ждет других участников
                productsPerBuy = 1 + new Random().nextInt(10);  //рандом значение. Сколько будет брать товара за раз
                quantityPurchase++;  //+1 покупка. Считаются не сколько мы покупок сделали, а сколько раз попытались купить. Пришли на склад - там пусто - все равно +1 покупка

                int thisPurchase = war.change(productsPerBuy); //берем товар со склада. Раздел на две переменные, чтобы вывести в inform количество взятого товара и остаток, который забрали
                sumPurchase += thisPurchase;

                if (thisPurchase == 0) {  //Условие выхода. На складе ничего нет
                    ThreadsStarter.cyclicBarrier.reset();
                    break;
                }
                //Если получили меньше того, что запрашивали - записываем в остаток.
                // Второе условие, чтобы не выводило лишие запросы в конце (Когда на складе ничего нет
                if (thisPurchase != productsPerBuy) {
                    balance = thisPurchase;
                    System.out.println("Забор остатка " + thisPurchase + " Пришло " + productsPerBuy + " запрос ");
                    //Сбрасываем барьер, генерим всем остальным покупателям в ожидании BrokenBarrierException и получаем вывод
                    ThreadsStarter.cyclicBarrier.reset();
                    break;  //забрали остаток, значит дальше можно ничего не делать и выводить результат
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            //При сбросе барьера
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
