# FreezePlugin ❄️

Plugin Minecraft đơn giản: đóng băng / gỡ đóng băng người chơi qua lệnh.

## Tính năng
- Lệnh `/freeze <tên_người_chơi>` — đóng băng, gõ lại lần nữa để gỡ.
- Người bị đóng băng không thể di chuyển (vẫn xoay đầu nhìn xung quanh được).
- Quyền `freeze.use` (mặc định: OP).

## Cách build (tạo file .jar)
Máy cần cài **Java 17+** và **Maven**, rồi chạy trong thư mục project:

```bash
mvn package
```

File jar sẽ nằm ở `target/FreezePlugin-1.0.0.jar`.
Copy file đó vào thư mục `plugins/` của server rồi restart server.

## Yêu cầu server
- Spigot / Paper **1.20+** (Java 17).

## Cấu trúc
```
freeze-plugin/
├── pom.xml
└── src/main/
    ├── java/com/soiichan/freeze/FreezePlugin.java
    └── resources/plugin.yml
```
