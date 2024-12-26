package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;

import java.util.List;

public interface MainAdminService {
    MainAdmin createMainAdmin(MainAdmin mainAdmin);
    List<MainAdmin> getAllMainAdmins();
    MainAdmin updateMainAdminById(String memberId, MainAdmin mainAdmin);
    void deleteMainAdminById(String mainAdminId);
    MainAdmin getMainAdminById(String mainAdminId);

}
