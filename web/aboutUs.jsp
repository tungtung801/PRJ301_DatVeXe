<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tùng Long Bus Lines - Dịch Vụ Vận Chuyển Chất Lượng Cao</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="assets/css/styleContact.css"/>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <main class="main-content" id="home-wrapper">

            <section class="hero-banner section">
                <div class="container">
                    <div class="hero-text fade-up">
                        <h1>Tùng Long Bus Lines</h1>
                        <p class="tagline">An Toàn • Tiện Lợi • Đúng Giờ</p>
                        <p>Hành trình của bạn, niềm tin của chúng tôi. Với hơn 6 năm kinh nghiệm, Tùng Long Bus Lines cam kết mang đến dịch vụ vận chuyển chất lượng cao nhất.</p>
                        <div class="hero-buttons">
                            <a href="${pageContext.request.contextPath}/ScheduleController" class="btn btn-primary"><i class="fas fa-ticket-alt"></i> Đặt Vé Ngay</a>
                            <a href="Contact.jsp" class="btn btn-outline"><i class="fas fa-phone"></i> Liên Hệ Tư Vấn</a>
                        </div>
                    </div>
                    <div class="hero-visual fade-up">
                        <div class="floating-card">
                            <span class="icon">🚌</span>
                            <h3>Dịch Vụ Đẳng Cấp</h3>
                            <p>Trải nghiệm hành trình tuyệt vời</p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="pillars-section section">
                <div class="container">
                    <div class="section-header light fade-up">
                        <h2>Ba Trụ Cột Chất Lượng</h2>
                        <p>Những cam kết vượt trội tạo nên thương hiệu Tùng Long Bus Lines</p>
                    </div>

                    <div class="pillar fade-up">
                        <div class="pillar-visual"><i class="fas fa-shield-alt"></i></div>
                        <div class="pillar-content">
                            <div class="pillar-badge"><i class="fas fa-shield-alt"></i> Ưu tiên số 1</div>
                            <h3>An Toàn Tuyệt Đối</h3>
                            <p>Sự an toàn của hành khách là ưu tiên hàng đầu. Chúng tôi đầu tư mạnh mẽ vào công nghệ và đào tạo chuyên nghiệp để đảm bảo mỗi chuyến đi đều an toàn.</p>
                            <ul class="pillar-features">
                                <li>Đội ngũ tài xế giàu kinh nghiệm</li>
                                <li>Hệ thống giám sát GPS và camera 24/7</li>
                                <li>Xe được bảo dưỡng định kỳ</li>
                                <li>Công nghệ cảnh báo va chạm</li>
                                <li>Bảo hiểm toàn diện cho hành khách</li>
                            </ul>
                        </div>
                    </div>

                    <div class="pillar fade-up">
                        <div class="pillar-visual"><i class="fas fa-concierge-bell"></i></div>
                        <div class="pillar-content">
                            <div class="pillar-badge"><i class="fas fa-star"></i> Dịch vụ 5 sao</div>
                            <h3>Tiện Lợi Tối Ưu</h3>
                            <p>Từ việc đặt vé đến trải nghiệm trên xe, tất cả đều được tối ưu hóa để mang lại sự tiện lợi và thoải mái tối đa cho bạn.</p>
                            <ul class="pillar-features">
                                <li>Đặt vé online dễ dàng qua website & app</li>
                                <li>Ghế ngồi cao cấp, điều hòa 2 chiều</li>
                                <li>WiFi miễn phí tốc độ cao</li>
                                <li>Hệ thống giải trí hiện đại</li>
                                <li>Dịch vụ đón trả tận nơi</li>
                            </ul>
                        </div>
                    </div>

                    <div class="pillar fade-up">
                        <div class="pillar-visual"><i class="fas fa-stopwatch"></i></div>
                        <div class="pillar-content">
                            <div class="pillar-badge"><i class="fas fa-clock"></i> Cam kết 99%</div>
                            <h3>Đúng Giờ Cam Kết</h3>
                            <p>Chúng tôi hiểu rõ giá trị thời gian của bạn và cam kết đúng giờ với độ chính xác cao nhất nhờ hệ thống điều hành thông minh.</p>
                            <ul class="pillar-features">
                                <li>Hệ thống định vị GPS thời gian thực</li>
                                <li>Lịch trình được tối ưu hóa bằng AI</li>
                                <li>Đội ngũ điều hành chuyên nghiệp 24/7</li>
                                <li>Cam kết đền bù nếu chậm trễ</li>
                                <li>Thông báo tình trạng chuyến xe qua App</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </section>

            <section class="stats-section section">
                <div class="container">
                    <div class="stats-container">
                        <div class="stat-item fade-up">
                            <span class="stat-number" data-target="6" data-suffix="+">0</span>
                            <div class="stat-label">Năm Kinh Nghiệm</div>
                        </div>
                        <div class="stat-item fade-up">
                            <span class="stat-number" data-target="195" data-suffix="K+">0</span>
                            <div class="stat-label">Hành Khách Tin Tưởng</div>
                        </div>
                        <div class="stat-item fade-up">
                            <span class="stat-number" data-target="99.2" data-suffix="%">0</span>
                            <div class="stat-label">Tỷ Lệ Đúng Giờ</div>
                        </div>
                        <div class="stat-item fade-up">
                            <span class="stat-number" data-target="15" data-suffix="+">0</span>
                            <div class="stat-label">Tuyến Đường Phủ Sóng</div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="cta-section section">
                <div class="cta-container fade-up">
                    <h2>Bắt Đầu Hành Trình Của Bạn</h2>
                    <p>Đặt vé ngay hôm nay để trải nghiệm dịch vụ vận chuyển đẳng cấp cùng Tùng Long Bus Lines.</p>
                    <div class="cta-buttons">
                        <a href="${pageContext.request.contextPath}/ScheduleController" class="btn btn-primary"><i class="fas fa-search"></i> Tìm Chuyến Xe</a>
                        <a href="searchTicket.jsp" class="btn btn-outline dark"><i class="fas fa-ticket-alt"></i> Tra Cứu Vé</a>
                    </div>
                </div>
            </section>
        </main>

        <%@include file="footer.jsp" %>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // --- HIỆU ỨNG FADE-UP KHI CUỘN TRANG ---
                const fadeUpObserver = new IntersectionObserver((entries) => {
                    entries.forEach(entry => {
                        if (entry.isIntersecting) {
                            entry.target.classList.add('visible');
                        }
                    });
                }, {threshold: 0.1});

                document.querySelectorAll('.fade-up').forEach(el => fadeUpObserver.observe(el));

                // --- HIỆU ỨNG ĐẾM SỐ ---
                const animateCounter = (el) => {
                    const target = +el.getAttribute('data-target');
                    const suffix = el.getAttribute('data-suffix') || '';
                    const duration = 1500;
                    const frameRate = 1000 / 60;
                    const totalFrames = Math.round(duration / frameRate);
                    let frame = 0;

                    const counter = setInterval(() => {
                        frame++;
                        const progress = frame / totalFrames;
                        const current = target * progress;

                        if (target % 1 !== 0) {
                            el.innerText = current.toFixed(1);
                        } else {
                            el.innerText = Math.round(current).toLocaleString();
                        }

                        if (frame === totalFrames) {
                            clearInterval(counter);
                            el.innerText = target.toLocaleString() + suffix;
                        }
                    }, frameRate);
                };

                const statsObserver = new IntersectionObserver((entries, observer) => {
                    entries.forEach(entry => {
                        if (entry.isIntersecting) {
                            animateCounter(entry.target);
                            observer.unobserve(entry.target);
                        }
                    });
                }, {threshold: 0.8});

                document.querySelectorAll('.stat-number').forEach(counter => statsObserver.observe(counter));
            });
        </script>
    </body>
</html>