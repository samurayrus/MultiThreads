package warehouse;

public class Warehouse {
    private int product = 1000; //Кол-во продукта на складе

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public synchronized int change(int ggg) {
        if (ggg <= getProduct()) {
            this.product -=ggg;
            return ggg;
        } else {
            int ccc = getProduct();
            setProduct(0);
            return ccc;
        }

    }
}
