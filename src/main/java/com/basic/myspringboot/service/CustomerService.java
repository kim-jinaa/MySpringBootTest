package com.basic.myspringboot.service;

import com.basic.myspringboot.dto.CustomerReqDTO;
import com.basic.myspringboot.dto.CustomerResDTO;
import com.basic.myspringboot.entity.Customer;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerResDTO saveCustomer(CustomerReqDTO customerReqDTO){
        Customer customer = modelMapper.map(customerReqDTO, Customer.class);
        Customer saveCustomer = customerRepository.save(customer);
        return modelMapper.map(saveCustomer, CustomerResDTO.class);
    }

    //아이디로조회
    @Transactional(readOnly = true)
    public CustomerResDTO getCustomerById(Long id){
        Customer customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(id + " User Not Found", HttpStatus.NOT_FOUND));
        CustomerResDTO customerResDTO = modelMapper.map(customerEntity, CustomerResDTO.class);
        return customerResDTO;
    }

    //이메일로조회
    @Transactional(readOnly = true)
    public CustomerResDTO getCustomerByEmail(String eamil){
        Customer customerEntity = customerRepository.findByEmail(eamil)
                .orElseThrow(() -> new BusinessException(eamil + " User Not Found", HttpStatus.NOT_FOUND));
        CustomerResDTO customerResDTO = modelMapper.map(customerEntity, CustomerResDTO.class);
        return customerResDTO;
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<CustomerResDTO> getCustomer(){
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResDTO> customerResDTOList = customerList.stream()
                .map(customer -> modelMapper.map(customer, CustomerResDTO.class))
                .collect(toList());
        return customerResDTOList;
    }

    //수정 update patch
    public CustomerResDTO updateCustomer(String email, CustomerReqDTO customerReqDto){
        Customer existCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException(email + " User Not Found", HttpStatus.NOT_FOUND));
        existCustomer.setName(customerReqDto.getName());
        existCustomer.setAge(customerReqDto.getAge());
        return modelMapper.map(existCustomer, CustomerResDTO.class);
    }

    //삭제 delete
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(id + " User Not Found", HttpStatus.NOT_FOUND));
        customerRepository.delete(customer);
    }

    public void updateCustomerForm(CustomerReqDTO customerReqDTO){
        Customer existCustomer = customerRepository.findById(customerReqDTO.getId())
                .orElseThrow(() ->
                        new BusinessException(customerReqDTO.getId() + " User Not Found", HttpStatus.NOT_FOUND));
        existCustomer.setName(customerReqDTO.getName());
        existCustomer.setAge(customerReqDTO.getAge());
    }

}
