package dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItemDTO implements Serializable {

    private final int productId;
    private final String productName;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal rowTotal;

    public CartItemDTO(int productId,
                       String productName,
                       BigDecimal price,
                       int quantity,
                       BigDecimal rowTotal) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.rowTotal = rowTotal;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getRowTotal() {
        return rowTotal;
    }
}