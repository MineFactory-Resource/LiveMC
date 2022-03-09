#LiveMC
## 개발 환경
[Paper-1.18.1-109](https://papermc.io/downloads#Paper-1.18)

## LiveMC 플러그인 설명
여러 인터넷 방송, 비디오 플랫폼에서 일어난 이벤트를 받아와 서버에서 명령어를 실행시킬 수 있도록 하는 플러그인 입니다.

--------------
## LiveMC 플러그인 설치 방법
1. LiveMC Releases 항목에서 최신 버전의 플러그인 파일을 다운로드 받습니다. 
2. 다운로드받은 *.jar 파일을 서버의 plugins 폴더로 이동시킵니다.

--------------
## LiveMC 플러그인 사용 방법
#### LiveMC config 양식
```yaml
#config.yml
Twip:
  key: Alert Box id
  token: API token
  Donation: "say Thanks for donation !"
  MarketReward: "say Thanks for using the market !"
```
Twip의 key와 token을 발급받는 방법은 [이곳](#twip-api-tokenkey-발급-받는-방법)을 참조해주세요.  
Donation은 Twip에서 후원이 발생되었을때 실행되는 명령어 입니다.  
MarketReward는 Twip에 보상을 포인트로 구매하였을때 실행되는 명령어 입니다.

명령어는 '/' 없이 설정해주세요.
### Twip API Token&Key 발급 받는 방법
Key와 Token을 얻기전에 Twip으로 접속해 로그인 후 권한을 부여해주세요.

[Twip Security](https://twip.kr/dashboard/security)  

위의 ``Twip Security``를 클릭하면 대시보드의 보안 설정 페이지로 이동됩니다.  
스크롤을 맨 아래로 내리면 `API 토큰 발급`란에 원하는 Alert Box 를 선택 후 발급하기를 눌러주세요.  
정상적으로 발급된 Key 값(얼럿박스 ID)과 API 토큰을 LiveMC 플러그인 폴더에 있는 ``config.yml`` 파일에 복사&붙여넣기를 해주세요.