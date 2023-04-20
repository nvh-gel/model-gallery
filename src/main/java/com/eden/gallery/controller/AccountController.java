package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.AccountService;
import com.eden.gallery.utils.PageConverter;
import com.eden.gallery.viewmodel.AccountVM;
import com.eden.gallery.viewmodel.LogInData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for handling account.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;
    private final PageConverter<AccountVM> pageConverter = new PageConverter<>();

    /**
     * Create account.
     *
     * @param request creation request data
     * @return response of creation
     */
    @PostMapping
    public ResponseModel createAccount(@RequestBody AccountVM request) {

        return ResponseModel.created(accountService.createOnQueue(request));
    }

    /**
     * Get a list accounts.
     *
     * @return list of account
     */
    @GetMapping
    public ResponseModel getAllAccounts() {

        return pageConverter.toResponseFromIterable(accountService.findAll(1, 10));
    }

    /**
     * Get a list of accounts by input page and size.
     *
     * @param page page number
     * @param size page size
     * @return list of accounts
     */
    @GetMapping("/{page}/{size}")
    public ResponseModel getAllAccountByPage(@PathVariable Integer page, @PathVariable Integer size) {

        return pageConverter.toResponseFromIterable(accountService.findAll(page, size));
    }

    /**
     * Get a single account by id.
     *
     * @param id account id to find
     * @return found account
     */
    @GetMapping("/{id}")
    public ResponseModel getAccountById(@PathVariable Long id) {

        AccountVM result = accountService.findById(id);
        if (null == result) {
            return ResponseModel.notFound();
        }
        return ResponseModel.ok(result);
    }

    /**
     * Update an account.
     *
     * @param request update request data
     * @return updated response
     */
    @PutMapping
    public ResponseModel updateAccount(@RequestBody AccountVM request) {

        return ResponseModel.updated(accountService.updateOnQueue(request));
    }

    /**
     * Delete an account.
     *
     * @param id account id to delete
     * @return deletion response
     */
    @DeleteMapping("/{id}")
    public ResponseModel deleteAccount(@PathVariable Long id) {

        return ResponseModel.deleted(accountService.deleteOnQueue(id));
    }

    /**
     * Activate an account.
     *
     * @param id account id to activate
     * @return activation result
     */
    @PutMapping("/activate/{id}")
    public ResponseModel activateAccount(@PathVariable Long id) {

        return ResponseModel.updated(accountService.activateAccount(id));
    }

    /**
     * Deactivate an account.
     *
     * @param id account id to deactivate
     * @return deactivation result
     */
    @PutMapping("/deactivate/{id}")
    public ResponseModel deactivateAccount(@PathVariable Long id) {

        return ResponseModel.updated(accountService.deactivateAccount(id));
    }

    /**
     * Verify an account by id.
     *
     * @param id    account id
     * @param token verification token generated when creating account
     * @return verification result
     */
    @PutMapping("/verify/{id}")
    public ResponseModel verifyAccount(@PathVariable Long id, @RequestParam("token") String token) {

        String result = accountService.verifyAccount(id, token);
        if (null == result) {
            return ResponseModel.notFound();
        }
        return ResponseModel.updated(result);
    }

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LogInData logInData) {

        String result = accountService.login(logInData);
        if (null == result) {
            return ResponseModel.error(null);
        }
        return ResponseModel.ok(result);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
