package ku.cs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Order {
    private String Order_ID;
    private String Employee_ID;
    private String Customer_ID;
    private int Order_Status;
    private Timestamp Order_Timestamp;    private int Outstanding_Balance;
    private String Order_Type;
    private String Delivery_date;

    public Order(String Order_ID, String Employee_ID, String Customer_ID, int Order_Status,
                 Timestamp orderTimestamp, int Outstanding_Balance, String Order_Type, String Delivery_date) {
        this.Order_ID = Order_ID;
        this.Employee_ID = Employee_ID;
        this.Customer_ID = Customer_ID;
        this.Order_Status = Order_Status;
        this.Order_Timestamp = orderTimestamp;
        this.Outstanding_Balance = Outstanding_Balance;
        this.Order_Type = Order_Type;
        this.Delivery_date = Delivery_date;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public int getOrder_Status() {
        return Order_Status;
    }

    public Timestamp getOrder_Timestamp() { return Order_Timestamp; }

    public int getOutstanding_Balance() {
        return Outstanding_Balance;
    }

    public String getOrder_Type() {
        return Order_Type;
    }

    public String getDelivery_date() {
        return Delivery_date;
    }
}
