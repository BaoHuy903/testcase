# Luồng kiểm thử: FAQ & Contact

Tệp này mô tả luồng thao tác của các trang `Home`, `Contact`, `FAQ` và cách tests tương tác với chúng.

## Tóm tắt ngắn
- Test gọi `homePage.open()` để mở trang chủ.
- Test điều hướng tới `Contact` hoặc `FAQ` qua `homePage.navigateTo...()`.
- Các Page Objects (`ContactPage`, `FAQPage`) cung cấp hàm để đọc/nhấp/kiểm tra UI.
- Sau cùng `RailwayTest.@AfterClass` gọi `WebDriverUtils.quit()` để đóng trình duyệt.

## Luồng chi tiết
1. Test bắt đầu → gọi `homePage.open()`
   - `WebDriverUtils.get()` khởi `ChromeDriver` lazy, mở `http://railwayb{env}.somee.com`.
   - `WebDriverUtils.removeAds()` chạy JS xóa overlay quảng cáo.
2. Test gọi `homePage.navigateToContactPage()` hoặc `homePage.navigateToFAQPage()`
   - `navigateToFAQPage()` cố click menu; nếu không chuyển trang thì `get(baseUrl + "/Page/FAQ.cshtml")`.
3. Trên `ContactPage`:
   - `isContactDetailsDisplayed()` chờ và kiểm tra `a[href='mailto:...']` hiển thị.
   - `getEmailLinkHref()` trả `href` để xác thực `mailto:`.
4. Trên `FAQPage`:
   - `getQuestionHref(i)` đọc `href` của anchor `#i`.
   - `clickQuestion(i)` click anchor để cuộn/activate câu trả lời.
   - `isAnswerTargetDisplayed(i)` kiểm tra phần tử có `id=i` hiển thị.
   - `clickCreateAccountLink()` và `clickBookTicketLink()` click các link nhúng; có fallback J S click nếu intercepted.
5. Tests assert kết quả tương ứng (TestNG assertions).
6. Sau khi test class xong, `RailwayTest.quitDriver()` gọi `WebDriverUtils.quit()` đóng browser.

## Sơ đồ luồng (Mermaid)
```mermaid
flowchart TD
  A[Test start] --> B[homePage.open()]
  B --> C{Remove ads}
  C --> D[Navigate to Contact]
  C --> E[Navigate to FAQ]
  D --> D1[ContactPage:isContactDetailsDisplayed]
  D --> D2[ContactPage:getEmailLinkHref]
  D --> D3[HomePage:isContactTabSelected]
  E --> E1[FAQPage:getQuestionHref(i)]
  E --> E2[FAQPage:clickQuestion(i)]
  E --> E3[FAQPage:isAnswerTargetDisplayed(i)]
  E --> E4[FAQPage:clickCreateAccountLink / clickBookTicketLink]
  D1 --> F[Assertions]
  D2 --> F
  E1 --> F
  E2 --> F
  E3 --> F
  E4 --> F
  F --> G[RailwayTest.@AfterClass -> WebDriverUtils.quit()]
  G --> H[Test end]
```

## Lệnh chạy tests (tại workspace gốc)
```powershell
./gradlew.bat test --tests vnuk_2026.railway.ContactTest
./gradlew.bat test --tests vnuk_2026.railway.FAQTest
```

File đã được tạo tại: `FAQ_Contact_Flow.md` trong workspace. Muốn tôi export sang PDF/PNG hoặc commit file này không?