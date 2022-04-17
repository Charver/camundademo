package kz.camundademo.camundademo.services;

import kz.camundademo.camundademo.domain.Interest;
import kz.camundademo.camundademo.domain.InterestRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableProcessApplication
public class ProcessConfig {

    public ProcessConfig(RuntimeService runtimeService, IdentityService identityService, InterestRepository interestRepository) {
        this.runtimeService = runtimeService;
        this.identityService = identityService;
        this.interestRepository = interestRepository;
    }

    private final RuntimeService runtimeService;
    private final IdentityService identityService;
    private final InterestRepository interestRepository;

    @EventListener
    public void processPostDeploy(PostDeployEvent postDeployEvent) {
        User manager = identityService.newUser("manager");
        manager.setFirstName("manager");
        manager.setLastName("manager");
        manager.setEmail("manager@example.com");
        manager.setPassword("manager");
        identityService.saveUser(manager);

        User approver = identityService.newUser("approver");
        approver.setFirstName("approver");
        approver.setLastName("approver");
        approver.setEmail("approver@example.com");
        approver.setPassword("approver");
        identityService.saveUser(approver);

        Group managers = identityService.newGroup("managers");
        managers.setName("managers");
        identityService.saveGroup(managers);

        Group approvers = identityService.newGroup("approvers");
        approvers.setName("approvers");
        identityService.saveGroup(approvers);

        identityService.createMembership(manager.getId(), managers.getId());
        identityService.createMembership(approver.getId(), approvers.getId());

        runtimeService.startProcessInstanceByKey("loan",
                Map.of("loan_amount", "100000", "month_duration", 6, "interest_rate", "10"));

        runtimeService.startProcessInstanceByKey("loan",
                Map.of("loan_amount", "1000000", "month_duration", 12, "interest_rate", "6"));

        runtimeService.startProcessInstanceByKey("loan",
                Map.of("loan_amount", "10000000", "month_duration", 36, "interest_rate", "4"));

        fillInterestTable();
    }

    private void fillInterestTable() {
        List<Interest> entities = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            entities.add(Interest.builder()
                    .interestRate(BigDecimal.valueOf(15. - (i * 0.1)).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    .monthAmount(i)
                    .build());
        }
        interestRepository.saveAll(entities);
    }


}
