package ku.cs.models;

public class Invoice {
    private String invoiceID;
    private String orderID;
    private int invoicePrice;
    private String invoiceTimestamp;
    private String statusPay;
    private byte[] paymentImage;

    public Invoice(String invoiceID, String orderID, int invoicePrice, String invoiceTimestamp, String statusPay, byte[] paymentImage) {
        this.invoiceID = invoiceID;
        this.orderID = orderID;
        this.invoicePrice = invoicePrice;
        this.invoiceTimestamp = invoiceTimestamp;
        this.statusPay = statusPay;
        this.paymentImage = paymentImage;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public String getOrderID() {
        return orderID;
    }

    public int getInvoicePrice() {
        return invoicePrice;
    }

    public String getInvoiceTimestamp() {
        return invoiceTimestamp;
    }

    public String getStatusPay() {
        return statusPay;
    }

    public byte[] getPaymentImage() {
        return paymentImage;
    }
}
