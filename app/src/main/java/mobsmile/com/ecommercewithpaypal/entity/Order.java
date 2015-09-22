package mobsmile.com.ecommercewithpaypal.entity;

/**
 * Created by Baris on 04.9.2015.
 * Serializable Entity Class of Order
 * @author Baris Sarikaya
 * @version 1.0
 */
public class Order {

    private Integer id;
    private String title;
    private String detail;
    private Integer price;
    private Integer quantity;

    public Order(Integer id, String title, String detail, Integer price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return !(id != null ? !id.equals(order.id) : order.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
