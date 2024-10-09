import { useEffect, useState } from "react";
import { Link, useNavigate, useSearchParams, useLocation } from "react-router-dom";
import '../Success.css'

export function Success() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [responseData, setResponseData] = useState(null);
  const [countDown, setCountDown] = useState(5);
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const senderName = params.get("senderName");
  const description = params.get("description");
  const eventId = params.get("eventId");
  const memo = params.get("memo");
  console.log(senderName, description, eventId)
  useEffect(() => {
    async function confirm() {
      const requestData = {
        orderId: searchParams.get("orderId"),
        amount: searchParams.get("amount"),
        paymentKey: searchParams.get("paymentKey"),
        senderName: senderName,
        eventId: eventId,
        description: description,
        memo: memo
      };

      const response = await fetch("https://j11a308.p.ssafy.io/confirm/payment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      });

      const json = await response.json();

      if (!response.ok) {
        throw { message: json.message, code: json.code };
      }

      return json;
    }

    confirm()
      .then((data) => {
        setResponseData(data);
      })
      .catch((error) => {
        navigate(`/fail?code=${error.code}&message=${error.message}`);
      });
    const countDownInterval = setInterval(() => {
      setCountDown((prev) => prev - 1)
    }, 1000);
    const timer = setTimeout(() => {
      navigate('/');
    }, 5000);
    return () => {
      clearTimeout(timer);
      clearInterval(countDownInterval);
    }
  }, [searchParams]);

  return (
    <>
      <div className="box_section" style={{ width: "600px" }}>
        <img width="100px" src="https://static.toss.im/illusts/check-blue-spot-ending-frame.png" />
        <h2>결제를 완료했어요</h2>
        <div className="p-grid typography--p" style={{ marginTop: "50px" }}>
          <div className="p-grid-col text--left">
            <b>결제금액</b>
          </div>
          <div className="p-grid-col text--right" id="amount">
            {`${Number(searchParams.get("amount")).toLocaleString()}원`}
          </div>
        </div>
         <div className="countdown-container">
          <div className="countdown-number">{countDown}</div>
          <div className="countdown-text">초 후에 홈 화면으로 이동합니다.</div>
        </div>
      </div>
    </>
  );
}
