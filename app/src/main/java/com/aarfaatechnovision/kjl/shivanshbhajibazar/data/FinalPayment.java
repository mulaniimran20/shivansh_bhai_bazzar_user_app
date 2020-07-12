package com.aarfaatechnovision.kjl.shivanshbhajibazar.data;

public class FinalPayment {

    public static String finalPayment, orderID;

    public static String getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(String finalPayment) {
        this.finalPayment = finalPayment;
    }

    public static String getOrderID() {
        return orderID;
    }

    public static void setOrderID(String orderID) {
        FinalPayment.orderID = orderID;
    }
}
