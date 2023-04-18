package com.eden.gallery.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.gallery.service.AccountService;
import com.eden.gallery.viewmodel.AccountVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for handling account.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

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
     * Get a list of all accounts.
     *
     * @return list of account
     */
    @GetMapping
    public ResponseModel getAllAccounts() {

        return ResponseModel.ok(accountService.findAll());
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

    @PutMapping("/verify/{id}")
    public ResponseModel verifyAccount(@PathVariable Long id, @RequestParam("token") String token) {

        String result = accountService.verifyAccount(id, token);
        if (null == result) {
            return ResponseModel.notFound();
        }
        return ResponseModel.updated(result);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
