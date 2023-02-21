package pojo;

import java.util.List;

public class ListOfOrdersForUserJson extends AbstractPojo {

    private boolean success;
    private List<OrdersInListOfOrdersJson> orders;
    private int total;
    private int totalToday;

    public ListOfOrdersForUserJson(boolean success, List<OrdersInListOfOrdersJson> orders, int total, int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public ListOfOrdersForUserJson() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrdersInListOfOrdersJson> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersInListOfOrdersJson> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}
