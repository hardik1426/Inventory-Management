package com.GTC.khatabook.entity;

import com.GTC.khatabook.enums.UnitType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Parent purchase.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    /*
     * Actual item from Item master.
     *
     * We store item_id rather than item name.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    /*
     * Quantity purchased.
     *
     * Example:
     * 17 PCS
     * 500 KG
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 3
    )
    private BigDecimal quantity;

    /*
     * Unit used for THIS purchase.
     *
     * It can be different from the item's default unit.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UnitType unit;

    /*
     * Purchase rate per unit.
     *
     * Example:
     * 17 PCS × ₹1,150
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal rate;

    /*
     * CGST percentage.
     *
     * Example: 2.5
     */
    @Column(
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal cgstPercentage;

    /*
     * Calculated CGST amount.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal cgstAmount;

    /*
     * SGST percentage.
     */
    @Column(
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal sgstPercentage;

    /*
     * Calculated SGST amount.
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal sgstAmount;

    /*
     * Final amount for this item.
     *
     * quantity × rate + CGST + SGST
     */
    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal total;
}