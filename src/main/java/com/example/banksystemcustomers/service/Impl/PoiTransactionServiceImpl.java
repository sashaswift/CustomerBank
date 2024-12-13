package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.TransactionItem;
import com.example.banksystemcustomers.mapper.EmployeeMapper;
import com.example.banksystemcustomers.mapper.TransactionMapper;
import com.example.banksystemcustomers.service.IPoiTransactionService;
import org.springframework.stereotype.Service;

@Service
public class PoiTransactionServiceImpl extends ServiceImpl<TransactionMapper, TransactionItem> implements IPoiTransactionService {
}
