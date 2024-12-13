package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.AccountInformation;
import com.example.banksystemcustomers.mapper.AccountMapper;
import com.example.banksystemcustomers.service.IPoiAccountService;
import org.springframework.stereotype.Service;

@Service
public class PoiAccountServiceImpI extends ServiceImpl<AccountMapper, AccountInformation> implements IPoiAccountService {
}
