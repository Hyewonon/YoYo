import { useEffect, useState } from "react";
import { Link, useNavigate, useSearchParams, useLocation } from "react-router-dom";

export function Success() {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const [responseData, setResponseData] = useState(null);
  const { senderName, description } = location.state;

  useEffect(() => {
    async function confirm() {
      const requestData = {
        orderId: searchParams.get("orderId"),
        amount: searchParams.get("amount"),
        paymentKey: searchParams.get("paymentKey"),
        senderName: senderName,
        receiverId : 59,
        eventId: 1,
        desciption : description
      };

      const response = await fetch("/api/confirm/payment", {
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
    </div>
      <div className="box_section" style={{ width: "600px", textAlign: "left" }}>
        <b>Response Data :</b>
        <div id="response" style={{ whiteSpace: "initial" }}>
          {responseData && <pre>{JSON.stringify(responseData, null, 4)}</pre>}
        </div>
      </div>
    </>
  );
}
