package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.Customer;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerBasicRestController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public Customer create(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("요청하신 id에 해당하는 Customer이 없습니다.", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public Customer getCustomerByEmail(@PathVariable String email){
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("요청하신 email에 해당하는 Customer이 없습니다.", HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
        customerRepository.delete(customer);
        return ResponseEntity.ok(id + "번 Customer가 삭제되었습니다.");
    }

    @DeleteMapping("email/{email}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Customer Not Found", HttpStatus.NOT_FOUND));
        customerRepository.delete(customer);
        return ResponseEntity.ok(email + "을 가진 Customer가 삭제되었습니다.");
    }



}
