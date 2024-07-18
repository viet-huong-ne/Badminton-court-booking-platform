package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Config.OnlinePay.Config;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import com.SWP.BadmintonCourtBooking.Repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    public int orderReturn(HttpServletRequest request){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
    public List<?> getAllPayments() {
        return convertToDTO(paymentRepository.findAll());
    }

    public List<?> getPaymentsByCourtID(int courtID) {
        List<Payment> pay = new ArrayList<>();
        List<Payment> payments = paymentRepository.findByCourtID(courtID);
        for (Payment payment : payments) {
            pay.add(payment);
        }
        List<Payment> payments1 = paymentRepository.findByCourtIDV2(courtID);
        for (Payment payment : payments1) {
            pay.add(payment);
        }
        return convertToDTO(pay);
    }
}
