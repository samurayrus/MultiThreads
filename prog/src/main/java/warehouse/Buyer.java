package warehouse;

import java.util.Random;

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
        while (true) {
            ThreadsStarter.phaser.arriveAndAwaitAdvance(); //Сообщает о готовности и ждет других участников
            productsPerBuy = 1 + new Random().nextInt(10);  //рандом значение. Сколько будет брать товара за раз
            quantityPurchase++;  //+1 покупка. Считаются не сколько мы покупок сделали, а сколько раз попытались купить. Пришли на склад - там пусто - все равно +1 покупка

            int thisPurchase = war.change(productsPerBuy); //берем товар со склада. Раздел на две переменные, чтобы вывести в inform количество взятого товара и остаток, который забрали
            sumPurchase += thisPurchase;
            //Если получили меньше того, что запрашивали - записываем в остаток.
            // Второе условие, чтобы не выводило лишие запросы в конце (Когда на складе ничего нет
            if (thisPurchase != productsPerBuy) {
                if (thisPurchase != 0) {
                    balance = thisPurchase;
                    System.out.println("Забор остатка " + thisPurchase + " Пришло " + productsPerBuy + " запрос ");
                }
                ThreadsStarter.phaser.arrive();
                break;  //забрали остаток, значит дальше можно ничего не делать и выводить результат
            }
        }
        inform();
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
