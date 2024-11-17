package ku.cs.services;

import ku.cs.models.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private List<Product> temporaryProductList;

    private OrderManager() {
        temporaryProductList = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public List<Product> getTemporaryProductList() {
        return temporaryProductList;
    }

    public void addProduct(Product product) {
        // ตรวจสอบว่าสินค้าอยู่ใน Temporary Product List หรือไม่
        if (!temporaryProductList.contains(product)) {
            temporaryProductList.add(product);
        }
    }


    public void removeProduct(Product product) {
        temporaryProductList.remove(product);
    }


}
