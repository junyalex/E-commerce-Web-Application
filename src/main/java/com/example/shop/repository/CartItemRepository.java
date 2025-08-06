package com.example.shop.repository;

import com.example.shop.dto.CartDetailDTo;
import com.example.shop.dto.CartItemDto;
import com.example.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    /**
     * Retrieves detailed cart entries for a given cart, including:
     * - CartItem ID
     * - Item name and price
     * - Quantity in cart
     * - URL of the representative image
     *
     * @param cartId the ID of the cart to fetch
     * @return a list of CartDetailDto with full item and image details
     */
    @Query("select new com.example.shop.dto.CartDetailDTo(ci.id, i.itemName, i.price, ci.count, im.imgUrl, i.id)"
            + " from CartItem ci, ItemImg im "
            + "join ci.item i "
            + "where ci.cart.id = :cartId "
            + "and im.item.id = ci.item.id "
            + "and im.repImgYn = 'Y' "
            + "order by ci.regTime desc"
    )
    List<CartDetailDTo> findCartDetailDtoByCartId(Long cartId);}
