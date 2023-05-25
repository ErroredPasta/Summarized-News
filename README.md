# Summarized-News

The Guardian API와 Open AI API를 이용한 줄거리를 보여주는 뉴스 앱

실행 전 각각의 API key를 발급받아서 아래와 같이 API key들을 추가해주세요.

[Branch]
- main : 각각 news_data, summary_data 모듈에 secret package에 NEWS_API_KEY, SUMMARIZER_API_KEY를 추가
- single-module : local.properties에 THE_GUARDIAN_API_KEY, OPEN_AI_API_KEY를 추가

[Changelog]

2023.03.24 : 멀티 모듈 도입

[API]
- The Guardian : https://open-platform.theguardian.com/
- Open AI : https://platform.openai.com/

[What I used]
- Architecture : MVI
- AAC : ViewModel, DataBinding, Navigation, Paging
- Async : Coroutine + Flow
- DI : Dagger Hilt
- Network : Retrofit2 + Gson
