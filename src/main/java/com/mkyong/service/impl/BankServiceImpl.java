package com.mkyong.service.impl;

import com.mkyong.repository.BankRepository;
import com.mkyong.entity.Bank;
import com.mkyong.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public Bank findOne(Long id) {
        return bankRepository.findOne(id);
    }
}
