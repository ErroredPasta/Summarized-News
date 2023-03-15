# Summarized-News

The Guardian API와 Open AI API를 이용한 줄거리를 보여주는 뉴스 앱

실행 전 각각의 API key를 발급받아서 local.properties에 THE_GUARDIAN_API_KEY, OPEN_AI_API_KEY로 key를 property로 추가해주세요.

[API]
- The Guardian : https://open-platform.theguardian.com/
- Open AI : https://platform.openai.com/

[What I used]
- Architecture : MVI
- AAC : ViewModel, DataBinding, Navigation, Paging
- Async : Coroutine + Flow
- DI : Dagger Hilt
- Network : Retrofit2 + Gson
