import { loadTossPayments } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';
import '../App.css'
const clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";
const customerKey = generateRandomString();

export function Payment() {

  const [payment, setPayment] = useState(null);
  const navigate = useNavigate();
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);
  // 두개 값 보내기 /payment/success
  const [senderName, setSenderName] = useState("");
  const [price, setPrice] = useState("")
  const [description, setDescription] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const { id } = useParams();
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
      let successUrl = window.location.origin + "/payment/success";
      let failUrl = window.location.origin + "/fail";

      switch (selectedPaymentMethod) {
        case "CARD":
          await payment.requestPayment({
            method: "CARD",
            amount,
            orderId: generateRandomString(),
            orderName: description,
            successUrl: `${successUrl}?senderName=${senderName}&description=${description}&eventId=${id}`, // URL에 쿼리 스트링으로 전달
            failUrl,
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
            method: "TRANSFER",
            amount,
            orderId: generateRandomString(),
            orderName: "마음 보내기",
            successUrl: `${successUrl}?senderName=${senderName}&description=${description}&eventId=${id}`, // URL에 쿼리 스트링으로 전달
            failUrl,
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
    }
    catch (error) {
      console.log("결제 에러", error);
    }
  }

  return (
    <div className="wrapper">
      <div className="box_section">
        <h1>마음 전하기</h1>
        <div className="inputContainer">
          <label className="inputLabel">보내시는 분</label>
          <input
            className="infoInput"
            type="text"
            value={senderName}
            onChange={(e) => setSenderName(e.target.value)}
            placeholder="보내는 사람 이름을 입력하세요"
          />
        </div>
        <div className="inputContainer">
          <label className="inputLabel">금액을 입력해주세요.</label>
          <input
            className="infoInput"
            type="text"
            value={price}
            onChange={(e) => setPrice(Number(e.target.value))}
            placeholder="가격을 입력하세요"
          />
        </div>
        <div className="inputContainer">
          <label className="inputLabel">주최자와의 관계</label>
          <textarea
            className="infoArea"
            type="text"
            onChange={(e) => setDescription(e.target.value)}
            placeholder="메모를 입력하세요"
          />
        </div>
        <div className="inputContainer">
          <label className="inputLabel">전화번호</label>
          <input
            className="infoInput"
            type="tel"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="전화번호를 입력하세요"
          />

        </div>

        <div id="payment-method" style={{ display: "flex", justifyContent: "center", width: "100%" }}>
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
