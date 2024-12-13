package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.CustomerInformation;
import com.example.banksystemcustomers.mapper.CustomerMapper;
import com.example.banksystemcustomers.service.IPoiCustomerService;
import org.springframework.stereotype.Service;

@Service
public class PoiCustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerInformation> implements IPoiCustomerService {
}
