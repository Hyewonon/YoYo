import { loadTossPayments, ANONYMOUS } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState } from "react";

const clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";
const customerKey = generateRandomString();

const amount = {
  currency: "KRW",
  value: 20000,
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
    switch (selectedPaymentMethod) {
      case "CARD":
        await payment.requestPayment({
          method: "CARD", // 카드 및 간편결제
          amount,
          orderId: generateRandomString(), // 받는 사람 + 고유 주문번호
          orderName: "마음 보내기",
          successUrl: window.location.origin + "/payment/success", 
          failUrl: window.location.origin + "/fail",
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
  const memberId = "receverId-or-eventId";
  return memberId + window.btoa(Math.random().toString()).slice(0, 20);
}
