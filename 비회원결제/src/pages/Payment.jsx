import { loadTossPayments, ANONYMOUS } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState } from "react";

// ------  SDK 초기화 ------
// TODO: clientKey는 개발자센터의 API 개별 연동 키 > 결제창 연동에 사용하려할 MID > 클라이언트 키로 바꾸세요.
// TODO: server.js 의 secretKey 또한 결제위젯 연동 키가 아닌 API 개별 연동 키의 시크릿 키로 변경해야 합니다.
// TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
// @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
const clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";
const customerKey = generateRandomString();

const amount = {
  currency: "KRW",
  value: 50000,
};

export function Payment() {
  const [payment, setPayment] = useState(null);

  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);

  function selectPaymentMethod(method) {
    setSelectedPaymentMethod(method);
  }

  useEffect(() => {
    async function fetchPayment() {
      try {
        const tossPayments = await loadTossPayments(clientKey);
        const payment = tossPayments.payment({
          customerKey,
        });


        setPayment(payment);
      } catch (error) {
        console.error("Error fetching payment:", error);
      }
    }

    fetchPayment();
  }, [clientKey, customerKey]);

  async function requestPayment() {
    // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
    // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
    switch (selectedPaymentMethod) {
      case "CARD":
        await payment.requestPayment({
          method: "CARD", // 카드 및 간편결제
          amount,
          orderId: generateRandomString(), // 고유 주문번호
          orderName: "마음 보내기",
          successUrl: window.location.origin + "/payment/success", // 결제 요청이 성공하면 리다이렉트되는 URL
          failUrl: window.location.origin + "/fail", // 결제 요청이 실패하면 리다이렉트되는 URL
          customerEmail: "",
          customerName: "",
          customerMobilePhone: "01054513115",
          card: {
            useEscrow: false,
            flowMode: "DEFAULT",
            useCardPoint: false,
            useAppCardOnly: false,
          },
        });
      case "TRANSFER":
        await payment.requestPayment({
          method: "TRANSFER", // 계좌이체 결제
          amount,
          orderId: generateRandomString(),
          orderName: "마음 보내기",
          successUrl: window.location.origin + "/payment/success",
          failUrl: window.location.origin + "/fail",
          customerEmail: "",
          customerName: "",
          customerMobilePhone: "01054513115",
          transfer: {
            cashReceipt: {
              type: "소득공제",
            },
            useEscrow: false,
          },
        });
    }
  }

  return (
    <div className="wrapper">
      <div className="box_section">
        <h1>비회원 결제</h1>
        <div id="payment-method" style={{ display: "flex", justifyContent:"center" }}>
          <button id="CARD" className={`button2 ${selectedPaymentMethod === "CARD" ? "active" : ""}`} onClick={() => selectPaymentMethod("CARD")}>
            카드
          </button>
          <button id="TRANSFER" className={`button2 ${selectedPaymentMethod === "TRANSFER" ? "active" : ""}`} onClick={() => selectPaymentMethod("TRANSFER")}>
            계좌이체
          </button>
        </div>
        <button className="button" onClick={() => requestPayment()}>
          결제하기
        </button>
      </div>
    </div>
  );
}

function generateRandomString() {
  return window.btoa(Math.random().toString()).slice(0, 20);
}
