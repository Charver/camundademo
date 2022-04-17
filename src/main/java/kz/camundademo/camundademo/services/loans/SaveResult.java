package kz.camundademo.camundademo.services.loans;

import kz.camundademo.camundademo.domain.Loan;
import kz.camundademo.camundademo.domain.LoanRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SaveResult implements JavaDelegate {

    private final LoanRepository loanRepository;

    public SaveResult(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Loan loan = new Loan();

        loan.setComment((String) delegateExecution.getVariable("comment"));
        loan.setAmount((String) delegateExecution.getVariable("loan_amount"));
        loan.setAmount((String) delegateExecution.getVariable("interest_rate"));

        loan.setDateFinish(LocalDateTime.now().plusMonths((Integer) delegateExecution.getVariable("month_duration")).withNano(0));

        loan.setFinalAmount((String) delegateExecution.getVariable("final_amount"));
        loan.setMonthPayment((String) delegateExecution.getVariable("monthly_payment"));


        Object isApproved = delegateExecution.getVariable("isApproved");
        loan.setApproved(isApproved == null ? false : (Boolean) isApproved);

        loanRepository.save(loan);
        System.out.println("amount = " + delegateExecution.getVariable("amount"));
    }

}
