# 🚀 Spring Boot Microservices E-commerce Practice

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple.svg)

This repository represents a comprehensive practice application built on Microservices Architecture using the Java Spring Boot ecosystem. The main goal of the project is to implement essential microservices patterns such as Service Discovery, API Gateway, Centralized Authentication, Distributed Tracing, and Event-Driven Communication.

## 🏗️ Technical Architecture

The project is built using the following technology stack with a Domain-Driven Design (DDD) approach:

- **Core Framework:** Java 17, Spring Boot 3.2.2
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway (Routing & Custom Filters)
- **Security:** Spring Security 6 & JWT (JSON Web Tokens)
- **Databases:**
  - **MySQL:** Relational data for Identity, Order, and Inventory services
  - **MongoDB:** NoSQL document store for Product service
- **Asynchronous Messaging:** RabbitMQ (Event-driven notification system)
- **Resilience & Fault Tolerance:** Resilience4j (Circuit Breaker implementation)
- **Containerization:** Docker & Docker Compose
- **Configuration:** Spring-dotenv (Secure environment variable management)

## 🧩 Services Overview

The system consists of the following independently operating services:

| Service Name | Port | Database | Responsibilities |
|------------|------|------------|---------------|
| Api Gateway | 8085 | - | Single entry point, JWT validation, Routing |
| Discovery Server | 8761 | - | Service Registry (Eureka Server) |
| Identity Service | 8086 | MySQL | User Registration, Login, Token Generation |
| Product Service | 8080 | MongoDB | Product Catalog Management (CRUD) |
| Inventory Service | 8082 | MySQL | Real-time stock control |
| Order Service | 8081 | MySQL | Order processing, Business logic, Event publishing |
| Notification Service | 8084 | - | Consuming RabbitMQ events (Email simulation) |

## 🚀 How to Run?

### Prerequisites

- JDK 17+
- Docker Desktop & Docker Compose
- Maven

### 1. Clone the Repository

```bash
git clone https://github.com/BurakKarahan8/DigitalCarsi.git
cd DigitalCarsi
```

### 2. Configure Environment Variables

🔐 To ensure security, sensitive data is not shared within the code. Create a `.env` file in the project root directory and paste the following settings:

```properties
# Database Configuration
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_USER=root
MYSQL_PASSWORD=rootpassword

# Message Broker
RABBITMQ_USER=guest
RABBITMQ_PASSWORD=guest

# Security (JWT)
JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
```

> **Note:** If you plan to run services individually through an IDE, ensure that the `.env` file is in the relevant service directory or defined in Run/Debug configurations.

### 3. Build and Run with Docker

🐳 You can spin up the entire infrastructure (Databases, Broker, Services) with a single command:

```bash
# Package the application
mvn clean package -DskipTests

# Start the containers
docker-compose up -d
```

## 🔌 API Usage Examples

All requests are routed through the API Gateway (Port 8085).

### 🔐 1. Authentication

**Step 1:** Create a new user registration.

```http
POST /auth/register
```

```json
{
  "name": "developer",
  "email": "dev@example.com",
  "password": "password123"
}
```

**Step 2:** Login to obtain a JWT Token.

```http
POST /auth/token
```

```json
{
  "username": "developer",
  "password": "password123"
}
```

A **Bearer Token** will be returned in the response. Copy this token for the next steps.

### 📦 2. Order System (Secured)

**Place an Order**

**Required Header:** `Authorization: Bearer <TOKEN>`

```http
POST /api/order
```

```json
{
  "orderLineItemsDtoList": [
    {
      "skuCode": "iphone_15",
      "price": 1200,
      "quantity": 1
    }
  ]
}
```

**Flow Diagram:**

1. API Gateway validates the token
2. Order Service receives the request
3. Synchronous call to Inventory Service for stock verification
4. If in stock → Order is saved to MySQL
5. An asynchronous event is sent to RabbitMQ
6. Notification Service consumes this event

## 🛡️ Circuit Breaker Pattern

The system uses **Resilience4j** to handle failures gracefully. If the Inventory Service crashes or slows down, the Order Service returns a predefined "fallback" response instead of blocking the entire system. This ensures system stability.

## 👨‍💻 Author

**Burak Karahan** - Software Engineer


***
---
***


# 🚀 Spring Boot Mikroservis E-ticaret Alıştırması

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple.svg)

Bu depo, Java Spring Boot ekosistemini kullanarak Mikroservis Mimarisi üzerine yapılmış kapsamlı bir uygulama çalışmasını temsil eder. Projenin temel amacı; Hizmet Keşfi (Service Discovery), API Gateway, Merkezi Kimlik Doğrulama, Dağıtık İzleme ve Olay Güdümlü İletişim (Event-Driven Communication) gibi temel mikroservis desenlerini uygulamaktır.

## 🏗️ Teknik Mimari

Proje, Domain-Driven Design (DDD) yaklaşımıyla aşağıdaki teknoloji yığını kullanılarak inşa edilmiştir:

- **Çekirdek Çerçeve:** Java 17, Spring Boot 3.2.2
- **Hizmet Keşfi:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway (Yönlendirme & Özel Filtreler)
- **Güvenlik:** Spring Security 6 & JWT (JSON Web Tokens)
- **Veritabanları:**
  - **MySQL:** Kimlik (Identity), Sipariş (Order) ve Envanter (Inventory) servisleri için ilişkisel veri
  - **MongoDB:** Ürün (Product) servisi için NoSQL doküman deposu
- **Asenkron Mesajlaşma:** RabbitMQ (Olay güdümlü bildirim sistemi)
- **Dayanıklılık & Hata Toleransı:** Resilience4j (Circuit Breaker uygulaması)
- **Konteynerleştirme:** Docker & Docker Compose
- **Yapılandırma:** Spring-dotenv (Güvenli ortam değişkeni yönetimi)

## 🧩 Servislere Genel Bakış

Sistem, birbirlerinden bağımsız çalışan aşağıdaki servislerden oluşmaktadır:

| Servis Adı | Port | Veritabanı | Sorumluluklar |
|------------|------|------------|---------------|
| Api Gateway | 8085 | - | Tek giriş noktası, JWT doğrulama, Yönlendirme |
| Discovery Server | 8761 | - | Hizmet Kayıt Defteri (Eureka Server) |
| Identity Service | 8086 | MySQL | Kullanıcı Kaydı, Giriş, Token Oluşturma |
| Product Service | 8080 | MongoDB | Ürün Kataloğu Yönetimi (CRUD) |
| Inventory Service | 8082 | MySQL | Gerçek zamanlı stok kontrolü |
| Order Service | 8081 | MySQL | Sipariş işleme, Mantıksal yönetim, Event yayınlama |
| Notification Service | 8084 | - | RabbitMQ olaylarını tüketme (E-posta simülasyonu) |

## 🚀 Nasıl Çalıştırılır?

### Ön Gereksinimler

- JDK 17+
- Docker Desktop & Docker Compose
- Maven

### 1. Depoyu Klonlayın
```bash
git clone https://github.com/BurakKarahan8/DigitalCarsi.git
cd DigitalCarsi
```

### 2. Ortam Değişkenlerini Yapılandırın

🔐 Güvenliği sağlamak için hassas veriler kod içerisinde paylaşılmamıştır. Projenin kök dizininde bir `.env` dosyası oluşturun ve aşağıdaki ayarları yapıştırın:
```properties
# Database Configuration
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_USER=root
MYSQL_PASSWORD=rootpassword

# Message Broker
RABBITMQ_USER=guest
RABBITMQ_PASSWORD=guest

# Security (JWT)
JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
```

> **Not:** Servisleri IDE üzerinden tek tek çalıştıracaksanız, `.env` dosyasının ilgili servisin dizininde olduğundan veya Run/Debug ayarlarında tanımlandığından emin olun.

### 3. Docker ile Derleme ve Çalıştırma

🐳 Tüm altyapıyı (Veritabanları, Broker, Servisler) tek bir komutla ayağa kaldırabilirsiniz:
```bash
# Uygulamayı paketleyin
mvn clean package -DskipTests

# Konteynerleri başlatın
docker-compose up -d
```

## 🔌 API Kullanım Örnekleri

Tüm istekler API Gateway (Port 8085) üzerinden yönlendirilir.

### 🔐 1. Kimlik Doğrulama (Authentication)

**Adım 1:** Yeni bir kullanıcı kaydı oluşturun.
```http
POST /auth/register
```
```json
{
  "name": "developer",
  "email": "dev@example.com",
  "password": "password123"
}
```

**Adım 2:** JWT Token almak için giriş yapın.
```http
POST /auth/token
```
```json
{
  "username": "developer",
  "password": "password123"
}
```

Yanıt olarak bir **Bearer Token** dönecektir. Diğer adımlar için bu token'ı kopyalayın.

### 📦 2. Sipariş Sistemi (Güvenli)

**Sipariş Ver**

**Header Gerekli:** `Authorization: Bearer <TOKEN>`
```http
POST /api/order
```
```json
{
  "orderLineItemsDtoList": [
    {
      "skuCode": "iphone_15",
      "price": 1200,
      "quantity": 1
    }
  ]
}
```

**Akış Şeması:**

1. API Gateway Token'ı doğrular
2. Order Service isteği alır
3. Stok kontrolü için Inventory Service'e senkron çağrı yapılır
4. Stok varsa → Sipariş MySQL'e kaydedilir
5. RabbitMQ'ya asenkron bir event gönderilir
6. Notification Service bu event'i tüketir

## 🛡️ Circuit Breaker Deseni

Sistem, hataları zarif bir şekilde yönetmek için **Resilience4j** kullanır. Eğer Inventory Service çökerse veya yavaşlarsa, Order Service tüm sistemi kilitlemek yerine önceden tanımlanmış bir "fallback" (hata dönüşü) yanıtı verir. Bu, sistemin kararlılığını sağlar.

## 👨‍💻 Yazar

**Burak Karahan** - Yazılım Mühendisi
