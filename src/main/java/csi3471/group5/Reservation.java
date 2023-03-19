package org.example;
import java.util.Date;
public class Reservation {
       Date startDate = new Date();
       Date endDate = new Date();
       boolean isCorporate, isActive;
       public enum paymentStatus{NOT_PAID, PAYMENT_PROCESSING, PAYMENT_PROCESSED};

       paymentStatus currPaymentStatus;

    public void setStartDate(Date d){
           startDate = d;
    }
    public void setEndDate(Date d){
           endDate = d;
    }

    public void setCorporate(boolean b){
           isCorporate = b;
    }
    public void setActive(boolean b){
           isActive = b;
    }

    public void setCurrPaymentStatus(paymentStatus p){
        currPaymentStatus = p;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isCorporate() {
        return isCorporate;
    }

    public boolean isActive() {
        return isActive;
    }

    public paymentStatus getCurrPaymentStatus() {
        return currPaymentStatus;
    }
}