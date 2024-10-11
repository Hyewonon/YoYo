// export const BASE_URL = `http://192.168.123.139:8000/yoyo/`;
// export const BASE_URL = `http://70.12.247.36:8000/yoyo/`;
export const BASE_URL = `https://j11a308.p.ssafy.io/yoyo/`;

export const END_POINT = {
    //회원
    MEMBER: (path) => `members/${path}`,
    //이벤트
    EVENT: "event",
    EVENT_PATH: (path) => `event/${path}`,

    SCHEDULE: `schedule`,
    SCHEDULE_PATH: (path) => `schedule/${path}`,

    NOTIFICATION: `notification`,
    NOTIFICATION_PATH: (path) => `notification/${path}`,

    TRANSACTION: `transactions`,
    TRANSACTION_PATH: (path) => `transactions/${path}`,

    RELATIONS: `members-relations`,
    RELATIONS_PATH: (path) => `members-relations/${path}`,

    //계좌
    ACCOUNT: "account",
    ACCOUTN_PATH: (path) => `account/${path}`,
    //페이
    PAYMENT: "pay",
    PAYMENT_PATH: (path) => `pay/${path}`,
    //거래
    TRANSACTIONS: (path) => `transactions/${path}`,
    RELATION: `members-relation`,
    RELATIONS: `members-relations`,
    RELATION_PATH: (path) => `members-relations/${path}`,
};
