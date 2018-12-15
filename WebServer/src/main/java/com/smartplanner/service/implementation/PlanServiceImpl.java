package com.smartplanner.service.implementation;

import com.smartplanner.model.Plan;
import com.smartplanner.repository.PlanRepository;
import com.smartplanner.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Autowired
    PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public boolean findPlanById(int id) {
        return planRepository.existsById(id);
    }

    public Plan getPlanById(int id) {
        return planRepository.getOne(id);
    }
}
