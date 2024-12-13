package com.example.banksystemcustomers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.banksystemcustomers.conService.EmployeeInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<EmployeeInformation> {
    List<EmployeeInformation> getById(@Param("employeeid") String employeeid);
    List<EmployeeInformation> getByidAndPwd(@Param("employeeid") String employeeid,@Param("employeepwd") String employeepwd);

}
