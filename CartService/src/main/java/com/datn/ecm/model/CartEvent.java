package com.datn.ecm.model;

import com.datn.ecm.constant.CartEventType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart_event", indexes = {@Index(name = "IDX_CART_EVENT_USER", columnList = "id,ownerId")})
@Data
public class CartEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private CartEventType cartEventType;

    private String ownerId;

    private long clothesId;

    private int quantity;

    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long lastModified;

    public CartEvent() {

    }

    @Builder
    public CartEvent(CartEventType cartEventType, String ownerId, long clothesId, int quantity) {
        this.cartEventType = cartEventType;
        this.ownerId = ownerId;
        this.clothesId = clothesId;
        this.quantity = quantity;
    }

}
