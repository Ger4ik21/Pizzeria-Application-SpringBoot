package com.modsen.pizzeria.controller;

import com.modsen.pizzeria.swagger.OrderApi;
import com.modsen.pizzeria.domain.OrderStatus;
import com.modsen.pizzeria.dto.request.CreateOrderRequest;
import com.modsen.pizzeria.dto.request.UpdateOrderRequest;
import com.modsen.pizzeria.dto.response.OrderResponse;
import com.modsen.pizzeria.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest){
        return orderService.createOrder(createOrderRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public OrderResponse updateOrder(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderRequest updateOrderRequest
    ){
        return orderService.updateOrder(id, updateOrderRequest);
    }

    @PutMapping("/{id}/update-status")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return orderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public OrderResponse getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }

}
