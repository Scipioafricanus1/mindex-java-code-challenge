package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for employee [{}]", compensation.getEmployee().getEmployeeId());

        return compensationRepository.insert(compensation);
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation for employee [{}]", id);

        Compensation compensation = compensationRepository.findCompensationByEmployeeId(id);

        if (compensation == null ) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensation;

    }
    
}
