package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Getter @Setter @ToString
public class Cart {

    @Id @GeneratedValue
    @Column(name="cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
}
