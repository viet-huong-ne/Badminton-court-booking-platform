package com.SWP.BadmintonCourtBooking.Controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Dto.TransactionStatusDTO;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import com.SWP.BadmintonCourtBooking.Entity.*;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.ServiceOfPayment;
import org.springframework.beans.factory.annotation.Autowired;
import com.SWP.BadmintonCourtBooking.Config.OnlinePay.Config;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  static java.lang.System.out;



@RestController
@CrossOrigin("*")
//@RequiredArgsConstructor
public class PaymentController {


    @GetMapping("/pay/{total}/{userId}")
    public String getPay(@PathVariable("total") Double total, @PathVariable("userId") Integer userId) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        if (total == null || userId == null) {
            return "Total or UserId is null. Please provide valid values.";
        }

        long amount = (long) (total * 100);
        String bankCode = "NCB";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef + " | Total: " + total + " | UserId: " + userId);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_UserId", String.valueOf(userId));
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;



        return paymentUrl;
    }

    private final ServiceOfPayment serviceOfPayment;
    //private final UserRepository userRepository;

    @Autowired
    public PaymentController(ServiceOfPayment serviceOfPayment) {
        this.serviceOfPayment = serviceOfPayment;
    }

    @GetMapping("/pay_infor")
    public ResponseEntity<?> transactionStatusDTO(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_UserId") Integer userId

    ) {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")) {
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setData("Amount: " + amount + ", Bank Code: " + bankCode + ", UserId: " + userId + ", OrderInfo: " + orderInfo);

            // Create Payment entity and save it
            Payment payment = new Payment();
            payment.setPaymentAmount(new BigDecimal(amount));
            payment.setPaymentTime(new Date());
            payment.setPaymentStatus("Success"); // Assuming it's successful by default
            payment.setBankCode(bankCode);
            payment.setOrderInfo(orderInfo);
            payment.setUserId(userId);
            // You can set other fields as needed

            serviceOfPayment.savePayment(payment); // Save payment
        } else {
            transactionStatusDTO.setStatus("Failed");
            transactionStatusDTO.setMessage("Payment Failed");
            transactionStatusDTO.setData("Error Code: " + responseCode);

            // Create Payment entity and save it
            Payment payment = new Payment();
            payment.setPaymentAmount(new BigDecimal(amount));
            payment.setPaymentTime(new Date());
            payment.setPaymentStatus("Failed"); // Assuming it's successful by default
            payment.setBankCode(bankCode);
            payment.setOrderInfo(orderInfo);
            // You can set other fields as needed

            serviceOfPayment.savePayment(payment);// Save payment
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", transactionStatusDTO.getStatus());
        response.put("message", transactionStatusDTO.getMessage());
        response.put("data", transactionStatusDTO.getData());

        return ResponseEntity.ok(response);
    }
}
