package com.example.book.DTO;

import io.micrometer.common.lang.NonNull;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Các trường hợp sử dụng DTO:
Tránh lộ dữ liệu không cần thiết:

Ví dụ, BookEntity có trường price hoặc stockQuantity không cần thiết gửi cho client. Chỉ gửi những trường cần thiết trong BookDTO.
Định dạng lại dữ liệu:

Chuyển đổi tên trường hoặc giá trị để phù hợp với yêu cầu của client:
isReady trong Entity → available trong DTO.
Format ngày/thời gian từ 2023-12-01T12:00:00Z → 01-12-2023.
Tối ưu hóa dữ liệu truyền tải:

Loại bỏ các trường không cần thiết từ Entity để giảm dung lượng dữ liệu gửi qua mạng.
Đáp ứng yêu cầu đặc thù của giao tiếp API:

Khi client yêu cầu thêm thông tin chỉ liên quan đến giao tiếp API (e.g., các thông điệp trạng thái, liên kết HATEOAS).
Hỗ trợ các hệ thống khác nhau:

Một Entity có thể có nhiều DTO để phục vụ các client khác nhau:
Web client cần BookWebDTO.
Mobile client cần BookMobileDTO.
 */
@Data
public class BookDTO {

    private String id;

    @NonNull
    private String name;

    @NonNull
    private String author;

    @NonNull
    private Boolean isReady;
}
