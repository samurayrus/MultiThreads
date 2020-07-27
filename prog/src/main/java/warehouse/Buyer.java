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
        productsPerBuy = 1 + new Random().nextInt(10);  //рандом значение. Сколько будет брать товара за раз
        System.out.println("Запрос на " + productsPerBuy + " в " + this.getName()); //Вывод для инфо
    }

    @Override
    public void run() {
        while (true) {
            quantityPurchase++;  //+1 покупка
            int thisPurchase = war.change(productsPerBuy); //берем товар со склада. Раздел на две переменные, чтобы вывести в inform количество взятого товара и остаток, который забрали
            sumPurchase += thisPurchase;

            //Если получили меньше того, что запрашивали - записываем в остаток.
            // Второе условие, чтобы не выводило лишие запросы в конце (Когда на складе ничего нет
            if (thisPurchase != productsPerBuy && thisPurchase != 0) {
                balance = thisPurchase;
                System.out.println("Забор остатка " + thisPurchase + " Пришло " + productsPerBuy + " запрос ");
                break;
            }

         //   if (thisPurchase == 0) {
          //      break; //Если мы изменили кол-во товара на складе на 0, то уже ничего не сделаем и выходим с выводом
         //   }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        inform(); //Вывод
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
