package pojo;

public class CreateOrderResponseJson extends AbstractPojo {

    private boolean success;
    private String name;
    private OrderDetailsJson order;

    public CreateOrderResponseJson(boolean success, String name, OrderDetailsJson order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }

    public CreateOrderResponseJson() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderDetailsJson getOrder() {
        return order;
    }

    public void setOrder(OrderDetailsJson order) {
        this.order = order;
    }
}
