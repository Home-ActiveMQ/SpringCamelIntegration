package com.mkyong.service;

import com.mkyong.entity.Bank;
import java.util.List;

public interface BankService {

    List<Bank> findAll();

    Bank findAll(Long id);

}
