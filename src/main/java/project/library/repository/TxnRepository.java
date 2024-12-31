package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.library.model.Txn;

public interface TxnRepository extends JpaRepository<Txn, Integer>
{
    Txn findByTxnId(String txnId);
}
