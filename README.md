# 🚀 Spring Boot Mikroservis E-ticaret Projesi

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://img.shields.io/badge/Java-17-orange.svg)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg)](https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://img.shields.io/badge/Docker-Enabled-blue.svg)
[![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple.svg)](https://img.shields.io/badge/Architecture-Microservices-purple.svg)

Bu depo, Java Spring Boot ekosistemini kullanarak Mikroservis Mimarisi üzerine yapılmış kapsamlı bir e-ticaret uygulamasıdır. Proje, modern mikroservis desenlerini ve Docker konteynerizasyonunu kullanarak ölçeklenebilir ve bakımı kolay bir sistem sunmaktadır.

## 📋 İçindekiler

- [Teknik Mimari](#-teknik-mimari)
- [Servisler](#-servisler)
- [Docker ile Kurulum](#-docker-ile-kurulum)
- [API Kullanımı](#-api-kullanımı)
- [Özellikler](#-özellikler)
- [Geliştirme](#-geliştirme)

## 🏗️ Teknik Mimari

Proje, Domain-Driven Design (DDD) yaklaşımıyla aşağıdaki teknoloji yığını kullanılarak inşa edilmiştir:

### Temel Teknolojiler
- **Çekirdek Çerçeve:** Java 17, Spring Boot 3.2.2
- **Hizmet Keşfi:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway (Yönlendirme & Özel Filtreler)
- **Güvenlik:** Spring Security 6 & JWT (JSON Web Tokens)
- **Konteynerizasyon:** Docker & Docker Compose

### Veritabanları
- **MySQL:** Identity, Order ve Inventory servisleri için
- **MongoDB:** Product servisi için NoSQL doküman deposu

### Mesajlaşma & Dayanıklılık
- **RabbitMQ:** Asenkron olay güdümlü mesajlaşma
- **Resilience4j:** Circuit Breaker pattern implementasyonu

### Yapılandırma
- **Spring-dotenv:** Güvenli ortam değişkeni yönetimi

## 🧩 Servisler

| Servis | Port | Veritabanı | Sorumluluklar |
|--------|------|-----------|--------------|
| **API Gateway** | 8085 | - | Tek giriş noktası, JWT doğrulama, Yönlendirme |
| **Discovery Server** | 8761 | - | Servis Kayıt Merkezi (Eureka) |
| **Identity Service** | 8086 | MySQL | Kullanıcı kaydı, Giriş, Token yönetimi |
| **Product Service** | 8080 | MongoDB | Ürün kataloğu yönetimi (CRUD) |
| **Inventory Service** | 8082 | MySQL | Gerçek zamanlı stok kontrolü |
| **Order Service** | 8081 | MySQL | Sipariş işleme, İş mantığı, Event yayınlama |
| **Notification Service** | 8084 | - | RabbitMQ event'lerini tüketme |

## 🐳 Docker ile Kurulum

### Ön Gereksinimler

Sisteminizde aşağıdaki araçların kurulu olması gerekmektedir:

- **Docker Desktop** (v20.10+)
- **Docker Compose** (v2.0+)
- **Git**
- **JDK 17** (opsiyonel - sadece yerel geliştirme için)
- **Maven** (opsiyonel - sadece yerel geliştirme için)

### 1️⃣ Projeyi Klonlama

```bash
git clone https://github.com/BurakKarahan8/Microservices.git
cd Microservices
```

### 2️⃣ Ortam Değişkenlerini Yapılandırma

🔐 Güvenlik nedeniyle hassas veriler kod içinde yer almamaktadır. Projenin kök dizininde bir `.env` dosyası oluşturun:

```bash
# Linux/Mac
touch .env

# Windows (PowerShell)
New-Item .env
```

`.env` dosyasının içeriğini aşağıdaki gibi düzenleyin:

```env
# MySQL Veritabanı Ayarları
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_USER=root
MYSQL_PASSWORD=rootpassword

# RabbitMQ Mesaj Broker Ayarları
RABBITMQ_USER=guest
RABBITMQ_PASSWORD=guest

# JWT Güvenlik Anahtarı
JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
```

> ⚠️ **Güvenlik Notu:** Production ortamında bu değerleri mutlaka değiştirin ve güçlü parolalar kullanın!

### 3️⃣ Docker Container'ları Başlatma

Projeyi Docker ile çalıştırmak için iki seçeneğiniz var:

#### Seçenek A: Önceden Build Edilmiş İmajlar (Önerilen)

```bash
# Tüm servisleri arka planda başlat
docker-compose up -d

# Logları takip et (opsiyonel)
docker-compose logs -f
```

#### Seçenek B: Projeyi Build Edip Çalıştırma

```bash
# Maven ile projeyi paketleyin
mvn clean package -DskipTests

# Docker imajlarını oluştur ve container'ları başlat
docker-compose up --build -d
```

### 4️⃣ Servislerin Durumunu Kontrol Etme

```bash
# Çalışan container'ları listele
docker-compose ps

# Tüm servislerin loglarını görüntüle
docker-compose logs

# Belirli bir servisin loglarını görüntüle
docker-compose logs identity-service
```

### 5️⃣ Servislerin Hazır Olmasını Bekleyin

İlk başlatmada servislerin ayağa kalkması 2-3 dakika sürebilir. Eureka Dashboard'dan servislerin durumunu kontrol edebilirsiniz:

```
http://localhost:8761
```

Tüm servisler yeşil renkte gözüküyorsa sistem kullanıma hazırdır! ✅

## 🌐 Servis URL'leri

Sistem çalıştıktan sonra aşağıdaki adreslere erişebilirsiniz:

| Servis | URL | Açıklama |
|--------|-----|----------|
| Eureka Dashboard | http://localhost:8761 | Servis kayıt merkezi |
| API Gateway | http://localhost:8085 | Ana API endpoint |
| RabbitMQ Management | http://localhost:15672 | Mesaj kuyruğu yönetimi (guest/guest) |
| Product Service | http://localhost:8080 | Doğrudan erişim (önerilmez) |
| Order Service | http://localhost:8081 | Doğrudan erişim (önerilmez) |
| Inventory Service | http://localhost:8082 | Doğrudan erişim (önerilmez) |
| Identity Service | http://localhost:8086 | Doğrudan erişim (önerilmez) |
| Zipkin | http://localhost:9411/ | Dağıtılmış İzleme Kontrol Paneli |

> 💡 **Not:** Tüm isteklerinizi API Gateway (port 8085) üzerinden yapmanız önerilir.

## 🔌 API Kullanımı

### 🔐 Kimlik Doğrulama (Authentication)

#### 1. Kullanıcı Kaydı

**Endpoint:** `POST http://localhost:8085/auth/register`

**Request Body:**
```json
{
  "name": "Ahmet Yılmaz",
  "email": "ahmet@example.com",
  "password": "Guvenli123!"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "userId": "123e4567-e89b-12d3-a456-426614174000"
}
```

#### 2. Giriş Yapma ve Token Alma

**Endpoint:** `POST http://localhost:8085/auth/token`

**Request Body:**
```json
{
  "username": "ahmet@example.com",
  "password": "Guvenli123!"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600
}
```

> 💡 Bu token'ı sonraki isteklerinizde `Authorization: Bearer <TOKEN>` header'ında kullanacaksınız.

### 📦 Ürün İşlemleri

#### Ürün Listesini Görüntüleme

**Endpoint:** `GET http://localhost:8085/api/product`

**Headers:**
```
Authorization: Bearer <TOKEN>
```

**Response:**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "name": "iPhone 15 Pro",
    "description": "Apple'ın en yeni flagship telefonu",
    "price": 45000
  }
]
```

#### Yeni Ürün Ekleme

**Endpoint:** `POST http://localhost:8085/api/product`

**Headers:**
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "MacBook Pro M3",
  "description": "Apple M3 çipli profesyonel dizüstü bilgisayar",
  "price": 85000
}
```

### 🛒 Sipariş İşlemleri

#### Sipariş Verme

**Endpoint:** `POST http://localhost:8085/api/order`

**Headers:**
```
Authorization: Bearer <TOKEN>
Content-Type: application/json
```

**Request Body:**
```json
{
  "orderLineItemsDtoList": [
    {
      "skuCode": "iphone_15_pro",
      "price": 45000,
      "quantity": 2
    },
    {
      "skuCode": "airpods_pro",
      "price": 8000,
      "quantity": 1
    }
  ]
}
```

**Response (Başarılı):**
```json
{
  "message": "Order placed successfully",
  "orderId": "ORD-2024-001234"
}
```

**Response (Stok Yetersiz):**
```json
{
  "error": "Product is not in stock",
  "skuCode": "iphone_15_pro"
}
```

### 📊 İş Akışı

Sipariş verme işlemi şu adımlardan oluşur:

1. **API Gateway** → Token'ı doğrular ve isteği yönlendirir
2. **Order Service** → Siparişi alır
3. **Inventory Service** → Stok kontrolü yapılır (Senkron REST çağrısı)
4. **MySQL** → Sipariş veritabanına kaydedilir (stok varsa)
5. **RabbitMQ** → Sipariş event'i kuyruğa gönderilir (Asenkron)
6. **Notification Service** → Event'i tüketir ve bildirim gönderir

```
[Client] → [API Gateway] → [Order Service] ⇄ [Inventory Service]
                                    ↓
                            [MySQL Database]
                                    ↓
                              [RabbitMQ]
                                    ↓
                         [Notification Service]
```

## ✨ Özellikler

### 🔒 Güvenlik

- **JWT Token Tabanlı Kimlik Doğrulama:** Stateless authentication
- **API Gateway Seviyesinde Doğrulama:** Merkezi güvenlik kontrolü
- **BCrypt Şifreleme:** Parolaların güvenli saklanması
- **Role-Based Access Control (RBAC):** Rol bazlı yetkilendirme

### 🔄 Circuit Breaker Pattern

Sistem, **Resilience4j** kütüphanesi kullanarak hata toleransı sağlar:

```java
@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
public boolean checkStock(String skuCode) {
    // Inventory Service'e çağrı
}

public boolean fallbackMethod(String skuCode, Exception e) {
    // Hata durumunda alternatif yanıt
    return false;
}
```

**Avantajlar:**
- Inventory Service çökse bile sistem çalışmaya devam eder
- Cascade failure (zincirleme hata) önlenir
- Sistem dayanıklılığı artar

### 📨 Event-Driven Architecture

Asenkron iletişim için **RabbitMQ** kullanılır:

**Producer (Order Service):**
```java
rabbitTemplate.convertAndSend(
    "order-exchange",
    "order.created",
    orderEvent
);
```

**Consumer (Notification Service):**
```java
@RabbitListener(queues = "order-notification-queue")
public void handleOrderEvent(OrderEvent event) {
    // E-posta gönder, SMS at, vb.
}
```

### 🔍 Service Discovery

Netflix Eureka ile dinamik servis keşfi:

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://discovery-server:8761/eureka/
```

**Avantajlar:**
- Servislerin IP adresleri hardcoded olarak yazılmaz
- Yeni servis instance'ları otomatik keşfedilir
- Load balancing için hazır altyapı

## 🛠️ Geliştirme

### Docker Container'ları Durdurma

```bash
# Tüm container'ları durdur
docker-compose down

# Container'ları durdurup volume'leri de sil (veritabanı verileri silinir!)
docker-compose down -v

# Container'ları durdurup imajları da sil
docker-compose down --rmi all
```

### Tek Bir Servisi Yeniden Başlatma

```bash
# Servisi durdur
docker-compose stop order-service

# Servisi başlat
docker-compose start order-service

# Servisi yeniden başlat (stop + start)
docker-compose restart order-service
```

### Logları İnceleme

```bash
# Tüm servislerin canlı logları
docker-compose logs -f

# Belirli bir servisin logları
docker-compose logs -f order-service

# Son 100 satır log
docker-compose logs --tail=100 order-service
```

### Container İçine Bağlanma

```bash
# MySQL container'ına bağlan
docker exec -it mysql-db mysql -uroot -p

# MongoDB container'ına bağlan
docker exec -it mongo-db mongosh

# Herhangi bir container'ın shell'ine bağlan
docker exec -it order-service sh
```

### IDE ile Geliştirme (Hybrid Mod)

Bazı servisleri IDE'de çalıştırıp diğerlerini Docker'da çalıştırmak için:

```bash
# Sadece altyapı servislerini başlat (Veritabanları, RabbitMQ, Eureka)
docker-compose up -d mysql-db mongo-db rabbitmq discovery-server

# Diğer servisleri IDE'den başlatın
# application.properties'de localhost:8761 olarak Eureka adresini güncelleyin
```

### Veritabanı Bağlantısı

**MySQL:**
```bash
Host: localhost
Port: 3306
Username: root
Password: rootpassword
```

**MongoDB:**
```bash
Host: localhost
Port: 27017
Connection String: mongodb://localhost:27017/product-service
```

## 🧪 Test Etme

### Postman Collection

Projeyi test etmek için hazır Postman collection oluşturabilirsiniz:

1. Postman'i açın
2. Import → Raw Text
3. Aşağıdaki curl komutlarını kullanın

**Örnek Test Senaryosu:**

```bash
# 1. Kullanıcı kaydı
curl -X POST http://localhost:8085/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@test.com","password":"test123"}'

# 2. Token al
TOKEN=$(curl -X POST http://localhost:8085/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"test@test.com","password":"test123"}' \
  | jq -r '.token')

# 3. Ürün listesini getir
curl -X GET http://localhost:8085/api/product \
  -H "Authorization: Bearer $TOKEN"

# 4. Sipariş ver
curl -X POST http://localhost:8085/api/order \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "orderLineItemsDtoList": [
      {
        "skuCode": "iphone_15",
        "price": 45000,
        "quantity": 1
      }
    ]
  }'
```

### Health Check

Servislerin sağlık durumunu kontrol edin:

```bash
# Discovery Server
curl http://localhost:8761/actuator/health

# API Gateway
curl http://localhost:8085/actuator/health
```

## 📚 Öğrenme Kaynakları

Bu proje aşağıdaki konuları öğrenmek için harika bir kaynak:

- ✅ Mikroservis Mimarisi
- ✅ Spring Boot & Spring Cloud
- ✅ Docker & Docker Compose
- ✅ JWT Authentication
- ✅ Circuit Breaker Pattern
- ✅ Event-Driven Architecture
- ✅ Service Discovery
- ✅ API Gateway Pattern
- ✅ NoSQL & SQL Veritabanları
- ✅ Message Queues (RabbitMQ)
- ✅ Zipkin

## 🐛 Sorun Giderme

### Servisler Ayağa Kalkmıyor

```bash
# Container loglarını kontrol edin
docker-compose logs

# Belirli bir servisin logunu detaylı inceleyin
docker-compose logs -f order-service
```

### Port Çakışması

Eğer portlar kullanımdaysa, `docker-compose.yml` dosyasındaki port mapping'leri değiştirin:

```yaml
ports:
  - "8085:8085"  # 8085 yerine 9085 gibi farklı bir port kullanın
```

### Veritabanı Bağlantı Hatası

```bash
# MySQL container'ının çalıştığından emin olun
docker-compose ps mysql-db

# MySQL loglarını kontrol edin
docker-compose logs mysql-db

# Container'ı yeniden başlatın
docker-compose restart mysql-db
```

### RabbitMQ'ya Bağlanamıyor

```bash
# RabbitMQ yönetim paneline erişim
http://localhost:15672
Kullanıcı adı: guest
Şifre: guest
```

### Eureka'da Servis Görünmüyor

- Servisin başlamasını 30 saniye bekleyin (Eureka heartbeat süresi)
- `application.properties` dosyasında `eureka.client.service-url.defaultZone` ayarını kontrol edin

## 🤝 Katkıda Bulunma

1. Projeyi fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir ve MIT lisansı altında sunulmaktadır.

## 👨‍💻 Yazar

**Burak Karahan** - Yazılım Mühendisi

---

## 📞 İletişim & Destek

Sorularınız veya önerileriniz için:

- GitHub Issues: [Sorun Bildir](https://github.com/BurakKarahan8/Microservices/issues)
- E-posta: [Proje sahibine ulaşın]

---

⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!

**Son Güncelleme:** Ocak 2025