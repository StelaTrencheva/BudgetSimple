package budget.simple.budgetsimple_back_end.repository;

import budget.simple.budgetsimple_back_end.logic.survey.Survey;
import budget.simple.budgetsimple_back_end.logic.wallets.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersistentTransactionData extends JpaRepository<Transaction, String> {
}
