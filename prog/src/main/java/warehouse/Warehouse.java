package warehouse;

public class Warehouse {
    private int product = 100000; //Кол-во продукта на складе

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public synchronized int change(int changeProduct) { //изменение товара на складе + возвращает на сколько вышло изменить
        if (changeProduct <= getProduct()) {
            this.product -= changeProduct;
            return changeProduct;
        } else {
            int ccc = getProduct();
            setProduct(0);
            return ccc;
        }

    }
}
