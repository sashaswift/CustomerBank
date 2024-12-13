package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.DepositManageInformation;
import com.example.banksystemcustomers.mapper.DepositManagerMapper;
import com.example.banksystemcustomers.service.IPoiDepositManagerService;
import org.springframework.stereotype.Service;

@Service
public class PoiDepositManagerServiceImpl extends ServiceImpl<DepositManagerMapper, DepositManageInformation> implements IPoiDepositManagerService {
}
