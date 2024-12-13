package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.EmployeeInformation;
import com.example.banksystemcustomers.mapper.EmployeeMapper;
import com.example.banksystemcustomers.service.IPoiEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class PoiEmployeeServiceImpl extends ServiceImpl<EmployeeMapper,EmployeeInformation> implements IPoiEmployeeService {
}
