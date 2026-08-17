package com.GTC.khatabook.service.impl;

import com.GTC.khatabook.dto.request.CustomerRequest;
import com.GTC.khatabook.dto.response.CustomerResponse;
import com.GTC.khatabook.entity.Customer;
import com.GTC.khatabook.repository.CustomerRepository;
import com.GTC.khatabook.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException(
                    "Customer already exists with mobile number: "
                            + request.getMobile()
            );
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setMobile(request.getMobile());
        customer.setAddress(request.getAddress());
        customer.setGstNumber(request.getGstNumber());
        customer.setActive(true);

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id: " + id
                        )
                );

        return mapToResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request
    ) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id: " + id
                        )
                );

        if (!customer.getMobile().equals(request.getMobile())
                && customerRepository.existsByMobile(request.getMobile())) {

            throw new RuntimeException(
                    "Another customer already exists with mobile number: "
                            + request.getMobile()
            );
        }

        customer.setName(request.getName());
        customer.setMobile(request.getMobile());
        customer.setAddress(request.getAddress());
        customer.setGstNumber(request.getGstNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id: " + id
                        )
                );

        customer.setActive(false);

        customerRepository.save(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobile(customer.getMobile())
                .address(customer.getAddress())
                .gstNumber(customer.getGstNumber())
                .active(customer.getActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}