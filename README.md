# 💻 LaptopShop – Web Bán Laptop

> Ứng dụng web thương mại điện tử bán laptop xây dựng bằng **Spring Boot 3.4.1**, theo kiến trúc **MVC**, tích hợp đầy đủ bảo mật, quản lý đơn hàng, giỏ hàng và phân quyền người dùng.

---

## 🧾 Mô tả dự án

**LaptopShop** là một ứng dụng web thương mại điện tử được xây dựng hoàn toàn bằng Java với framework Spring Boot. Dự án hướng đến việc mô phỏng một hệ thống bán hàng trực tuyến thực tế, bao gồm hai vai trò chính: **Người dùng (User)** và **Quản trị viên (Admin)**.

Người dùng có thể duyệt sản phẩm, tìm kiếm laptop, thêm vào giỏ hàng và đặt hàng. Quản trị viên có thể quản lý toàn bộ sản phẩm, đơn hàng, người dùng thông qua giao diện admin riêng biệt.

---

## 🚀 Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| **Ngôn ngữ** | Java 21 |
| **Framework** | Spring Boot 3.4.1 |
| **Kiến trúc** | MVC (Model – View – Controller) |
| **View Engine** | JSP + JSTL |
| **ORM / Database** | Spring Data JPA + Hibernate |
| **Cơ sở dữ liệu** | MySQL (Connector 9.1.0) |
| **Bảo mật** | Spring Security + BCrypt |
| **Quản lý Session** | Spring Session JDBC |
| **Validation** | Spring Boot Starter Validation |
| **Build Tool** | Maven |
| **Frontend** | HTML, CSS, JavaScript (vanilla) |
| **Dev Tool** | Spring Boot DevTools |

---

## ✨ Chức năng hệ thống

### 👤 Phía Người Dùng (User)

#### 🔐 Xác thực & Tài khoản
- Đăng ký tài khoản mới
- Đăng nhập / Đăng xuất
- Mã hóa mật khẩu bằng BCrypt đảm bảo an toàn
- Quản lý thông tin cá nhân (cập nhật hồ sơ)
- Phiên đăng nhập được lưu trữ bền vững qua Spring Session JDBC

#### 🛍️ Duyệt & Tìm kiếm Sản phẩm
- Xem danh sách sản phẩm (laptop) với đầy đủ thông tin
- Lọc sản phẩm theo danh mục, thương hiệu, khoảng giá
- Tìm kiếm sản phẩm theo tên / từ khóa
- Xem chi tiết sản phẩm (thông số kỹ thuật, hình ảnh, giá)
- Phân trang danh sách sản phẩm

#### 🛒 Giỏ Hàng
- Thêm sản phẩm vào giỏ hàng
- Xem giỏ hàng, xem tổng tiền
- Cập nhật số lượng sản phẩm trong giỏ
- Xóa sản phẩm khỏi giỏ hàng

#### 📦 Đặt Hàng
- Tiến hành thanh toán từ giỏ hàng
- Nhập thông tin địa chỉ giao hàng
- Xác nhận đơn hàng
- Xem lịch sử đơn hàng của bản thân
- Xem chi tiết từng đơn hàng

---

### 🛠️ Phía Quản Trị Viên (Admin)

#### 📊 Dashboard
- Trang tổng quan quản trị hệ thống

#### 📦 Quản lý Sản phẩm
- Thêm mới sản phẩm (tên, giá, mô tả, hình ảnh, số lượng tồn kho)
- Sửa thông tin sản phẩm
- Xóa sản phẩm
- Upload hình ảnh sản phẩm
- Quản lý tồn kho

#### 🗂️ Quản lý Danh mục
- Thêm / sửa / xóa danh mục sản phẩm
- Phân loại sản phẩm theo danh mục

#### 👥 Quản lý Người dùng
- Xem danh sách tất cả người dùng
- Phân quyền vai trò (USER / ADMIN)
- Kích hoạt / Vô hiệu hóa tài khoản

#### 🧾 Quản lý Đơn hàng
- Xem tất cả đơn hàng trong hệ thống
- Xem chi tiết đơn hàng
- Cập nhật trạng thái đơn hàng (Chờ xác nhận → Đang xử lý → Đang giao → Hoàn thành / Hủy)

---

## 🔐 Phân quyền & Bảo mật

Hệ thống sử dụng **Spring Security** để bảo vệ các endpoint:

| Đường dẫn | Quyền truy cập |
|---|---|
| `/` , `/products/**`, `/login`, `/register` | Công khai |
| `/cart/**`, `/order/**`, `/profile/**` | Yêu cầu đăng nhập (USER) |
| `/admin/**` | Chỉ ADMIN |

- Mật khẩu được băm bằng **BCrypt** trước khi lưu vào database
- Session người dùng được lưu vào MySQL qua **Spring Session JDBC** (hỗ trợ cluster, không mất session khi restart server)
- Validate dữ liệu đầu vào ở cả client lẫn server (Spring Validation)

---

## 🗄️ Cấu trúc cơ sở dữ liệu (MySQL)

Các bảng chính trong hệ thống:

```
users           – Thông tin tài khoản người dùng
roles           – Vai trò (ADMIN, USER)
products        – Thông tin sản phẩm laptop
categories      – Danh mục sản phẩm
cart            – Giỏ hàng
cart_items      – Chi tiết sản phẩm trong giỏ
orders          – Đơn hàng
order_details   – Chi tiết đơn hàng
spring_session  – Lưu trữ session (Spring Session JDBC)
```

---

## 🗂️ Cấu trúc thư mục dự án

```
src/
├── main/
│   ├── java/com/springboot/hellospringboot/
│   │   ├── config/          # Cấu hình Spring Security
│   │   ├── controller/      # Xử lý request (MVC Controllers)
│   │   │   ├── admin/       # Controller cho trang Admin
│   │   │   └── user/        # Controller cho trang User
│   │   ├── entity/          # Entity (JPA – ánh xạ bảng DB)
│   │   ├── repository/      # Spring Data JPA Repositories
│   │   ├── service/         # Business Logic (Service Layer)
│   │   └── HelloSpringBootApplication.java
│   ├── resources/
│   │   └── application.properties  # Cấu hình DB, server, session
│   └── webapp/
│       └── WEB-INF/views/   # Các file JSP (giao diện)
│           ├── admin/       # Giao diện trang Admin
│           └── user/        # Giao diện trang User
pom.xml                      # Khai báo dependencies Maven
```

---

## ⚙️ Hướng dẫn cài đặt & chạy dự án

### Yêu cầu hệ thống
- Java 21+
- Maven 3.8+
- MySQL 8.0+
- IDE: IntelliJ IDEA / Eclipse / VS Code

### Các bước cài đặt

**1. Clone dự án**
```bash
git clone https://github.com/doleviettai/Web-spring-boot-project-web-b-n-laptopShop.git
cd Web-spring-boot-project-web-b-n-laptopShop
```

**2. Tạo database MySQL**
```sql
CREATE DATABASE laptopshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**3. Cấu hình kết nối database**

Mở file `src/main/resources/application.yaml` và chỉnh sửa:
```yaml
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto : update
  datasource:
    url : "jdbc:mysql://localhost:3306/laptopshop"
    username : your_username
    password : your_password
```

**4. Chạy ứng dụng**
```bash
./mvnw spring-boot:run
```
Hoặc chạy trực tiếp file `HelloSpringBootApplication.java` trong IDE.

**5. Truy cập ứng dụng**
```
Trang người dùng : http://localhost:8080
Trang admin      : http://localhost:8080/admin
```

---

