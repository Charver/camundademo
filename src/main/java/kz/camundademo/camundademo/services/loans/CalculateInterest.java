package kz.camundademo.camundademo.services.loans;

import kz.camundademo.camundademo.domain.Interest;
import kz.camundademo.camundademo.domain.InterestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CalculateInterest implements JavaDelegate {

    private final InterestRepository interestRepository;

    public CalculateInterest(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Double amount = Double.valueOf((String) delegateExecution.getVariable("loan_amount"));
        int monthDuration = ((Integer) delegateExecution.getVariable("month_duration"));

        Optional<Interest> firstByMonthAmount = interestRepository.findFirstByMonthAmount(monthDuration);
        double interestRate = firstByMonthAmount.orElse(Interest.builder().interestRate(10.).build()).getInterestRate();

        Double monthlyInterestRate = interestRate / 1200;

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal monthly = BigDecimal.ZERO;

        for (int i = 0; i < monthDuration; i++) {
            monthly = BigDecimal.valueOf(amount / ((Math.pow(1 + monthlyInterestRate, monthDuration) - 1) /
                    (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, monthDuration))));
            total = total.add(monthly);
        }

        delegateExecution.setVariable("final_amount", total.setScale(2, RoundingMode.HALF_UP).toPlainString());
        delegateExecution.setVariable("monthly_payment", monthly.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }
}
