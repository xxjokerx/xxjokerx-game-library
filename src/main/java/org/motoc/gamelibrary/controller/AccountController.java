package org.motoc.gamelibrary.controller;


import org.motoc.gamelibrary.business.AccountService;
import org.motoc.gamelibrary.dto.AccountDto;
import org.motoc.gamelibrary.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Defines account endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService service;

    private final AccountMapper mapper;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
        this.mapper = AccountMapper.INSTANCE;
    }

    @GetMapping("/admin/accounts/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/accounts")
    List<AccountDto> findAll() {
        logger.trace("findAll() called");
        return mapper.accountsToDto(service.findAll());
    }

    @GetMapping("/admin/accounts/{id}")
    AccountDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.accountToDto(service.findById(id));
    }

    @GetMapping("/admin/accounts/page")
    Page<AccountDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/accounts")
    AccountDto save(@RequestBody @Valid AccountDto account,
                    @RequestParam(value = "has-contact", required = false) boolean hasContact) {
        logger.trace("save(account) called");
        return mapper.accountToDto(
                service.save(mapper.dtoToAccount(account), hasContact)
        );
    }

    @GetMapping("/admin/accounts/no-current-loan")
    Page<AccountDto> findAccountsWithNoCurrentLoan(Pageable pageable) {
        logger.trace("findAccountsWithNoCurrentLoan() called");
        return mapper.pageToPageDto(service.findAccountsWithNoCurrentLoan(pageable));
    }
}
