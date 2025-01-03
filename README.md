![MovieCenter Logo](https://via.placeholder.com/150)

# MovieCenter

🎬 MovieCenter, modern Android uygulama geliştirme pratiklerini kullanarak film ve video içeriği keşfetmenizi sağlayan bir uygulamadır. Bu projede Retrofit, OkHttp, Jetpack Compose, Jetpack Navigation ve diğer birçok popüler kütüphane entegre edilmiştir.

---

## 🚀 Hızlı Başlangıç

MovieCenter, aşağıdaki adımları izleyerek kolayca kurulabilir:

1. Projeyi klonlayın:
    ```bash
    git clone https://github.com/kullaniciadi/MovieCenter.git
    ```
2. Android Studio ile projeyi açın.
3. `build.gradle` dosyalarının bağımlılıklarının yüklendiğinden emin olun.
4. Uygulamayı bir emulatörde veya gerçek bir cihazda çalıştırın.

---

## 🎥 Özellikler
- 🎥 **Film Listesi**: Popüler filmleri görüntüleyin.
- 📖 **Film Detayları**: Seçilen bir film hakkında detaylı bilgi.
- ▶️ **YouTube Oynatıcı**: Fragmanları YouTube Player ile oynatın.
- 🏗️ **Modern Mimari**: MVVM (Model-View-ViewModel) tasarım deseni uygulanmıştır.
- 🎨 **Jetpack Compose** ile dinamik ve etkileyici UI tasarımı.

---

## 🛠️ Kullanılan Teknolojiler ve Kütüphaneler

### 1. Retrofit
HTTP isteklerini kolayca yapabilmek için Retrofit kullanılmıştır. API'lerle haberleşme süreçlerini kolaylaştırır.
```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

### 2. OkHttp
Ağ isteklerini yönetmek ve izlemek için OkHttp kullanılmıştır.
```gradle
implementation 'com.squareup.okhttp3:okhttp:4.11.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'
```

### 3. Jetpack Compose
Modern ve deklaratif bir UI için Jetpack Compose tercih edilmiştir.
```gradle
implementation 'androidx.compose.ui:ui:1.5.0'
implementation 'androidx.compose.material:material:1.5.0'
implementation 'androidx.compose.ui:ui-tooling:1.5.0'
```

### 4. Jetpack Navigation
Uygulamanın ekranlar arası gezinmesini yönetmek için Jetpack Navigation kullanılmıştır.
```gradle
implementation 'androidx.navigation:navigation-compose:2.7.0'
```

### 5. LiveData
UI ile veriler arasındaki iletişimi reaktif bir şekilde sağlamak için LiveData kullanılmıştır.
```gradle
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'
```

### 6. ViewModel
UI mantığını veri kaynağından ayırmak ve veri sürekliliğini sağlamak için ViewModel kütüphanesi kullanılmıştır.
```gradle
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
```

### 7. YouTube Player
YouTube videolarını entegre etmek için YouTubePlayer kullanılmıştır.
```gradle
implementation 'com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0'
```

### 8. Lifecycle Kütüphaneleri
Activity ve fragment döngülerini izlemek için Lifecycle kütüphaneleri kullanılmıştır.
```gradle
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
```

---

## 📸 Ekran Görüntüleri

### YouTube Fragman Oynatıcı
![YouTube Player](https://via.placeholder.com/500x300)

### Video Tanıtımı
YouTube'da uygulamanın kısa bir tanıtım videosunu izlemek için aşağıdaki bağlantıyı kullanabilirsiniz:
[![YouTube Tanıtım Videosu](https://img.youtube.com/vi/vPZDKscaO30/0.jpg)](https://www.youtube.com/shorts/vPZDKscaO30)

---

## 🔖 Lisans
Bu proje MIT Lisansı altında lisanslanmıştır.

MIT Lisansı, açık kaynak yazılım lisansı türlerinden biridir. Bu lisans şu hakları sağlar:

- **Kullanım İzni**: Kodu istediğiniz gibi kullanabilirsiniz.
- **Dağıtım İzni**: Kodu dağıtabilir ve paylaşabilirsiniz.
- **Değiştirme ve Ticaret**: Kodu değiştirebilir ve ticari projelerinizde kullanabilirsiniz.

Ancak şu koşullara dikkat edilmelidir:

1. Yazılım "olduğu gibi" sağlanır, yani herhangi bir garanti verilmez.
2. Lisans metni proje ile birlikte paylaşılmalıdır. Detaylı bilgi için [LICENSE](LICENSE) dosyasına göz atabilirsiniz.

