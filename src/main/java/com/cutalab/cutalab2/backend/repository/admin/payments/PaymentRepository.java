package com.cutalab.cutalab2.backend.repository.admin.payments;

import com.cutalab.cutalab2.backend.entity.admin.payments.PPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<PPaymentEntity, Integer> {

    @Query(value = "select * from p_payments p where p_service_id = 7 and id not in (:ids) order by payment_date desc", nativeQuery = true)
    List<PPaymentEntity> findAllProgressiPayments(List<Integer> ids);

    @Query(value = "select payment_id from aba_packages a", nativeQuery = true)
    List<Integer> findAllProgressiPaymentsToExclude();


}