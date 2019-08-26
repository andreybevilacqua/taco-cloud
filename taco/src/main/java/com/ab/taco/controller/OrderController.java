package com.ab.taco.controller;

import com.ab.taco.model.Order;
import com.ab.taco.model.User;
import com.ab.taco.property.OrderProps;
import com.ab.taco.repo.OrderRepository;
import com.ab.taco.repo.UserRepository;
import com.ab.taco.service.message.JmsOrderMessagingService;
import com.ab.taco.service.message.RabbitOrderMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.ab.taco.service.util.ObjectCreator.buildOrder;

@Slf4j
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderProps orderProps;

    @Autowired
    private JmsOrderMessagingService jmsOrderMessagingService;

    @Autowired
    private RabbitOrderMessagingService rabbitOrderMessagingService;

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @GetMapping("/convertAndSend/order")
    public String convertAndSendOrder() {
        Order order = buildOrder();
//        jmsOrderMessagingService.sendOrder(order);
        rabbitOrderMessagingService.sendOrder(order);
        return "Convert and sent order";
    }

    @PostMapping
    public boolean processOrder(@Valid Order order, Errors errors, Principal principal, SessionStatus sessionStatus) {
        if(errors.hasErrors()) return false;
        log.info("Order submitted: " + order);

        User user = userRepository.findByUsername(principal.getName());

        order.setUser(user);
        orderRepository.save(order);

        sessionStatus.setComplete();

        // If you want to get security context anywhere in the application:
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();
        return true;
    }

    @GetMapping
    public List<Order> ordersFromUser(Principal principal) {
        User user = userRepository.findByUsername("user");
        return orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, orderProps.getPageSize()));
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseEntity<Order> patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent()) {
            if (patch.getName() != null) {
                order.get().setName(patch.getName());
            }
            if (patch.getStreet() != null) {
                order.get().setStreet(patch.getStreet());
            }
            if (patch.getCity() != null) {
                order.get().setCity(patch.getCity());
            }
            if (patch.getState() != null) {
                order.get().setState(patch.getState());
            }
            if (patch.getZip() != null) {
                order.get().setZip(patch.getZip());
            }
            if (patch.getCcNumber() != null) {
                order.get().setCcNumber(patch.getCcNumber());
            }
            if (patch.getCcExpiration() != null) {
                order.get().setCcExpiration(patch.getCcExpiration());
            }
            if (patch.getCcCVV() != null) {
                order.get().setCcCVV(patch.getCcCVV());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
