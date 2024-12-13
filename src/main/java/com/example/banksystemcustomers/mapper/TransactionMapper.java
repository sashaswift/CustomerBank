package com.example.banksystemcustomers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.banksystemcustomers.conService.TransactionItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionMapper extends BaseMapper<TransactionItem> {
    void AddTransaction(@Param("transactionItem") TransactionItem transactionItem);
    List<TransactionItem> GetAllTransaction(@Param("cardid") String cardid);
}
