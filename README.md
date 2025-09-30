# MenuXmlParser

یک پارسر XML منو برای اندروید که قابلیت خواندن، نمایش و ویرایش منوهای پویا را دارد.

## ویژگی‌ها

- **پارس XML**: خواندن فایل XML منو از assets با Jackson
- **نمایش سلسله‌مراتبی**: نمایش گروه‌ها، منوهای سطح بالا، اپراتورها و مقادیر شارژ
- **UI دوگانه**: پیاده‌سازی کامل در Kotlin و Java
- **ذخیره‌سازی محلی**: امکان ویرایش و ذخیره تغییرات در حافظه داخلی
- **معماری Clean**: جدایی Domain، Data و Presentation
- **RecyclerView**: نمایش لیست‌های بهینه با آداپترهای سفارشی

## ساختار پروژه

### Kotlin Implementation
```
com.miaadrajabi.menuxmlparser/
├── domain/
│   ├── model/          # مدل‌های دامین
│   └── usecase/        # MenuEditor
├── data/
│   ├── xml/           # مدل‌های XML و کانفیگ
│   ├── mapper/        # مپرهای XML-Domain
│   ├── source/        # Assets و Local data sources
│   └── repository/    # MenuRepository
└── presentation/
    └── menu/          # ViewModel، Activity، Adapters
```

### Java Implementation
```
com.miaadrajabi.menuxmlparser.java/
├── domain/
│   ├── model/         # مدل‌های دامین جاوا
│   └── usecase/       # MenuEditor جاوا
├── data/
│   ├── xml/          # مدل‌های XML جاوا
│   ├── mapper/       # مپرهای XML-Domain جاوا
│   ├── source/       # Data sources جاوا
│   └── repository/   # MenuRepository جاوا
└── presentation/
    └── menu/         # ViewModel، Activity، Adapters جاوا
```

## استفاده

1. اپ را اجرا کنید
2. بین UI کاتلین و جاوا انتخاب کنید
3. روی گروه‌های منو کلیک کنید تا منوهای زیرمجموعه باز شوند
4. برای شارژ: اپراتور انتخاب کنید و مقادیر شارژ را مشاهده کنید

## وابستگی‌ها

- Jackson XML: پارس فایل‌های XML
- RecyclerView: نمایش لیست‌ها
- Material3: UI components
- ViewModel & LiveData: مدیریت state

## لایسنس

این پروژه تحت لایسنس MIT منتشر شده است.
