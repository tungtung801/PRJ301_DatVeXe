package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;
import java.io.File;
import model.UserDTO;

public class Email {

    static final String from = "longtest772025@gmail.com";
    static final String password = "lwip zpma lohz pecf";

    // Template 1: Email thông báo chung
    public static String createNotificationTemplate(String customerName, String title, String content, String companyName) {
        return String.format(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>"
                + "</div>"
                + "<h2 style='color: #333; text-align: center; margin-bottom: 20px;'>%s</h2>"
                + "<div style='background-color: #f9f9f9; padding: 20px; border-radius: 5px; margin-bottom: 20px;'>"
                + "<p style='color: #666; font-size: 16px; line-height: 1.6;'>Xin chào <strong>%s</strong>,</p>"
                + "<p style='color: #666; font-size: 16px; line-height: 1.6;'>%s</p>"
                + "</div>"
                + "<div style='text-align: center; margin-top: 30px;'>"
                + "<p style='color: #999; font-size: 14px;'>Trân trọng,<br><strong>%s</strong></p>"
                + "</div>"
                + "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>"
                + "<div style='text-align: center; color: #999; font-size: 12px;'>"
                + "<p>Email này được gửi tự động, vui lòng không trả lời.</p>"
                + "</div>"
                + "</div>",
                title, customerName, content, companyName
        );
    }

    // Template 2: Email xác nhận đặt vé
 public static String createBookingConfirmationTemplate(int bookingId, int scheduleId,
            String passengerName, String passengerPhone, String ticketCode) {
        return String.format(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>"
                + "</div>"
                + "<h2 style='color: #4CAF50; text-align: center; margin-bottom: 20px;'>✅ XÁC NHẬN ĐẶT VÉ THÀNH CÔNG</h2>"
                + "<div style='background-color: #f0f8ff; padding: 20px; border-radius: 5px; margin-bottom: 20px;'>"
                + "<p style='color: #333; font-size: 16px;'>Xin chào <strong>%s</strong>,</p>" 
                + "<p style='color: #333; font-size: 16px;'>Cảm ơn bạn đã đặt vé tại hệ thống của chúng tôi!</p>"
                + "</div>"
                + "<div style='background-color: #fff; border: 2px solid #4CAF50; border-radius: 5px; padding: 20px; margin-bottom: 20px;'>"
                + "<h3 style='color: #4CAF50; margin-top: 0;'>THÔNG TIN VÉ</h3>"
                + "<table style='width: 100%%; border-collapse: collapse;'>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Mã đặt vé:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%d</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Mã lịch trình:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%d</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Tên hành khách:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Số điện thoại:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Mã vé:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "</table>"
                + "</div>"
                + "<div style='background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 15px; margin-bottom: 20px;'>"
                + "<p style='color: #856404; margin: 0;'><strong>Lưu ý:</strong> Vui lòng có mặt tại bến xe trước 15 phút để làm thủ tục lên xe.</p>"
                + "</div>"
                + "<div style='text-align: center; margin-top: 30px;'>"
                + "<p style='color: #999; font-size: 14px;'>Chúc bạn có chuyến đi an toàn, vui vẻ!<br><strong>Đội ngũ hỗ trợ khách hàng</strong></p>"
                + "</div>"
                + "</div>",
                passengerName, bookingId, scheduleId, passengerName, passengerPhone, ticketCode
        );
    }

    // Template 3: Email khuyến mãi
    public static String createPromotionTemplate(String customerName, String promotionTitle,
            String discount, String validDate, String promoCode) {
        return String.format(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); border-radius: 10px;'>"
                + "<div style='background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>" + "<h2 style='color: #e74c3c; text-align: center; margin-bottom: 20px; font-size: 28px;'>🎉 %s 🎉</h2>"
                + "<div style='background-color: #ff6b6b; color: white; padding: 20px; border-radius: 5px; text-align: center; margin-bottom: 20px;'>"
                + "<h3 style='margin: 0; font-size: 24px;'>GIẢM GIÁ %s</h3>"
                + "</div>"
                + "<div style='background-color: #f9f9f9; padding: 20px; border-radius: 5px; margin-bottom: 20px;'>"
                + "<p style='color: #333; font-size: 16px;'>Xin chào <strong>%s</strong>,</p>"
                + "<p style='color: #333; font-size: 16px;'>Chúng tôi có tin tuyệt vời dành cho bạn! Đừng bỏ lỡ cơ hội tiết kiệm khi đặt vé xe khách.</p>"
                + "</div>"
                + "<div style='border: 2px dashed #ff6b6b; border-radius: 5px; padding: 20px; text-align: center; margin-bottom: 20px;'>"
                + "<h3 style='color: #ff6b6b; margin-top: 0;'>MÃ KHUYẾN MÃI</h3>"
                + "<div style='background-color: #ff6b6b; color: white; padding: 15px; border-radius: 5px; font-size: 20px; font-weight: bold; letter-spacing: 2px;'>%s</div>"
                + "<p style='color: #666; margin-bottom: 0;'>Có hiệu lực đến: <strong>%s</strong></p>"
                + "</div>"
                + "<div style='text-align: center; margin-top: 30px;'>"
                + "<a href='#' style='background-color: #4CAF50; color: white; padding: 15px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px;'>ĐẶT VÉ NGAY</a>"
                + "</div>"
                + "</div>"
                + "</div>",
                promotionTitle, discount, customerName, promoCode, validDate
        );
    }

    // Template 4: Email nhắc nhở
    public static String createReminderTemplate(String customerName, String reminderTitle,
            String reminderContent, String actionRequired) {
        return String.format(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>"
                + "</div>"
                + "<h2 style='color: #f39c12; text-align: center; margin-bottom: 20px;'>⏰ %s</h2>"
                + "<div style='background-color: #fff3cd; border-left: 4px solid #f39c12; padding: 20px; margin-bottom: 20px;'>"
                + "<p style='color: #856404; font-size: 16px; margin: 0;'>Xin chào <strong>%s</strong>,</p>"
                + "<p style='color: #856404; font-size: 16px; margin: 10px 0 0 0;'>%s</p>"
                + "</div>"
                + "<div style='background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 15px; margin-bottom: 20px;'>"
                + "</div>" + "<p style='color: #0c5460; margin: 0;'><strong>Bạn cần:</strong> %s</p>"
                + "</div>"
                + "<div style='text-align: center; margin-top: 30px;'>"
                + "<p style='color: #999; font-size: 14px;'>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>"
                + "</div>"
                + "</div>",
                reminderTitle, customerName, reminderContent, actionRequired
        );
    }

    // Template 5: Email đăng ký thành công
    public static String createRegistrationSuccessTemplate(String customerName, String email,
            String sdt, String loginUrl) {
        return String.format(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>"
                + "</div>"
                + "<h2 style='color: #28a745; text-align: center; margin-bottom: 20px;'>🎉 CHÀO MỪNG BẠN ĐẾN VỚI CHÚNG TÔI!</h2>"
                + "<div style='background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; padding: 20px; margin-bottom: 20px;'>"
                + "<h3 style='color: #155724; margin-top: 0;'>✅ Đăng ký thành công!</h3>"
                + "<p style='color: #155724; font-size: 16px; margin: 10px 0;'>Xin chào <strong>%s</strong>,</p>"
                + "<p style='color: #155724; font-size: 16px; margin: 10px 0;'>Chúc mừng bạn đã đăng ký tài khoản thành công tại hệ thống đặt vé xe khách của chúng tôi!</p>"
                + "</div>"
                + "<div style='background-color: #f8f9fa; border-left: 4px solid #28a745; padding: 20px; margin-bottom: 20px;'>"
                + "<h4 style='color: #333; margin-top: 0;'>THÔNG TIN TÀI KHOẢN</h4>"
                + "<table style='width: 100%%; border-collapse: collapse;'>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Tên tài khoản:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Email:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Ngày đăng ký:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td></tr>"
                + "</table>"
                + "</div>"
                + "<div style='background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 15px; margin-bottom: 20px;'>"
                + "<p style='color: #856404; margin: 0;'><strong>Lưu ý:</strong> Nếu bạn gặp khó khăn trong việc sử dụng, vui lòng liên hệ với chúng tôi qua email hoặc hotline hỗ trợ.</p>"
                + "</div>" + "<div style='text-align: center; margin-top: 30px;'>"
                + "<p style='color: #999; font-size: 14px;'>Cảm ơn bạn đã tin tưởng và lựa chọn dịch vụ của chúng tôi!<br><strong>Đội ngũ hỗ trợ khách hàng</strong></p>"
                + "</div>"
                + "</div>",
                customerName, customerName, email, sdt, loginUrl
        );
    }

    // Template 6: Email thay đổi mật khẩu thành công
    public static String createPasswordChangeSuccessTemplate(String customerName, String newPassword,
            String changeTime, String phoneNumber) {
        // Use string concatenation instead of String.format to avoid semicolon issues
        return "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<div style='text-align: center; margin-bottom: 30px;'>"
                + "<img src='cid:logo' alt='Logo' style='max-width: 200px; height: auto;'>"
                + "</div>"
                + "<h2 style='color: #17a2b8; text-align: center; margin-bottom: 20px;'>🔐 MẬT KHẨU ĐÃ ĐƯỢC THAY ĐỔI</h2>"
                + "<div style='background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 20px; margin-bottom: 20px;'>"
                + "<h3 style='color: #0c5460; margin-top: 0;'>✅ Thay đổi mật khẩu thành công!</h3>"
                + "<p style='color: #0c5460; font-size: 16px; margin: 10px 0;'>Xin chào <strong>" + customerName + "</strong>,</p>"
                + "<p style='color: #0c5460; font-size: 16px; margin: 10px 0;'>Mật khẩu tài khoản của bạn đã được thay đổi thành công.</p>"
                + "</div>"
                + "<div style='background-color: #f8f9fa; border-left: 4px solid #17a2b8; padding: 20px; margin-bottom: 20px;'>"
                + "<h4 style='color: #333; margin-top: 0;'>THÔNG TIN THAY ĐỔI</h4>"
                + "<table style='width: 100%; border-collapse: collapse;'>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Mật khẩu vừa được thay đổi:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>" + newPassword + "</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Thời gian:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>" + changeTime + "</td></tr>"
                + "<tr><td style='padding: 8px; border-bottom: 1px solid #eee;'><strong>Số điện thoại:</strong></td><td style='padding: 8px; border-bottom: 1px solid #eee;'>" + phoneNumber + "</td></tr>"
                + "</table>"
                + "</div>"
                + "<div style='background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 5px; padding: 20px; margin-bottom: 20px;'>"
                + "<h4 style='color: #856404; margin-top: 0;'>⚠️ LƯU Ý BẢO MẬT</h4>"
                + "<ul style='color: #856404; margin: 10px 0;'>"
                + "<li>Không chia sẻ mật khẩu với bất kỳ ai</li>"
                + "<li>Đăng xuất khỏi các thiết bị công cộng</li>"
                + "<li>Thay đổi mật khẩu định kỳ để bảo mật tài khoản</li>"
                + "</ul>"
                + "</div>"
                + "<div style='text-align: center; margin-top: 30px; padding: 20px; border-top: 1px solid #eee;'>"
                + "<p style='color: #666; font-size: 14px; margin: 5px 0;'>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ với chúng tôi ngay lập tức.</p>"
                + "<p style='color: #666; font-size: 14px; margin: 5px 0;'>Hotline: 1900-xxxx | Email: support@company.com</p>"
                + "<p style='color: #999; font-size: 12px; margin: 15px 0 0 0;'>© 2024 Company Name. All rights reserved.</p>"
                + "</div>"
                + "</div>";
    }

    // Phương thức gửi email với template tùy chỉnh
    public static boolean sendEmailWithCustomTemplate(String to, String subject, String htmlContent, String imagePath) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);

            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            msg.setSentDate(date);
            msg.setReplyTo(InternetAddress.parse(from, false));

            Multipart multipart = new MimeMultipart("related");

            // Phần HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/HTML; charset=UTF-8");
            multipart.addBodyPart(htmlPart);

            // Phần hình ảnh nhúng
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    MimeBodyPart imagePart = new MimeBodyPart();
                    DataSource source = new FileDataSource(imageFile);
                    imagePart.setDataHandler(new DataHandler(source));
                    imagePart.setHeader("Content-ID", "<logo>");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    multipart.addBodyPart(imagePart);
                } else {
                    System.out.println("Cảnh báo: Không tìm thấy file hình ảnh tại: " + imagePath);
                }
            }

            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Gửi email thành công với template tùy chỉnh");
            return true;

        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
        }
    }

//    // Ví dụ sử dụng các template
//    public static void main(String[] args) {
//        String imagePath = "D:\\FPTU\\2025\\Sumer25\\PRJ301\\PRJ301_SU25_DatVeXe (1)\\PRJ301_SU25_DatVeXe\\Core\\web\\assets\\images\\tunglong_logo.png";
//
////        // Email xác nhận đặt vé
////        String bookingTemplate = createBookingConfirmationTemplate(
////            "Nguyễn Văn A", 
////            "TL2025001", 
////            "Hà Nội - Đà Nẵng", 
////            "15/07/2025 - 08:00", 
////            "A12", 
////            "350,000"
////        );
////        
////        sendEmailWithCustomTemplate("longat1103@gmail.com", 
////                                   "Xác nhận đặt vé thành công", 
////                                   bookingTemplate, 
////                                   imagePath);
////        
////        
//        //  Email đăng ký thành công
////        HttpServletRequest request = null;
////        HttpSession session = request.getSession();
////        UserDTO user = (UserDTO) session.getAttribute("user");
////        String registrationTemplate = createRegistrationSuccessTemplate(
////                user.getFullname(),
////                user.getEmail(),
////                user.getPhone(),
////                ""
////        );
//
////        sendEmailWithCustomTemplate("halekaiqkahsn@gmail.com",
////                "🎉 Chào mừng bạn đến với Tùng Long Bus!",
////                registrationTemplate,
////                imagePath);
//
//        // Email thay đổi mật khẩu thành công
//        String passwordChangeTemplate = createPasswordChangeSuccessTemplate(
//            "Nguyễn Văn A",
//            "15/07/2025 - 16:45",
//            "192.168.1.100",
//            "Chrome on Windows 10"
//        );
//        
//        sendEmailWithCustomTemplate("halekaiqkahsn@gmail.com", 
//                                   "Mật khẩu đã được thay đổi thành công", 
//                                   passwordChangeTemplate, 
//                                   imagePath);
//    }
    }


