package com.example.plusteamproject.domain.product.entity;

import com.example.plusteamproject.common.BaseEntity;
import com.example.plusteamproject.common.Status;
import com.example.plusteamproject.domain.product.dto.ProductUpdateRequestDto;
import com.example.plusteamproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private String name;

    @Column
    private String content;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean isDeleted = Status.EXIST.isValue();

//    @OneToMany(mappedBy = "product")
//    private List<Report> reports = new ArrayList<>();

    private Product(ProductCategory category, String name, String content, BigDecimal price, Long quantity, User user) {
        this.category = category;
        this.name = name;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public static Product of(ProductCategory category, String name, String content, BigDecimal price, Long quantity, User user) {
        return new Product(category, name, content, price, quantity, user);
    }

    public void update(ProductUpdateRequestDto dto) {
        if (dto.getProductCategory() != null)
            this.category = dto.getProductCategory();
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getContent() != null)
            this.content = dto.getContent();
        if (dto.getPrice() != null)
            this.price = dto.getPrice();
        if (dto.getQuantity() != null)
            this.quantity = dto.getQuantity();
    }

    public void updateDeleteStatus(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
