package com.SWP.BadmintonCourtBooking.Service;

import com.SWP.BadmintonCourtBooking.Config.OnlinePay.Config;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Dto.Response.RevenueResponse;
import com.SWP.BadmintonCourtBooking.Dto.RevenueDTO;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceOfPayment {
    private final PaymentRepository paymentRepository;
    private final CourtRepository courtRepository;

    @Autowired
    public ServiceOfPayment(PaymentRepository paymentRepository, CourtRepository courtRepository) {
        this.paymentRepository = paymentRepository;
        this.courtRepository = courtRepository;
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

    public int orderReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
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

    //    public RevenueResponse getRevenueOfCourt(Integer courtID){
//
//    }
    public RevenueResponse getRevenue(LocalDateTime date, int courtID) {
        LocalDate startOfYear = date.toLocalDate().withDayOfYear(1);
        LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);

        List<Payment> dailyPayments = paymentRepository.findPaymentsByDateRange(courtID, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));

        Map<LocalDate, Double> dailyRevenueMap = dailyPayments.stream()
                .collect(Collectors.groupingBy(payment -> payment.getPaymentTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Collectors.summingDouble(payment -> payment.getPaymentAmount().doubleValue())));

        List<RevenueDTO> dailyRevenue = dailyRevenueMap.entrySet().stream()
                .map(entry -> new RevenueDTO(java.sql.Date.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());

        List<Double> monthlyRevenue = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            LocalDateTime startOfMonth = YearMonth.of(date.getYear(), i).atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = YearMonth.of(date.getYear(), i).atEndOfMonth().atTime(23, 59, 59);
            List<Payment> monthlyPayments = paymentRepository.findPaymentsByDateRange(courtID, startOfMonth, endOfMonth);
            double totalMonthlyRevenue = monthlyPayments.stream().mapToDouble(payment -> payment.getPaymentAmount().doubleValue()).sum();
            monthlyRevenue.add(totalMonthlyRevenue);
        }

        List<Payment> yearlyPayments = paymentRepository.findPaymentsByYear(courtID, date);
        double totalYearlyRevenue = yearlyPayments.stream().mapToDouble(payment -> payment.getPaymentAmount().doubleValue()).sum();

        return new RevenueResponse(dailyRevenue, monthlyRevenue, totalYearlyRevenue);
    }

    public RevenueResponse getRevenueForOwner(LocalDateTime date, int userID) {
        LocalDate startOfYear = date.toLocalDate().withDayOfYear(1);
        LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
        List<Court> court = courtRepository.getCourtByUserID(userID);
        List<Double> monthlyRevenue = new ArrayList<>();
        List<RevenueDTO> dailyRevenue = new ArrayList<>();

        double totalYearlyRevenue = 0;
        for (Court c : court) {
            List<Payment> dailyPayments = paymentRepository.findPaymentsByDateRange(c.getCourtID(), startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59));

            Map<LocalDate, Double> dailyRevenueMap = dailyPayments.stream()
                    .collect(Collectors.groupingBy(payment -> payment.getPaymentTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Collectors.summingDouble(payment -> payment.getPaymentAmount().doubleValue())));

            List<RevenueDTO> courtDailyRevenue = dailyRevenueMap.entrySet().stream()
                    .map(entry -> new RevenueDTO(java.sql.Date.valueOf(entry.getKey()), entry.getValue()))
                    .collect(Collectors.toList());
            dailyRevenue.addAll(courtDailyRevenue);
            for (int i = 1; i <= 12; i++) {
                LocalDateTime startOfMonth = YearMonth.of(date.getYear(), i).atDay(1).atStartOfDay();
                LocalDateTime endOfMonth = YearMonth.of(date.getYear(), i).atEndOfMonth().atTime(23, 59, 59);
                List<Payment> monthlyPayments = paymentRepository.findPaymentsByDateRange(c.getCourtID(), startOfMonth, endOfMonth);
                double totalMonthlyRevenue = monthlyPayments.stream().mapToDouble(payment -> payment.getPaymentAmount().doubleValue()).sum();
                if (monthlyRevenue.size() < 12) {
                    monthlyRevenue.add(totalMonthlyRevenue);
                } else monthlyRevenue.set(i - 1, (monthlyRevenue.get(i - 1) + totalMonthlyRevenue));
            }

            List<Payment> yearlyPayments = paymentRepository.findPaymentsByYear(c.getCourtID(), date);
            totalYearlyRevenue += yearlyPayments.stream().mapToDouble(payment -> payment.getPaymentAmount().doubleValue()).sum();
        }
        List<RevenueDTO> uniqueList = new LinkedHashMap<>(
                dailyRevenue.stream()
                        .collect(Collectors.toMap(
                                RevenueDTO::getRevenueDate,
                                dto -> dto,
                                (existing, replacement) -> {
                                    existing.setAmount(existing.getAmount() + replacement.getAmount());
                                    return existing;
                                }
                        ))
        ).values().stream()
                .collect(Collectors.toList());
        return new RevenueResponse(uniqueList, monthlyRevenue, totalYearlyRevenue);
    }

}
