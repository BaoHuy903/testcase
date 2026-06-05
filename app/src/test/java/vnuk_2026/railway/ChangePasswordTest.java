package vnuk_2026.railway;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vnuk_2026.pages.HomePage;
import vnuk_2026.pages.LoginPage;
import vnuk_2026.pages.ChangePasswordPage;
import vnuk_2026.utils.WebDriverUtils;

public class ChangePasswordTest extends RailwayTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    ChangePasswordPage changePasswordPage = new ChangePasswordPage();

    private final String VALID_EMAIL = "batoannguyen52@gmail.com";
    private final String DEFAULT_PASSWORD = "123456789"; // Mật khẩu mặc định ban đầu của tài khoản dùng để test đổi mật khẩu

    // Thông báo lỗi hiển thị ở khung đỏ cho các trường hợp CHA_002, CHA_003, CHA_004
    private final String GENERAL_ERROR_MSG = "Password change failed. Please correct the errors and try again.";
    
    // Thông báo lỗi hiển thị ở khung đỏ riêng cho trường hợp CHA_005
    private final String INCORRECT_CURRENT_PASSWORD_ERROR_MSG = "An error occurred when attempting to change the password. Maybe your current password is incorrect.";

    @BeforeMethod
    public void setUp() {
        // Trước mỗi Test Case: Mở trang -> Đăng nhập bằng mật khẩu mặc định -> Đi tới tab Change Password
        homePage.open();
        homePage.navigateToLoginPage();
        loginPage.login(VALID_EMAIL, DEFAULT_PASSWORD);
        homePage.navigateToChangePasswordPage();
    }

    @AfterMethod
    public void tearDownSession() {
        // Xóa sạch session/cookies sau mỗi test case để đưa trình duyệt về trạng thái logout
        try {
            WebDriverUtils.get().manage().deleteAllCookies();
        } catch (Exception e) {
            System.out.println("Không thể xóa cookies: " + e.getMessage());
        }
    }

    @Test
    public void CHA_001_changePasswordSuccessfully() {
        String newPassword = "987654321"; // Mật khẩu mới dùng để đổi trong test case này

        try {
            // Tiến hành đổi sang mật khẩu mới
            changePasswordPage.changePassword(DEFAULT_PASSWORD, newPassword, newPassword);
            
            String expectedSuccessMsg = "Your password has been updated!"; 
            Assert.assertEquals(changePasswordPage.getSuccessMessage(), expectedSuccessMsg);
            
        } finally {
            // Khối finally luôn luôn chạy kể cả khi Assert ở trên có Fail hay Pass
            // Đảm bảo tài khoản luôn được khôi phục về mật khẩu mặc định ban đầu
            changePasswordPage.changePassword(newPassword, DEFAULT_PASSWORD, DEFAULT_PASSWORD);
        }
    }

    @Test
    public void CHA_002_changePasswordWithEmptyFields() {
        // Để trống tất cả các trường dữ liệu đầu vào
        changePasswordPage.changePassword("", "", "");
        
        // Kiểm tra hiển thị thông báo lỗi chung ở khung đỏ
        Assert.assertTrue(changePasswordPage.getErrorMessage().contains(GENERAL_ERROR_MSG));
    }

    @Test
    public void CHA_003_changePasswordWithUnmatchedConfirmPassword() {
        String newPassword = "987654321"; // Mật khẩu mới dùng để đổi trong test case này
        String unmatchedConfirmPassword = "0123456789"; // Confirm Password không khớp với New Password

        // Nhập Confirm Password không khớp với New Password
        changePasswordPage.changePassword(DEFAULT_PASSWORD, newPassword, unmatchedConfirmPassword);
        
        // Kiểm tra hiển thị thông báo lỗi chung ở khung đỏ
        Assert.assertTrue(changePasswordPage.getErrorMessage().contains(GENERAL_ERROR_MSG));
    }

    @Test
    public void CHA_004_changePasswordWithInvalidLength() {
        String shortPassword = "123"; // Độ dài ngắn hơn giới hạn tối thiểu (8 ký tự)
        
        changePasswordPage.changePassword(DEFAULT_PASSWORD, shortPassword, shortPassword);
        
        // Kiểm tra hiển thị thông báo lỗi chung ở khung đỏ
        Assert.assertTrue(changePasswordPage.getErrorMessage().contains(GENERAL_ERROR_MSG));
    }

    @Test
    public void CHA_005_changePasswordWithIncorrectCurrentPassword() {
        String wrongCurrentPassword = "wrongCurrentPassword123@";
        String newPassword = "987654321"; // Mật khẩu mới dùng để đổi trong test case này

        // Nhập sai mật khẩu hiện tại
        changePasswordPage.changePassword(wrongCurrentPassword, newPassword, newPassword);
        
        // Kiểm tra hiển thị thông báo lỗi riêng ở khung đỏ
        Assert.assertTrue(changePasswordPage.getErrorMessage().contains(INCORRECT_CURRENT_PASSWORD_ERROR_MSG));
    }
}