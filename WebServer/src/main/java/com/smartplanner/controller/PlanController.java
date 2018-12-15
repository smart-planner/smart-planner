package com.smartplanner.controller;

import com.smartplanner.exception.ResourceNotFoundException;
import com.smartplanner.model.Plan;
import com.smartplanner.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/plans")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("{id}")
    public Plan getPlanById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        if (!planService.findPlanById(id)) {
            throw new ResourceNotFoundException("Plan", "ID", id);
        }

        return planService.getPlanById(id);
    }
}
