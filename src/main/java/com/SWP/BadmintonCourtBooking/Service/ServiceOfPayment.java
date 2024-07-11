package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import com.SWP.BadmintonCourtBooking.Repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceOfPayment {
    private final PaymentRepository paymentRepository;

    @Autowired
    public ServiceOfPayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public List<PaymentResDTO> convertToDTO(List<Payment> payments) {
        List<PaymentResDTO> paymentResDTOList = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentResDTO paymentResDTO = new PaymentResDTO();
            paymentResDTO.setBankCode(payment.getBankCode());
            paymentResDTO.setPaymentAmount(payment.getPaymentAmount());
            paymentResDTO.setPaymentDate(payment.getPaymentTime());
            paymentResDTO.setTransactionCode(payment.getTransactionCode());
            paymentResDTOList.add(paymentResDTO);
        }
        return paymentResDTOList;
    }

    public List<?> getAllPayments() {
        return convertToDTO(paymentRepository.findAll());
    }

    public List<?> getPaymentsByCourtID(int courtID) {
        List<Payment> payments = paymentRepository.findByCourtID(courtID);
        for (Payment payment : payments) {
            System.out.println(payment.toString());
        }
        return convertToDTO(paymentRepository.findByCourtID(courtID));
    }
}
