package com.github.louchen.zoom.controller.admin;

import com.github.louchen.zoom.api.order.model.Order;
import com.github.louchen.zoom.api.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器 - 订单
 *
 * @author louchen
 */
@RestController
@RequestMapping("/api/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Order> getList(String name, Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Order> orders = orderService.findByPage(pageRequest);
        orders.forEach(o->{
            if(o.getMember()!=null){
                o.getMember().toString();
            }
            if(o.getStore()!=null){
                o.getStore().toString();
            }
        });
        return orders;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order get(@PathVariable Long id) {
        Order o = orderService.find(id);
        if(o.getMember()!=null){
            o.getMember().toString();
        }
        if(o.getStore()!=null){
            o.getStore().toString();
        }
        return o;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Order add(@RequestBody Order orders) {
        return orderService.save(orders);
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Order update(@RequestBody Order orders) {
        return orderService.update(orders);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        orderService.delete(id);
    }

}
