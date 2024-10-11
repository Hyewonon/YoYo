package com.yoyo.banking.domain.account.service;

import com.yoyo.banking.domain.account.dto.account.AccountAuthDTO;
import com.yoyo.banking.domain.account.dto.account.DummyAccountDTO;
import com.yoyo.banking.domain.account.dto.account.DummyTransactionDTOs;
import com.yoyo.banking.domain.account.dto.account.DummyTransactionDTOs.DummyTransactionDTO;
import com.yoyo.banking.domain.account.dto.SsafyCommonHeader.Request;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.global.util.AesService;
import com.yoyo.banking.global.util.BankingUtil;
import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SsafyBankService {

    private final AccountRepository accountRepository;
    private final BankingUtil bankingUtil;
    private final AesService aesService;

    @Value("${ssafy.bank.api-key}")
    private String bankApiKey;

    @Value("${ssafy.bank.account.auth.text}")
    private String bankAuthAccountText;

    @Value("${ssafy.bank.account.create}")
    private String bankCreateAccountUrl;

    @Value("${ssafy.bank.user-key.create}")
    private String bankCreateUserKeyUrl;

    @Value("${ssafy.bank.user-key.get}")
    private String bankGetUserKeyUrl;

    @Value("${ssafy.bank.account.auth.open}")
    private String bankAuthAccountOpenUrl;

    @Value("${ssafy.bank.account.auth.check}")
    private String bankAuthAccountCheckUrl;

    @Value("${ssafy.bank.account.transaction.get}")
    private String bankAccountTransactionGetUrl;

    @Value("${ssafy.bank.account.demand-deposit.create}")
    private String bankCreateDemandDepositUrl;

    @Value("${ssafy.bank.account.demand-deposit.deposit}")
    private String bankDemandDepositDepositUrl;

    @Value("${ssafy.bank.account.demand-deposit.withdrawal}")
    private String bankDemandDepositWithdrawalUrl;

    private final Long DUMMY_MONEY_AMOUNT = 10000000L;

    /**
     * [ssafy 금융 API]더미 계좌 생성
     */
    public ResponseEntity<?> createDummyAccount(Long memberId) {
//        log.info("---------------더미 계좌 생성-------------");

        // 1. 은행에서 상품 등록
//        log.info("------------1. 상품 등록------------");
        String accountTypeUniqueNo = createDemandDeposit();

        // 2. 해당 상품으로 사용자 계좌 생성
//        log.info("------------2. 사용자 계좌 성생------------");
        String url = bankCreateAccountUrl;
        Map<String, Object> request = new HashMap<>();
        request.put("accountTypeUniqueNo", accountTypeUniqueNo);

        ResponseEntity<Map> response = sendPostRequestToSsafy(url, request, memberId);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> tmp = (Map<String, Object>) response.getBody().get("REC");
            String account = (String) tmp.get("accountNo");
            String bankCode = (String) tmp.get("bankCode");
            String bankName = bankingUtil.findBankNameByBankCode(bankCode);

            // 더미 계좌 생성 시 돈 입금
//            updateDemandDeposit(memberId, DUMMY_MONEY_AMOUNT, true);
            Map<String, Object> requestToSsafy = new HashMap<>();
            requestToSsafy.put("accountNo", account);
            requestToSsafy.put("transactionBalance", DUMMY_MONEY_AMOUNT);
            requestToSsafy.put("transactionSummary", "계좌 생성 시 축하금");
           sendPostRequestToSsafy(url, requestToSsafy, memberId);


            return new ResponseEntity<>(DummyAccountDTO.Response.of(account, bankName, memberId), HttpStatus.CREATED);
        } else {
            return response;
        }
    }

    /**
     * [ssafy 금융 API] 은행 상품 등록
     * */
    public String createDemandDeposit(){
        String url = bankCreateDemandDepositUrl;
        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("bankCode", "999");
        requestToSsafy.put("accountName", "싸피 은행 입출금 수시입출금");
        requestToSsafy.put("accountDescription", "싸피 은행 입출금 수시입출금");

        Request header = bankingUtil.createAPICommonHeaderWithoutUserkey(url);
        requestToSsafy.put("Header", header);

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, null);

        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> tmp = (Map<String, Object>) responseFromSsafy.getBody().get("REC");
            String accountTypeUniqueNo = (String) tmp.get("accountTypeUniqueNo");
            return accountTypeUniqueNo;
        } else {
            throw new BankingException(ErrorCode.UNEXPECTED_ERROR);
        }
    }

    /**
     * [ssafy 금융 API] 1원 송금
     */
    public ResponseEntity<?> openAccountAuth(AccountAuthDTO.Request request, Long memberId) {
        String url = bankAuthAccountOpenUrl;
        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("accountNo", request.getAccountNumber());
        requestToSsafy.put("authText", bankAuthAccountText);

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        // 성공 시 userKey db에 저장
        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(CommonResponse.of(true, "1원 송금에 성공했습니다."), HttpStatus.OK);
        } else {
            return responseFromSsafy;
        }
    }

    /**
     * [ssafy 금융 API] 거래 내역 확인
     */
    public ResponseEntity<?> getDummyAccountTransaction(AccountAuthDTO.Request request, Long memberId, String date) {
        String url = bankAccountTransactionGetUrl;
        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("accountNo", request.getAccountNumber());
        requestToSsafy.put("startDate", date);
        requestToSsafy.put("endDate", date);
        requestToSsafy.put("transactionType", "M");
        requestToSsafy.put("orderByType", "DESC");

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> rec = (Map<String, Object>) responseFromSsafy.getBody().get("REC");
            Long totalCount = Long.valueOf((String) rec.get("totalCount"));
            List<DummyTransactionDTO> transactions = extractTransactions(rec);
            return new ResponseEntity<>(DummyTransactionDTOs.of(totalCount, transactions), responseFromSsafy.getStatusCode());
        } else {
            return responseFromSsafy;
        }
    }
    /*
    * * SSafy API로부터 받은 거래 내역 응답에서 거래 내역 list 만 추출
    * */
    private List<DummyTransactionDTO> extractTransactions(Map<String, Object> rec) {
        List<Map<String, Object>> transactionList = (List<Map<String, Object>>) rec.get("list");
        return transactionList.stream()
                       .map(this::mapToDummyTransactionDTO)
                       .collect(Collectors.toList());
    }

    /*
    * * 거래내역 list 를 클라이언트 응답 DTO 에 매핑
    * */
    private DummyTransactionDTO mapToDummyTransactionDTO(Map<String, Object> transaction) {
        String tmpDate = (String) transaction.get("transactionDate");
        String tmpTime = (String) transaction.get("transactionTime");
        LocalDateTime transactionDateTime = LocalDateTime.parse(tmpDate + tmpTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return DummyTransactionDTO.builder()
                                  .transactionUniqueNo(Long.valueOf((String) transaction.get("transactionUniqueNo")))
                                  .transactionDateTime(transactionDateTime)
                                  .transactionTypeName((String) transaction.get("transactionTypeName"))
                                  .transactionSummary((String) transaction.get("transactionSummary"))
                                  .build();
    }

    /**
     * [ssafy 금융 API] 1원 송금 확인
     */
    public ResponseEntity<?> checkAccountAuth(AccountAuthDTO.Request request, Long memberId) {
        String url = bankAuthAccountCheckUrl;
        String successMessage = "1원 송금 인증에 성공했습니다.";
        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("accountNo", request.getAccountNumber());
        requestToSsafy.put("authText", bankAuthAccountText);
        requestToSsafy.put("authCode", request.getAuthCode());

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        // 성공 시 userKey db에 저장
        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(CommonResponse.of(true, successMessage), HttpStatus.OK);
        } else {
            return responseFromSsafy;
        }
    }

    /**
     * [ssafy 금융 API] userKey 생성 및 저장
     */
    public ResponseEntity<?> createUserKey(Long memberId) {
        String url = bankCreateUserKeyUrl;
        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("userId", bankingUtil.createMemberEmail(memberId));
        requestToSsafy.put("apiKey", bankApiKey);
        requestToSsafy.put("Header", null);

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            String userKey = responseFromSsafy.getBody().get("userKey").toString();
            Account account = accountRepository.findByMemberId(memberId)
                                               .orElse(Account.builder()
                                                              .memberId(memberId)
                                                              .build());
            account.setUserKey(userKey);
            accountRepository.save(account);
            return new ResponseEntity<>(CommonResponse.of(true, "유저 키를 등록했습니다."), HttpStatus.OK);
        } else {
            return responseFromSsafy;
        }
    }

    /**
     * [ssafy 금융 API] userKey 조회 및 저장
     */
    public ResponseEntity<?> getUserKey(Long memberId) {
        String url = bankGetUserKeyUrl;

        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("userId", bankingUtil.createMemberEmail(memberId));
        requestToSsafy.put("apiKey", bankApiKey);
        requestToSsafy.put("Header", null);

        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            String userKey = responseFromSsafy.getBody().get("userKey").toString();
            Account account = accountRepository.findByMemberId(memberId)
                                               .orElse(Account.builder()
                                                              .memberId(memberId)
                                                              .build());
            account.setUserKey(userKey);
            accountRepository.save(account);
            return new ResponseEntity<>(userKey, HttpStatus.OK);
        } else {
            return responseFromSsafy;
        }
    }

    /**
     * [ssafy 금융 API] 출금 / 입금 요청
     *
     * @param isDeposit 입금 요청 여부
     * */
    public ResponseEntity<?> updateDemandDeposit(Long memberId, Long amount, Boolean isDeposit) {
//        log.info("-------------계좌 입출금---------------");

        String url = bankDemandDepositWithdrawalUrl;
        String successMessage = "계좌 출금(페이 충전)에 성공했습니다.";
        String transactionSummary = "계좌 출금";
        if(isDeposit){
            url = bankDemandDepositDepositUrl;
            successMessage = "계좌 입금(페이 환불)에 성공했습니다.";
            transactionSummary = "계좌 입금";
        }

        Account account = accountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_ACCOUNT));

        Map<String, Object> requestToSsafy = new HashMap<>();
        requestToSsafy.put("accountNo", aesService.decrypt(account.getAccountNumber()));
        requestToSsafy.put("transactionBalance", amount);
        requestToSsafy.put("transactionSummary", transactionSummary);
        ResponseEntity<Map> responseFromSsafy = sendPostRequestToSsafy(url, requestToSsafy, memberId);

        if (responseFromSsafy.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(CommonResponse.of(true, successMessage), HttpStatus.OK);
        } else {
            return responseFromSsafy;
        }
    }

    /*
     * * [ssafy 금융 API] 공통 post 요청
     * */
    private ResponseEntity<Map> sendPostRequestToSsafy(String url, Map<String, Object> request, Long memberId) {
        RestClient restClient = RestClient.create();

        // request에 Header 안담겨있으면 공통 Header로 생성
        if(!request.containsKey("Header")) {
            Request header = bankingUtil.createAPICommonHeader(url, memberId);
            request.put("Header", header);
        }
        try {
            return restClient.post()
                             .uri(url)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(request)
                             .retrieve()
                             .toEntity(Map.class);
        }catch(HttpClientErrorException e){
            // 클라이언트에게 오류 응답 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", String.valueOf(400));
            errorResponse.put("errorCode", ErrorCode.SSAFY_API_ERROR.toString());
            errorResponse.put("errorMessage", (String) e.getResponseBodyAs(Map.class).get("responseMessage"));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
