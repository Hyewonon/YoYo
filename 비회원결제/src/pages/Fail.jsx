import { Link, useSearchParams } from "react-router-dom";

export function FailPage() {
  const [searchParams] = useSearchParams();

  return (
    <div id="info" className="box_section" style={{ width: "600px" }}>
      <img width="100px" src="https://static.toss.im/lotties/error-spot-no-loop-space-apng.png" alt="에러 이미지" />
      <h2>결제를 실패했어요</h2>

      <div className="p-grid typography--p" style={{ marginTop: "50px" }}>
        <div className="p-grid-col text--left">
          <b>에러!!</b>
        </div>
        <div className="p-grid-col text--right" id="message">{`${searchParams.get("message")}`}</div>
      </div>
    </div>
  );
}
