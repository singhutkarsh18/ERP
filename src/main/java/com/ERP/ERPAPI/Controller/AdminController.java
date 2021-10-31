package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Admin;
import com.ERP.ERPAPI.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService service;

    @PostMapping("/admin/auth")
    public void checkAdmin(@RequestBody Admin admin)
    {
        service.checkAuth(admin);
    }

}
