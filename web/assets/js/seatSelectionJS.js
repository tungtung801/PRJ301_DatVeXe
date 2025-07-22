document.addEventListener('DOMContentLoaded', function () {
    // Lấy các phần tử DOM cần thiết
    const seatElements = document.querySelectorAll('.seat.available');
    const selectedSeatsInput = document.getElementById('selectedSeatsInput');
    const selectedSeatsInfoDiv = document.getElementById('selectedSeatsInfo');
    const totalPriceDiv = document.getElementById('totalPrice');
    const bookingButton = document.getElementById('bookingBtn');
    const pricePerSeat = parseFloat(document.getElementById('hiddenPricePerSeat').value);

    // Mảng để lưu trữ ID của các ghế đang được chọn
    let selectedSeatIds = [];
    // Mảng để lưu trữ số hiệu của các ghế đang được chọn (ví dụ: A01, B05)
    let selectedSeatNumbers = [];

    // Hàm cập nhật thông tin tổng hợp (số ghế, giá tiền)
    function updateBookingSummary() {
        // Cập nhật input hidden để gửi lên server
        selectedSeatsInput.value = selectedSeatIds.join(',');

        // Hiển thị thông tin ghế đã chọn
        if (selectedSeatNumbers.length > 0) {
            selectedSeatsInfoDiv.innerHTML = `<strong>Ghế đã chọn: </strong> ${selectedSeatNumbers.join(', ')}`;
        } else {
            selectedSeatsInfoDiv.innerHTML = `<strong>Ghế đã chọn: </strong> Chưa chọn ghế nào`;
        }

        // Tính toán và hiển thị tổng giá tiền
        const totalPrice = selectedSeatIds.length * pricePerSeat;
        // Sử dụng Intl.NumberFormat để định dạng tiền tệ Việt Nam
        const formatter = new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0
        });
        totalPriceDiv.innerHTML = `<strong>Tổng tiền: </strong> <span style="color: red; font-weight: bold;">${formatter.format(totalPrice)}</span>`;

        // Hiển thị hoặc ẩn nút "Tiếp tục đặt vé"
        if (selectedSeatIds.length > 0) {
            bookingButton.style.display = 'block';
        } else {
            bookingButton.style.display = 'none';
        }
    }

    // Gắn sự kiện click cho từng ghế trống
    seatElements.forEach(seat => {
        seat.addEventListener('click', function () {
            // Lấy ID và số hiệu ghế
            const seatId = this.dataset.seatId;
            const seatNumber = this.dataset.seatNumber;
            const checkbox = this.querySelector('input[type="checkbox"]');

            // Kiểm tra trạng thái ghế
            if (this.classList.contains('available')) {
                // Nếu ghế đang trống và chưa được chọn, thì chọn ghế
                if (!this.classList.contains('selected')) {
                    this.classList.add('selected');
                    checkbox.checked = true; // Đánh dấu checkbox đã chọn
                    selectedSeatIds.push(seatId);
                    selectedSeatNumbers.push(seatNumber);
                } else {
                    // Nếu ghế đã được chọn, thì bỏ chọn ghế
                    this.classList.remove('selected');
                    checkbox.checked = false; // Bỏ đánh dấu checkbox
                    selectedSeatIds = selectedSeatIds.filter(id => id !== seatId);
                    selectedSeatNumbers = selectedSeatNumbers.filter(num => num !== seatNumber);
                }
                updateBookingSummary(); // Cập nhật lại thông tin tóm tắt
            }
            // Ghế 'booked' thì không làm gì cả vì có cursor: not-allowed rồi
        });
    });

    // Gọi hàm cập nhật lần đầu khi trang tải để hiển thị trạng thái ban đầu
    updateBookingSummary();
});