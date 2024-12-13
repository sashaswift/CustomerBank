package com.example.banksystemcustomers.controller;

import com.example.banksystemcustomers.conService.*;
import com.example.banksystemcustomers.mapper.*;
import com.example.banksystemcustomers.service.IPoiAccountService;
import com.example.banksystemcustomers.service.IPoiCustomerService;
import com.example.banksystemcustomers.service.IPoiDepositService;
import com.example.banksystemcustomers.service.IPoiTransactionService;
import com.example.banksystemcustomers.vo.*;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/customer")
public class customerController {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DepositMapper depositMapper;
    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private DepositManagerMapper depositManagerMapper;

    @Autowired
    private IPoiCustomerService poiCustomerService;

    @Autowired
    private IPoiAccountService poiAccountService;

    @Autowired
    private IPoiDepositService poiDepositService;

    @Autowired
    private IPoiTransactionService poiTransactionService;

    @GetMapping("/getmyinformation")
    public Result GetMyInformation( String id ){
        //personalID or employeeID,用来获取员工的个人信息
        List<CustomerInformation> customerInformations=customerMapper.getById(id);
        //List<CustomerInformation> customerInformations=poiCustomerService.getById(employeeid);
        List<CustomerVo> customerVos=new ArrayList<>();
        for(CustomerInformation customerInformation:customerInformations){
            CustomerVo customerVo=new CustomerVo();
            BeanUtils.copyProperties(customerInformation,customerVo);
            customerVos.add(customerVo);
        }
        return Result.success(customerVos);
    }

    @GetMapping("/loggingtestify")
    public Result loggingtestify(String id,String passwd){
        //personalID or employeeID,用来获取员工的个人信息
        List<CustomerInformation> customerInformations=customerMapper.getByidAndPwd(id,passwd);
        //List<CustomerInformation> customerInformations=poiCustomerService.getByidAndPwd(id,passwd);
        List<CustomerVo> customerVos=new ArrayList<>();
        for(CustomerInformation customerInformation:customerInformations){
            CustomerVo customerVo=new CustomerVo();
            BeanUtils.copyProperties(customerInformation,customerVo);
            customerVos.add(customerVo);
        }
        return Result.success(customerVos);
    }

    @PostMapping("/signup")
    public Result SignUp(@RequestBody CustomerInformation customerinformation){
        poiCustomerService.save(customerinformation);
        //customerMapper.addCustomer(customerinformation);
        List <CustomerInformation> customerInformations=customerMapper.getById(customerinformation.personid);
        List<CustomerVo> customerVos=new ArrayList<>();
        for(CustomerInformation customerInformation1:customerInformations){
            CustomerVo customerVo=new CustomerVo();
            BeanUtils.copyProperties(customerInformation1,customerVo);
            customerVos.add(customerVo);
        }
        return Result.success(customerVos);
    }

    @PutMapping("/changemyinformation")
    public Result ChangeMyInformation(@RequestBody CustomerInformation customerinformation){
        //customerMapper.updateCustomer(customerinformation);
        poiCustomerService.updateById(customerinformation);
        List <CustomerInformation> customerInformations=customerMapper.getById(customerinformation.personid);
        List<CustomerVo> customerVos=new ArrayList<>();
        for(CustomerInformation customerInformation1:customerInformations){
            CustomerVo customerVo=new CustomerVo();
            BeanUtils.copyProperties(customerInformation1,customerVo);
            customerVos.add(customerVo);
        }
        return Result.success(customerVos);
    }

    @GetMapping("/getuseraccount")
    public Result GetUserAccount(String cardid,String cardpasswd){
        //根据账户和密码来确定是否满足用户信息，并且返回相应的用户信息
        List<AccountInformation> accountInformations=accountMapper.GetByIDAndPwd(cardid,cardpasswd);
        List<AccountVo> accountVos=new ArrayList<>();
        for(AccountInformation accountInformation:accountInformations){
            AccountVo accountVo=new AccountVo();
            BeanUtils.copyProperties(accountInformation,accountVo);
            accountVos.add(accountVo);
        }
        return Result.success(accountVos);
    }
    @GetMapping("/getuseraccountbyid")
    public Result GetUserAccountById(String cardid){
        //根据账户来确定是否满足用户信息
        List<AccountInformation> accountInformations=accountMapper.GetByID(cardid);
        //List<AccountInformation> accountInformationspoiAccountService.getById(cardid);
        List<AccountVo> accountVos=new ArrayList<>();
        for(AccountInformation accountInformation:accountInformations){
            AccountVo accountVo=new AccountVo();
            BeanUtils.copyProperties(accountInformation,accountVo);
            accountVos.add(accountVo);
        }
        return Result.success(accountVos);
    }

    @PostMapping("/addaccount")
    public Result AddAccount(@RequestBody AccountInformation anaccountinformation){
        //开户
        CustomerInformation customerInformation=poiCustomerService.getById(anaccountinformation.personid);
        if(customerInformation==null) return Result.fail("身份证号验证错误！");
        BankCardGenerator bankCardGenerator=new BankCardGenerator();
        String cardid;
        List<AccountInformation> accountInformations;
        do{
            cardid=bankCardGenerator.generateCardNumber();
            accountInformations=accountMapper.GetByID(anaccountinformation.cardid);
        }while(!accountInformations.isEmpty());
        anaccountinformation.setCardid(cardid);
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.from(now.atZone(ZoneOffset.UTC).toInstant());
        anaccountinformation.setOpendate(timestamp);
        poiAccountService.save(anaccountinformation);
        accountInformations=accountMapper.GetByID(cardid);
        //accountMapper.AddAccount(anaccountinformation);
        // accountInformations=accountMapper.GetByID(anaccountinformation.cardid);
        //List<AccountInformation> accountInformationspoiAccountService.getById(anaccountinformation.cardid);
        List<AccountVo> accountVos=new ArrayList<>();
        for(AccountInformation accountInformation:accountInformations){
            AccountVo accountVo=new AccountVo();
            BeanUtils.copyProperties(accountInformation,accountVo);
            accountVos.add(accountVo);
        }
        return Result.success(accountVos);
    }

    @GetMapping("/getdepositmethods")
    public Result GetDepositMethods(){
        List<DepositManageInformation> depositManageInformations=depositManagerMapper.GetAll();
        List<DepositMagageVo> depositMagageVos=new ArrayList<>();
        for(DepositManageInformation depositManageInformation:depositManageInformations){
            DepositMagageVo depositMagageVo=new DepositMagageVo();
            BeanUtils.copyProperties(depositManageInformation,depositMagageVo);
            depositMagageVos.add(depositMagageVo);
        }
        return Result.success(depositMagageVos);
    }

    @PostMapping("/createdeposit")
    public Result CreateDeposit(@RequestBody DepositInformationUser depositinformationuser){
        //进行存款，并设置存款金额
        if(depositinformationuser.depositbalance.compareTo(new BigDecimal("50000"))>0) return Result.fail("金额过大");
        String depositid=depositManagerMapper.GetByRemark("活期存款").get(0);
        if(depositinformationuser.depositid.equals(depositid)) {//如果为活期存款
            //List <DepositInformationUser> depositInformationUsers=depositMapper.GetById(depositinformationuser.id);
            //depositinformationuser.depositbalance.add(depositInformationUsers.get(0).depositbalance);
            depositMapper.UpdateDepositAdd(depositid,depositinformationuser.cardid,depositinformationuser.depositbalance);
        }else{
            //poiDepositService.save(depositinformationuser);
            //depositMapper.AddDeposit(depositinformationuser);
            depositMapper.AddDeposit(depositid,depositinformationuser.cardid,depositinformationuser.depositbalance);
        }
        //depositMapper.AddDeposit(depositinformationuser);
        List <DepositInformationUser> depositInformationUsers=depositMapper.GetByIdAndKinds(depositinformationuser.cardid,"活期存款");
        //List <DepositInformationUser> depositInformationUsers=poiDepositService.getById(depositinformationuser.id);
        List <DepositVo> depositVos=new ArrayList<>();
        for(DepositInformationUser adepositInformationUser:depositInformationUsers){
            DepositVo depositVo=new DepositVo();
            BeanUtils.copyProperties(adepositInformationUser,depositVo);
            depositVos.add(depositVo);
        }

        return Result.success(depositVos);
    }

    @PutMapping("/changedeposit")
    public Result ChangeDeposit(@RequestBody DepositInformationUser depositinformationuserinflex){
        //存款策略更改(只能活期改定期)
        DepositInformationUser depositinformationuserflex=depositMapper.GetByIdAndKinds(depositinformationuserinflex.cardid,"活期存款").get(0);
        //depositinformationuserflex.depositbalance=depositinformationuserflex.depositbalance.subtract(depositinformationuserinflex.depositbalance);
        //poiDepositService.updateById(depositinformationuserflex);
        depositMapper.UpdateDepositWith(depositinformationuserflex.depositid,depositinformationuserflex.cardid,depositinformationuserinflex.depositbalance);

        List <DepositInformationUser> depositInformationUsers=depositMapper.GetById(depositinformationuserflex.id);
        //List <DepositInformationUser> depositInformationUsers=poiDepositService.getById(depositinformationuserflex.id);
        List <DepositVo> depositVos=new ArrayList<>();
        for(DepositInformationUser adepositInformationUser:depositInformationUsers){
            DepositVo depositVo=new DepositVo();
            BeanUtils.copyProperties(adepositInformationUser,depositVo);
            depositVos.add(depositVo);
        }
        depositInformationUsers.clear();
        //poiDepositService.save(depositinformationuserinflex);
        depositMapper.AddDeposit(depositinformationuserinflex.depositid,depositinformationuserinflex.cardid,depositinformationuserinflex.depositbalance);

        return Result.success(depositVos);
    }

    @GetMapping("/getdeposit")
    public Result GetDeposit(String id){
        //查看某一具体存款
        List <DepositInformationUser> depositInformationUsers=depositMapper.GetById(id);
        //List <DepositInformationUser> depositInformationUsers=poiDepositService.getById(id);
        List <DepositVo> depositVos=new ArrayList<>();
        for(DepositInformationUser depositInformationUser:depositInformationUsers){
            DepositVo depositVo=new DepositVo();
            BeanUtils.copyProperties(depositInformationUser,depositVo);
            depositVos.add(depositVo);
        }
        return Result.success(depositVos);
    }

    @GetMapping("/getdepositbalances")
    public Result GetDepositBalances(String cardid){
        //查看不同存款策略的余额，定期存款余额可以根据存款记录获得(因为定期不可取)
        List <DepositInformationUser> depositInformationUsers=depositMapper.GetAllKinds(cardid);
        //List <DepositInformationUser> depositInformationUsers=poiDepositService.getById(depositinformationuser.id);
        List <DepositVo> depositVos=new ArrayList<>();
        for(DepositInformationUser adepositInformationUser:depositInformationUsers){
            DepositVo depositVo=new DepositVo();
            BeanUtils.copyProperties(adepositInformationUser,depositVo);
            depositVos.add(depositVo);
        }

        return Result.success(depositVos);
    }

    @PutMapping("/withdrawlbalance")
    public Result WithdrawlBalance(String cardid, BigDecimal out){
        //取款
        if(out.compareTo(new BigDecimal("50000"))>0) return Result.fail("金额过大");
        BigDecimal bigDecimal=poiAccountService.getById(cardid).getBalance();
        if(out.compareTo(bigDecimal)>0) return Result.fail("金额过大");
        String remark="活期存款";
        DepositInformationUser newdepositInformationUser=depositMapper.GetByIdAndKinds(cardid,remark).get(0);
        newdepositInformationUser.depositbalance.subtract(out);
        depositMapper.UpdateDepositWith(newdepositInformationUser.depositid,cardid,out);
        //poiDepositService.updateById(newdepositInformationUser);
        List <DepositInformationUser> depositInformationUsers=depositMapper.GetById(newdepositInformationUser.id);
        //List <DepositInformationUser> depositInformationUsers=poiDepositService.getById(depositinformationuserflex.id);
        List <DepositVo> depositVos=new ArrayList<>();
        for(DepositInformationUser adepositInformationUser:depositInformationUsers){
            DepositVo depositVo=new DepositVo();
            BeanUtils.copyProperties(adepositInformationUser,depositVo);
            depositVos.add(depositVo);
        }

        return Result.success(depositVos);
    }

    @PostMapping("/transaction")
    public Result Transaction(@RequestBody TransactionItem transactionitem){
        if(transactionitem.amount.compareTo(new BigDecimal("50000"))>0) return Result.fail("超出限制金额");
        BigDecimal bigDecimal=poiAccountService.getById(transactionitem.acpcardid).getBalance();
        if(transactionitem.amount.compareTo(bigDecimal)>0) return Result.fail("金额过大");
        String vcardid=transactionitem.getAcpcardid();
        // 获取当前时间
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        // 格式化日期时间为 HHmmssSSSSSS 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssSSSSSS");
        String formattedTime = now.format(formatter);
        // 截取前6位毫秒部分（如果需要，可以保留全部6位或调整）
        // 这里我们假设保留全部6位微秒
        String timeWithMicroseconds = formattedTime.substring(0, 12); // HHmmssSSSSSS

        // 构建最终的视频ID
        String vid = vcardid + timeWithMicroseconds;
        //LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.from(now.atZone(ZoneOffset.UTC).toInstant());
        transactionitem.setTransactiondate(timestamp);
        transactionitem.setAccountid(vid);
        poiTransactionService.save(transactionitem);
        //transactionMapper.AddTransaction(transactionitem);
        return Result.success();
    }

    @GetMapping("/getalltransaction")
    public Result GetAllTransaction(String cardid){
        List<TransactionItem> transactionItems=transactionMapper.GetAllTransaction(cardid);
        List<TransactionVO> transactionVOS=new ArrayList<>();
        for(TransactionItem transactionItem:transactionItems){
            TransactionVO transactionVO=new TransactionVO();
            BeanUtils.copyProperties(transactionItem,transactionVO);
            transactionVOS.add(transactionVO);
        }
        return Result.success(transactionVOS);
    }



}
