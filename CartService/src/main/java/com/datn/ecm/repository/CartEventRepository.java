package com.datn.ecm.repository;

import com.datn.ecm.model.CartEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface CartEventRepository extends CrudRepository<CartEvent, Long> {

    @Query(value = "SELECT c.* \n" +
            "FROM ( SELECT * FROM ecm_cart.cart_event \n" +
            "       WHERE owner_id = :ownerId  AND (cart_event_type = \"CLEAR_CART\" OR cart_event_type = \"CHECK_OUT\") \n" +
            "       ORDER BY cart_event.created_at DESC \n" +
            "       LIMIT 1) t\n" +
            "RIGHT JOIN ecm_cart.cart_event c ON c.owner_id = t.owner_id\n" +
            "WHERE c.created_at BETWEEN coalesce(t.created_at, 0) AND 9223372036854775807 AND coalesce(t.id, -1) != c.id AND c.owner_id = :ownerId"
            , nativeQuery = true)
    Stream<CartEvent> getCartEventStreamByUser(@Param("ownerId") String ownerId);

    //List<CartEvent> findByOwnerId(String sessionid);

}