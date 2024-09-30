import { loadTossPayments, ANONYMOUS } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState, useNavigate } from "react";
const clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";
const customerKey = generateRandomString();

export function Payment() {
  const [payment, setPayment] = useState(null);
  const navigate = useNavigate();
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState("");
  // 두개 값 보내기 /payment/success
  const [senderName, setSenderName] = useState("");
  const [description, setDescription] = useState("");

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
    const amount = {
      currency: "KRW",
      value: price,
    };

    try {
      switch (selectedPaymentMethod) {
      case "CARD":
        await payment.requestPayment({
          method: "CARD", // 카드 및 간편결제
          amount,
          orderId: generateRandomString(), // 받는 사람 + 고유 주문번호
          orderName: description,
          successUrl: window.location.origin + "/payment/success", 
          failUrl: window.location.origin + "/fail",
          customerEmail: "",
          customerName: "",
          customerMobilePhone: phoneNumber,
          card: {
            useEscrow: false,
            flowMode: "DEFAULT",
            useCardPoint: false,
            useAppCardOnly: false,
          },
        });
        break; 
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
          customerMobilePhone: phoneNumber,
          transfer: {
            cashReceipt: {
              type: "소득공제",
            },
            useEscrow: false,
          },
        });
        break;
      default:
        alert("결제 수단을 선택해주세요.");
      }
      // "/payment/success" 페이지로 값 보내기
navigate("/payment/success", { state: { senderName: senderName, description: description } });
    }
    catch (error) {
      console.log("결제 에러", error);
    }
  }

  return (
    <div className="wrapper">
      <div className="box_section">
        <h1>비회원 결제</h1>
        <div>
          <label>보내는 사람 이름:</label>
          <input
            type="text"
            value={senderName}
            onChange={(e) => setSenderName(e.target.value)}
            placeholder="보내는 사람 이름을 입력하세요"
          />
        </div>
        <div>
          <label>가격:</label>
          <input
            type="number"
            value={price}
            onChange={(e) => setPrice(Number(e.target.value))}
            placeholder="가격을 입력하세요"
          />
        </div>
        <div>
          <label>전화번호:</label>
          <input
            type="tel"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="전화번호를 입력하세요"
          />
        </div>
        <div>
          <label>메모:</label>
          <input
            type="text"
            onChange={(e) => setDescription(e.target.value)}
            placeholder="메모를 입력하세요"
          />
        </div>
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
  return window.btoa(Math.random().toString()).slice(0, 15);
}
