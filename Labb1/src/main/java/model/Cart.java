package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product product) {
        CartItem existingItem = items.get(product.getId());
        if (existingItem == null) {
            items.put(product.getId(), new CartItem(product, 1));
        } else {
            existingItem.increaseQuantity();
        }
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.getRowTotal());
        }
        return total;
    }
}
