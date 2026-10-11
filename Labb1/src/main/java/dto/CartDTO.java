package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartDTO implements Serializable {

    private final List<CartItemDTO> items;
    private final BigDecimal totalPrice;

    public CartDTO(List<CartItemDTO> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }
}