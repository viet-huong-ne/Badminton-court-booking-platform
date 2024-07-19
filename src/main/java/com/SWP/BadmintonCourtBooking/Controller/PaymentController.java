package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Config.OnlinePay.Config;
import com.SWP.BadmintonCourtBooking.Dto.PaymentResDTO;
import com.SWP.BadmintonCourtBooking.Dto.Response.RevenueResponse;
import com.SWP.BadmintonCourtBooking.Dto.TransactionStatusDTO;
import com.SWP.BadmintonCourtBooking.Entity.Court;
import com.SWP.BadmintonCourtBooking.Entity.Payment;
import com.SWP.BadmintonCourtBooking.Repository.CourtRepository;
import com.SWP.BadmintonCourtBooking.Repository.UserRepository;
import com.SWP.BadmintonCourtBooking.Service.ServiceOfPayment;
import com.google.api.client.util.DateTime;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@RestController
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    ServiceOfPayment service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourtRepository courtRepository;

    @GetMapping("/payV2/{total}/{userId}")
    public String getPay(@PathVariable("total") Double total, @PathVariable("userId") Integer userId) throws UnsupportedEncodingException { //@PathParam("price") Long price, @PathParam("id") Integer contractId

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        if (total == null) {

            return "Total is null. Please provide a valid total value.";
        }
        if (userId == null) {
            return "UserId is null. Please provide a valid user ID.";
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


        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl); // + "?contractId=" + contractId
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        // vnp_Params.put("vnp_ApiUrl", Config.vnp_ApiUrl);

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

//        PaymentResDTO paymentResDTO = new PaymentResDTO();
//        paymentResDTO.setStatus("OK");
//        paymentResDTO.setMessage("Successfully");
//        paymentResDTO.setURL(paymentUrl);
        //return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);

        return paymentUrl;
    }

    //@Autowired // Hường test
    private final ServiceOfPayment serviceOfPayment;

    @Autowired
    public PaymentController(ServiceOfPayment serviceOfPayment) {
        this.serviceOfPayment = serviceOfPayment;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = service.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }

    @GetMapping("/payInforV2/{vnp_Amount}/{vnp_BankCode}/{vnp_OrderInfo}/{vnp_ResponseCode}/{vnp_TxnRef}")
    public ResponseEntity<?> transactionStatusDTO(
            @PathVariable(value = "vnp_Amount") String amount,
            @PathVariable(value = "vnp_BankCode") String bankCode,
            @PathVariable(value = "vnp_OrderInfo") String orderInfo,
            @PathVariable(value = "vnp_ResponseCode") String responseCode,
            @PathVariable(value = "vnp_TxnRef") String transactinCode
    ) {
        int userId = Config.extractUserId(orderInfo);
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")) {
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setData("Amount: " + amount + ", Bank Code: " + bankCode + ", UserId: " + userId + ", OrderInfo: " + orderInfo);

            // Create Payment entity and save it
            Payment payment = new Payment();
            double amount1 = Double.parseDouble(amount) / 100;
            payment.setPaymentAmount(new BigDecimal(amount1));
            payment.setPaymentTime(new Date());
            payment.setPaymentStatus("Successfully"); // Assuming it's successful by default
            payment.setBankCode(bankCode);
            payment.setTransactionCode(transactinCode);
            //User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserID not found"));
            //còn thiếu set booking

            // You can set other fields as needed

            serviceOfPayment.savePayment(payment); // Save payment
        } else {
            transactionStatusDTO.setStatus("Failed");
            transactionStatusDTO.setMessage("Payment Failed");
            transactionStatusDTO.setData("Error Code: " + responseCode);

            // Create Payment entity and save it
            Payment payment = new Payment();
            double amount1 = Double.parseDouble(amount) / 100;
            payment.setPaymentAmount(new BigDecimal(amount1));
            payment.setPaymentTime(new Date());
            payment.setPaymentStatus("Failed"); // Assuming it's successful by default
            payment.setBankCode(bankCode);
            payment.setTransactionCode(transactinCode);
            //còn thiếu set booking
            // You can set other fields as needed

            serviceOfPayment.savePayment(payment);// Save payment
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", transactionStatusDTO.getStatus());
        response.put("message", transactionStatusDTO.getMessage());
        response.put("paycode", transactinCode);
        //response.put("data", transactionStatusDTO.getData());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/allPayment")
    public ResponseEntity<?> AllPayment() {
        return new ResponseEntity<>(serviceOfPayment.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping("/paymentOfCourt/{courtID}")
    public ResponseEntity<?> paymentOfCourt(@PathVariable(value = "courtID") int courtID) {
        List<?> payments = serviceOfPayment.getPaymentsByCourtID(courtID);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    //TODO API TINH DOANH THU CỦA MỘT SÂN
    @GetMapping("/RevenueOfCourt/{courtID}")
    public ResponseEntity<?> getRevenueForCourt(@PathVariable(value = "courtID") int courtID) {
        LocalDateTime date = LocalDateTime.now();

        RevenueResponse response = serviceOfPayment.getRevenue(date,courtID);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO API TINH DOANH THU CỦA CHỦ SAN CÓ NHIEU SAN
    @GetMapping("/RevenueForCourtOwner/{userID}")
    public ResponseEntity<?> getRevenueForCourtOwnerDay(@PathVariable(value = "userID") int userID) {
        LocalDateTime date = LocalDateTime.now();
        RevenueResponse response = serviceOfPayment.getRevenueForOwner(date,userID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

