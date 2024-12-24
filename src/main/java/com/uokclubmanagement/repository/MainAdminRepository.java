package com.uokclubmanagement.repository;

import com.uokclubmanagement.entity.MainAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MainAdminRepository extends MongoRepository<MainAdmin, String> {

    MainAdmin findMainAdminByMainAdminId(String mainAdminId);
}
