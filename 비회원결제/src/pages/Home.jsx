import React from 'react'
import '../App.css'
export default function Home() {
  return (
    <div className='wrapper'>
      <div className="box_section">
        <h1>YoYo!</h1>
        <h3>경조사 비용 관리 어플리케이션</h3>
        <h4>YoYo를 설치하고 경조사 비용 관리를 자동으로 맡겨요!</h4>
         <button className="button" onClick={() => {
          const userResponse = window.confirm("YoYo를 설치하시겠습니까?");
          if (userResponse) {
            alert("앱을 다운로드합니다.");
            window.location.href = "https://expo.dev/artifacts/eas/pJHh1bzZbiBetzTMVaVVyh.apk";
          } else {
            return
          }
        }}>
          App 다운로드
        </button>
      </div>
    </div>
  )
}
