<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tùng Long Bus Lines</title>
        <link rel="stylesheet" href="assets/css/DatVeXe.css"/>
    </head>
    <body>

        <%@ include file="header.jsp" %>
        <main>
            <div class="banner-video">
                <video autoplay loop muted poster="poster_video.jpg">
                    <source src="assets/videos/Tùng_Long_Bus_Lines_Video.mp4" type="video/mp4">
                    <source src="video_nen.webm" type="video/webm">
                </video>
            </div>
        </main>

    </section>
    <section class="popular-trip-section">
        <div class="container popular-controller"> <h1>Các tuyến đường được ưa chuộng nhất</h1>
            <div class="popular-trip-row">
                <div class="trip-item">
                    <div class="trip-image">
                        <img src="assets/images/dalat_huyndai.png" alt="Xe đi Đà Lạt">
                    </div>
                    <div class="trip-item-detail">
                        <h3>TP Hồ Chí Minh ➢ Đà Lạt</h3>
                        <p class="description">
                            Hãy chuẩn bị cho một chuyến đi đầy cảm hứng từ TP. Hồ Chí Minh đến thành phố ngàn hoa Đà Lạt! Tận hưởng khung cảnh thiên nhiên thay đổi dọc đường, từ sự nhộn nhịp của đô thị đến không khí trong lành, mát mẻ của cao nguyên. Chuyến đi này không chỉ đưa bạn đến điểm đến, mà còn là cơ hội để thư giãn và bắt đầu những trải nghiệm đáng nhớ.
                        </p>
                        <p><strong>Quãng đường: </strong>305 km</p>
                        <p><strong>Tổng thời gian: </strong>8 giờ</p>
                        <p class="price"><strong>Giá vé: </strong>250.000đ</p>
                        <a href="${pageContext.request.contextPath}/ScheduleController" class="btn-trip-detail">Tìm hiểu thêm</a>
                    </div>
                </div>

                <div class="trip-item">
                    <div class="trip-image">
                        <img src="assets/images/hcm_nhatrang.png" alt="Xe đi Nha Trang">
                    </div>
                    <div class="trip-item-detail">
                        <h3>TP Hồ Chí Minh ➢ Nha Trang</h3>
                        <p class="description">
                            Khám phá vẻ đẹp bờ biển Nha Trang với chuyến đi thoải mái từ TP. Hồ Chí Minh. Tận hưởng nắng vàng, biển xanh và những bãi cát trắng trải dài. Chúng tôi mang đến hành trình tiện nghi để bạn sẵn sàng cho kỳ nghỉ dưỡng tuyệt vời tại thành phố biển này.
                        </p>
                        <p><strong>Quãng đường: </strong>397 km</p>
                        <p><strong>Tổng thời gian: </strong>9 giờ</p>
                        <p class="price"><strong>Giá vé: </strong>450.000đ</p>
                        <a href="${pageContext.request.contextPath}/ScheduleController" class="btn-trip-detail">Tìm hiểu thêm</a>
                    </div>
                </div>

                <div class="trip-item">
                    <div class="trip-image">
                        <img src="assets/images/hcm_phanthiett.png" alt="Xe đi Phan Thiết">
                    </div>
                    <div class="trip-item-detail">
                        <h3>TP Hồ Chí Minh ➢ Phan Thiết</h3>
                        <p class="description">
                            Tận hưởng hành trình từ TP. Hồ Chí Minh đến Phan Thiết, thành phố biển đầy nắng và gió với những đồi cát mênh mông và hải sản tươi ngon. Chuyến đi này sẽ đưa bạn đến những trải nghiệm độc đáo, từ khám phá ẩm thực đến các hoạt động thể thao biển sôi động.
                        </p>
                        <p><strong>Quãng đường: </strong>200 km</p>
                        <p><strong>Tổng thời gian: </strong>5 giờ</p>
                        <p class="price"><strong>Giá vé: </strong>260.000đ</p>
                        <a href="${pageContext.request.contextPath}/ScheduleController" class="btn-trip-detail">Tìm hiểu thêm</a>
                    </div>
                </div>
            </div>
        </div>
    </section>


    <section class="about-us-section">
        <div class="container">
            <h2>Tùng Long Bus Lines</h2>
            <p class="intro-text">Với gần 7 năm kinh nghiệm trong ngành vận tải hành khách, Tùng Long Bus Lines tự hào là đối tác tin cậy của hàng triệu khách hàng trên khắp Việt Nam. Chúng tôi cam kết mang đến những chuyến đi an toàn, tiện nghi và đúng giờ, kết nối bạn với mọi miền đất nước.</p>
            <div class="stats-grid">
                <div class="stat-item">
                    <h3>6+</h3>
                    <p>Năm kinh nghiệm</p>
                </div>
                <div class="stat-item">
                    <h3>15+</h3>
                    <p>Tuyến đường</p>
                </div>
                <div class="stat-item">
                    <h3>195K+</h3>
                    <p>Khách hàng hài lòng</p>
                </div>
                <div class="stat-item">
                    <h3>24/7</h3>
                    <p>Hỗ trợ</p>
                </div>
            </div>
            <a href="aboutUs.jsp" class="btn-primary">Tìm hiểu thêm về chúng tôi</a>
        </div>
    </section>


    <%@include file="footer.jsp" %>

</body>
</html>