package com.example.banksystemcustomers.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.banksystemcustomers.conService.DepositInformationUser;
import com.example.banksystemcustomers.mapper.DepositMapper;
import com.example.banksystemcustomers.service.IPoiDepositService;
import org.springframework.stereotype.Service;

@Service
public class PoiDepositServiceImpl extends ServiceImpl<DepositMapper, DepositInformationUser> implements IPoiDepositService {
}
