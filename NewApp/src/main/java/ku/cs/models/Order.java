package ku.cs.models;

public class Order {
    private String orderID;
    private String employeeID;
    private String customerID;
    private int orderStatus;
    private String orderTimestamp;
    private int outstandingBalance;
    private String orderType;
    private String deliveryDate;

    public Order(String orderID, String employeeID, String customerID, int orderStatus,
                 String orderTimestamp, int outstandingBalance, String orderType, String deliveryDate) {
        this.orderID = orderID;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.orderStatus = orderStatus;
        this.orderTimestamp = orderTimestamp;
        this.outstandingBalance = outstandingBalance;
        this.orderType = orderType;
        this.deliveryDate = deliveryDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public int getOutstandingBalance() {
        return outstandingBalance;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }
}
