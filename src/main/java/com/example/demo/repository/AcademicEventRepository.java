package com.example.demo.repository;

public interface AcedemicEventRepository extends JpaRepository<AcedemicProfil,Long>{
Optional<UserAccount> findByEmail(String email);
    
}