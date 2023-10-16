package com.basic.myspringboot.controller;

import com.basic.myspringboot.dto.CustomerReqDTO;
import com.basic.myspringboot.dto.CustomerResDTO;
import com.basic.myspringboot.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    //post 입력
    @PostMapping
    public CustomerResDTO saveCustomer(@RequestBody CustomerReqDTO customerReqDTO){
        return customerService.saveCustomer(customerReqDTO);
    }

    // post 1개조회(id)
    @PostMapping("/{id}")
    public CustomerResDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // post 1개조회(email)
    @PostMapping("/email/{email}")
    public CustomerResDTO getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    //get 전체조회
    @GetMapping
    public List<CustomerResDTO> getCustomers(){
        return customerService.getCustomer();
    }

    //update 수정 patch
    @PatchMapping("/{email}")
    public CustomerResDTO updateCustomer(@PathVariable String email, @RequestBody CustomerReqDTO customerReqDTO){
        return customerService.updateCustomer(email, customerReqDTO);
    }

    //delete 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(id + "번 Customer이 삭제처리 되었습니다.");
    }

}
