package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        compensation.setEmployee(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployee(id);

        if (compensation == null) {
            // I just followed the precedent set in EmployeeServiceImpl for handling this error,
            // though it seems it would be cleaner to handle it by returning an HTTP status of 404
            // instead of throwing an internal error.
            throw new RuntimeException("Invalid employee: " + id);
        }

        return compensation;
    }
}
