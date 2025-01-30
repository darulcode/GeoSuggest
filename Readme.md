# City Suggestions API

## Overview
City Suggestions API adalah REST API yang menyediakan fitur auto-complete untuk nama kota besar berdasarkan input pengguna. API ini didesain untuk memberikan saran kota dengan skor yang menunjukkan tingkat relevansi hasil pencarian.

## Features
- Endpoint utama tersedia di `/suggestions`.
- Mendukung pencarian berdasarkan query string `q`.
- Memungkinkan peningkatan akurasi dengan parameter `latitude` dan `longitude`.
- Menggunakan struktur data **Trie** untuk optimasi pencarian nama kota dari file CSV, sehingga tidak membebani sumber daya dengan pembacaan berulang.
- Respons dalam format JSON dengan daftar kota yang diurutkan berdasarkan skor relevansi.
- API dapat dicoba pada: **[https://suggest.darulcode.web.id](https://suggest.darulcode.web.id)**

## API Endpoints
### **GET** `/suggestions`
#### **Query Parameters**
| Parameter   | Tipe     | Wajib  | Deskripsi |
|------------|---------|--------|------------|
| `q`        | String  | Yes    | Nama kota (parsial atau lengkap) untuk pencarian. |
| `latitude`  | Double  | No     | Koordinat lintang pengguna untuk peningkatan skor. |
| `longitude` | Double  | No     | Koordinat bujur pengguna untuk peningkatan skor. |

#### **Response Format**
```json
{
  "status" : 200,
  "message" : "successfully get data",
  "suggestions": [
    {
      "name": "London, ON, Canada",
      "latitude": "42.98339",
      "longitude": "-81.23304",
      "score": 0.9
    },
    {
      "name": "London, OH, USA",
      "latitude": "39.88645",
      "longitude": "-83.44825",
      "score": 0.5
    }
  ]
}
```

#### **Contoh Respons Saat Tidak Ada Hasil**
```json
{
  "suggestions": []
}
```

### **GET** `/suggestions/city`
#### **Query Parameters**
| Parameter   | Tipe     | Wajib  | Deskripsi |
|------------|---------|--------|------------|
| `name`     | String  | Yes    | Nama kota sebagai prefix untuk mendapatkan saran nama kota. |

#### **Response Format**
```json
{
  "status" : 200,
  "message" : "successfully get data",
  "suggestions": ["London", "London Town"]
}
```

## Implementation Details
- **Trie Structure for Fast Lookup**:
    - Data kota dari CSV dimuat sekali ke dalam struktur **Trie** saat aplikasi dimulai.
    - Pencarian dilakukan langsung pada Trie untuk performa optimal.
    - Menghindari pembacaan ulang dari file setiap kali request diterima.
- **Scoring System**:
    - Skor dihitung berdasarkan **kemiripan nama kota** dengan input `q`.
    - Jika `latitude` dan `longitude` diberikan, **kedekatan geografis** dengan lokasi pengguna juga mempengaruhi skor.
- **Tech Stack**:
    - **Spring Boot** untuk backend API.
    - **Java Trie Implementation** untuk optimasi pencarian.
    - **CSV Data Source** untuk daftar kota.
    - **Docker** (opsional) untuk deployment.

## How to Run Locally
### **Requirements**
- Java 17+
- Maven

### **Steps**
1. Clone repository:
   ```sh
   git clone https://github.com/your-repo/city-suggestions-api.git
   cd city-suggestions-api
   ```
2. Build dan jalankan aplikasi:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
3. API tersedia di `http://localhost:8080/suggestions`

## Deployment
Aplikasi ini telah di-deploy dan dapat diuji secara langsung di **[https://suggest.darulcode.web.id](https://suggest.darulcode.web.id)**.

## Testing
Unit test telah disediakan menggunakan **JUnit** dan **MockMvc**. Jalankan pengujian dengan perintah berikut:
```sh
mvn test
```

## Contributors
- **A Darul Ilmi** - Developer

## License
MIT License