package com.basic.myspringboot.controller;

import com.basic.myspringboot.dto.CustomerReqDTO;
import com.basic.myspringboot.dto.CustomerResDTO;
import com.basic.myspringboot.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customerpage")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/index")
    public ModelAndView index() {
        List<CustomerResDTO> customerResDTOList = customerService.getCustomer();
        return new ModelAndView("index","customer",customerResDTOList);
    }

    //등록페이지 호출
    @GetMapping("/signup")
    public String showSignUpForm(CustomerReqDTO customer) {
        return "add-customer";
    }

    //입력항목 검증 후, 등록처리를 해주는 메서드
    @PostMapping("/addcustomer")
    public String addCustomer(@Valid CustomerReqDTO customer, BindingResult result, Model model) {
        //입력항목 검증 오류가 발생했나요?
        if (result.hasErrors()) {
            return "add-customer";
        }
        //등록요청
        customerService.saveCustomer(customer);
        return "redirect:/customerpage/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateFrom(@PathVariable Long id, Model model){
        CustomerResDTO customerResDTO = customerService.getCustomerById(id);
        model.addAttribute("customer", customerResDTO);
        return "update-customer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") long id, @Valid CustomerReqDTO customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(">>>> hasErrors customer " + customer);
            model.addAttribute("customer", customer);
            return "update-customer";
        }
        customerService.updateCustomerForm(customer);
        return "redirect:/customerpage/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id){
        customerService.deleteCustomer(id);
        return "redirect:/customerpage/index";
    }


}
