/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Payment {
    private int id;
    private int orderId;
    private String method;
    private double amount;
    private String transactionId;
    private String status;

    public Payment(int orderId, String method, double amount, String transactionId, String status) {
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getter/setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public String getMethod() { return method; }
    public double getAmount() { return amount; }
    public String getTransactionId() { return transactionId; }
    public String getStatus() { return status; }

    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setMethod(String method) { this.method = method; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setStatus(String status) { this.status = status; }
}

