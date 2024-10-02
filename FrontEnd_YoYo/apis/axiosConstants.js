export const BASE_URL = `http://192.168.0.16:8000/yoyo/`;

export const END_POINT = {
    //회원
    MEMBER: (path) => `members/${path}`,
    //이벤트
    EVENT: "event",
    EVENT_PATH: (path) => `event/${path}`,
    //계좌
    ACCOUNT: "account",
    ACCOUTN_PATH: (path) => `account/${path}`,
    //페이
    PAYMENT: "pay",
    PAYMENT_PATH: (path) => `pay/${path}`,
};
