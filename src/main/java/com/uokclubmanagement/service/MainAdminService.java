package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.MainAdmin;

import java.util.List;

public interface MainAdminService {
    MainAdmin createMainAdmin(MainAdmin mainAdmin);
    List<MainAdmin> getAllMainAdmins();
    MainAdmin updateMainAdminById(String mainAdminID, MainAdmin mainAdmin) ;
    void deleteMainAdminById(String mainAdminId);
    MainAdmin getMainAdminById(String mainAdminId);

}
