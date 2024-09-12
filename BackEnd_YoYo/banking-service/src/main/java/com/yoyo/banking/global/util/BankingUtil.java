package com.yoyo.banking.global.util;

import com.yoyo.banking.domain.account.dto.SsafyCommonHeader;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.domain.account.repository.BankRepository;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import jakarta.transaction.Transactional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class BankingUtil {

    private final BankRepository bankRepository;
    private final AccountRepository accountRepository;

    @Value("${ssafy.bank.api-key}")
    private String bankApiKey;

    /*
     * [Util] 20자리 랜덤 문자열 생성
     * */
    public String createRandomString() {
        String chars = "abcdefghijklmnopqrstuvwsyz0123456789-";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < 20; i++) {
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /*
     * [Util] Ssafy 서버 등록 용 멤버 임의 메일 생성
     */
    public String createMemberEmail(Long memberId) {
        StringBuilder sb = new StringBuilder();
        sb.append("yoyo").append(memberId).append("@yoyo.com");
        log.info("email = {}", sb);
        return sb.toString();
    }

    /*
     * [Util] 공통 Header 생성
     * */
    public SsafyCommonHeader.Request createAPICommonHeader(String url, Long memberId) {
        String userKey = findUserKeyByMemberId(memberId);
        String apiName = abstractAPINameFromUrl(url);
        return SsafyCommonHeader.Request.of(apiName, this.bankApiKey, userKey);
    }

    /*
     * [Util] userKey 없는 Header 생성
     * */
    public SsafyCommonHeader.Request createAPICommonHeaderWithoutUserkey(String url) {
        String apiName = abstractAPINameFromUrl(url);
        return SsafyCommonHeader.Request.of(apiName, this.bankApiKey);
    }

    /*
     * [Util] apiName 조회
     * */
    public String abstractAPINameFromUrl(String url) {
        log.info("apiName = {}", url.substring(url.lastIndexOf("/") + 1));
        return url.substring(url.lastIndexOf("/") + 1);
    }

    // repository 접근 메소드
    public String findBankCodeByBankName(String bankName){
        return bankRepository.findBankCodeByBankName(bankName)
                             .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_BANK));
    }

    public String findBankNameByBankCode(String banCode){
        return bankRepository.findBankNameByBankCode(banCode)
                             .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_BANK));
    }

    // repository 접근 메소드
    private String findUserKeyByMemberId(Long memberId) {
        return accountRepository.findUserKeyByMemberId(memberId)
                                .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_USER_KEY));
    }
}
