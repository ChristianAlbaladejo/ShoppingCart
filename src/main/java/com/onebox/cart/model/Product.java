package com.onebox.cart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document()
public class Product {
    @Id
    private String id;
    @NonNull
    @JsonProperty("description")
    private String description;
    @NonNull
    @DecimalMin(value = "0.0", inclusive = false)
    @JsonProperty("amount")
    private BigDecimal amount;
    @NonNull
    @Min(0)
    @Max(99)
    private int quantity;
}
