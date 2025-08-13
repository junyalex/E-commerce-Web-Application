package com.example.shop.controller;

import com.example.shop.dto.OrderHistoryDto;
import com.example.shop.service.MemberService;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/orders")
    public String orderHistory(Principal principal, Model model) {
        List<OrderHistoryDto> orderHistory = orderService.getOrderHistory(principal.getName());
        model.addAttribute("orders", orderHistory);

        return "order/orderHistory";
    }

    /**
     * Cancels order with given orderId and email
     */
    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity<Long> cancelOrder(
            @PathVariable("orderId") Long orderId,
            Principal principal
    ){
        if(!orderService.validateOrder(orderId, principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(orderId);
    }

}
