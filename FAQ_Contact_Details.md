# Giải thích chi tiết: FAQ & Contact (Code + Flow)

File này tóm tắt các lớp và hàm chính liên quan đến phần `Contact` và `FAQ`, kèm đoạn mã và luồng thực thi để bạn đọc.

---

## WebDriverUtils

File: `app/src/main/java/vnuk_2026/utils/WebDriverUtils.java`

Mục đích: quản lý khởi tạo, tái sử dụng và đóng `WebDriver` cho các test; có helper xóa quảng cáo trên trang.

Hàm chính:

- `get()`

```java
private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

public static WebDriver get() {
    if (drivers.get() == null) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        var driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        drivers.set(driver);
    }
    return drivers.get();
}
```

Flow: khởi tạo lazy `ChromeDriver` cho thread hiện tại, trả về driver đã lưu trong `ThreadLocal`.

- `removeAds()`

Chạy một đoạn JavaScript để tìm và remove/ẩn các phần tử quảng cáo có liên kết tới `somee.com`.

- `quit()`

Đóng trình duyệt và xóa driver khỏi `ThreadLocal`.

---

## HomePage

File: `app/src/main/java/vnuk_2026/pages/HomePage.java`

Các hàm chính và flow:

- `open()`

```java
public void open() {
    String aut = System.getProperty("autEnvironment", "B2").toLowerCase();
    String baseUrl = String.format("http://railwayb%s.somee.com", aut.replace("b", ""));
    WebDriverUtils.get().get(baseUrl);
    WebDriverUtils.removeAds();
}
```

Flow: xây URL dựa trên `autEnvironment`, mở trang chủ rồi xóa quảng cáo.

- `navigateToContactPage()`, `navigateToLoginPage()`, `navigateToBookTicketPage()`, `navigateToTimeTablePage()`

Pattern: gọi `removeAds()` rồi `findElement(...).click()` để điều hướng.

- `navigateToFAQPage()`

Thử click menu FAQ; nếu click không chuyển trang thì kiểm tra URL và điều hướng trực tiếp tới `/Page/FAQ.cshtml`. Cuối cùng gọi `removeAds()`.

- `isContactTabSelected()` / `isFAQTabSelected()`

Lấy phần tử liên kết menu, tìm parent `<li>`, đọc `class` attribute và kiểm tra chuỗi `selected`.

---

## ContactPage

File: `app/src/main/java/vnuk_2026/pages/ContactPage.java`

Hàm chính:

- `isContactDetailsDisplayed()`

```java
private final By emailLinkBy = By.cssSelector("a[href='mailto:thanh.viet.le@logigear.com']");

public boolean isContactDetailsDisplayed() {
    try {
        WebDriverUtils.removeAds();
        WebElement emailLink = new org.openqa.selenium.support.ui.WebDriverWait(WebDriverUtils.get(), java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(emailLinkBy));
        return emailLink.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}
```

Flow: chờ tối đa 10s cho link `mailto:` hiển thị; trả `true` nếu có, `false` nếu lỗi/tắt.

- `getEmailLinkHref()`

Trả giá trị `href` của liên kết email để test so sánh định dạng `mailto:`.

---

## FAQPage

File: `app/src/main/java/vnuk_2026/pages/FAQPage.java`

Hàm chính:

- `getQuestionHref(int index)`

```java
public String getQuestionHref(int index) {
    WebElement questionLink = WebDriverUtils.get().findElement(By.cssSelector("a[href='#" + index + "']"));
    return questionLink.getAttribute("href");
}
```

- `clickQuestion(int index)`

Click anchor để cuộn/activate phần trả lời.

- `isAnswerTargetDisplayed(int index)`

Kiểm tra phần tử có `id=index` có hiển thị hay không, bắt lỗi và trả `false` nếu không tồn tại.

- `clickCreateAccountLink()` / `clickBookTicketLink()`

Tìm liên kết nhúng trong phần trả lời (dưới `id='1'`), click; nếu bị intercept thì fallback bằng `JavascriptExecutor` để click.

---

## RailwayTest (base)

File: `app/src/test/java/vnuk_2026/railway/RailwayTest.java`

Chứa một `@AfterClass`:

```java
@AfterClass
public void quitDriver() {
    WebDriverUtils.quit();
}
```

Flow: đóng browser sau khi test class hoàn tất.

---

## Tests: ContactTest & FAQTest

- `ContactTest` (file: `app/src/test/java/vnuk_2026/railway/ContactTest.java`)
  - `verifyContactDetailsDisplayed()` : open → navigateToContactPage → assert `isContactDetailsDisplayed()`.
  - `verifyEmailLinkWorks()` : open → navigateToContactPage → lấy `href` và assert bằng `mailto:...`.
  - `verifyContactTabHighlighted()` : open → navigateToContactPage → assert `isContactTabSelected()`.

- `FAQTest` (file: `app/src/test/java/vnuk_2026/railway/FAQTest.java`)
  - `verifyFAQQuestionsScroll()` : open → navigateToFAQPage → lặp i=1..8: kiểm tra `href` endsWith `#i`, click → assert target hiển thị.
  - `verifyFAQEmbeddedLinks()` : click link register → assert URL chứa `register` → quay lại FAQ → click book ticket → assert URL chứa `login` hoặc `bookticket`.
  - `verifyFAQTabHighlighted()` : open → navigateToFAQPage → assert `isFAQTabSelected()`.

---

## Ghi chú

- Tôi đã sửa `WebDriverUtils.get()` để thêm `--remote-allow-origins=*` (và bỏ `--guest`) nhằm khắc phục lỗi `NoSuchWindowException`/khởi driver không ổn định.
- Nếu bạn muốn file này ở vị trí khác hoặc format khác (ví dụ PDF), cho tôi biết.

---

File này được tạo tự động cho bạn đọc tại: `FAQ_Contact_Details.md` (gốc workspace).
