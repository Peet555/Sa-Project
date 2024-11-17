package ku.cs.models;

import java.io.InputStream;

public class Product {

     String Product_ID;
     String Product_Name;
     String Type;
     int Price; // ใช้ double แทน int เพื่อรองรับราคาที่อาจเป็นทศนิยม
     int Quantity;
     String Description; // คำอธิบาย
     InputStream Product_Image;
     byte[] Product_Image_Byte;
     private String orderID;

     private String productId;
     private String productName;
     private int price;
     private int quantity; // อาจต้องการสำหรับรายละเอียดผลิตภัณฑ์
     private int orderedQuantity; // จำนวนที่สั่งในออเดอร์นี้
     private String description; // อาจต้องการสำหรับรายละเอียดผลิตภัณฑ์
     private byte[] productImageByte;


    public Product(String ID,String product_Name, int price, byte[] product_Image) {
        this.Product_ID = ID;
        this.Product_Name = product_Name;
        this.Price = price;
        this.Product_Image_Byte = product_Image;
    }

    public Product(String product_Name, int price, int quantity, String description, byte[] product_Image_Byte) {
        Product_Name = product_Name;
        Price = price;
        Quantity = quantity;
        Description = description;
        Product_Image_Byte = product_Image_Byte;
    }

    public Product(String Product_ID, String Product_Name, int Quantity, int Price, String Type, String Description, InputStream Product_Image) {
        this.Product_ID = Product_ID;
        this.Product_Name = Product_Name;
        this.Quantity = Quantity;
        this.Price = Price;
        this.Type = Type;
        this.Description = Description;
        this.Product_Image = Product_Image;
    }

    public Product(String productId, String productName, String type,int price, int quantity, String description) {
        this.Product_ID = productId; // ตรวจสอบการตั้งค่านี้
        this.Product_Name = productName;
        this.Type = type;
        this.Price = price;
        this.Quantity = quantity;
        this.Description = description;
    }

    public Product(String productId, String productName, int price, int quantity, String description, byte[] productImageByte) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.productImageByte = productImageByte;
    }




    public void setProduct_ID(String product_ID) {
        this.Product_ID = product_ID;
    }


    // เพิ่ม getter และ setter ตามต้องการ
    public String getProduct_ID() {
        return Product_ID;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public String getType() {
        return Type;
    }

    public String getDescription() {
        return Description;
    }

    public void setProduct_Name(String product_Name) {
        this.Product_Name = product_Name;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
    public InputStream getImage() {
        return Product_Image;
    }

    public void setImage(InputStream product_Image) {
        this.Product_Image = product_Image;
    }

    public byte[] getProduct_Image_Byte() {
        return Product_Image_Byte;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getOrderID() {
        return orderID;
    }
    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }


    @Override
    public String toString() {
        return "Product{" +
                "Product_ID='" + Product_ID + '\'' +
                ", Product_Name='" + Product_Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Price=" + Price +
                ", Quantity=" + Quantity +
                ", Description='" + Description + '\'' +
                '}';
    }

}
