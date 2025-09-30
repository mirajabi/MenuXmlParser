# MenuXmlParser

An Android XML menu parser with dynamic menu reading, display, and editing capabilities.

## Features

- **XML Parsing**: Read menu XML files from assets using Jackson
- **Hierarchical Display**: Show menu groups, top-level menus, operators, and charge amounts
- **Dual UI Implementation**: Complete implementation in both Kotlin and Java
- **Local Storage**: Edit and save menu modifications to internal storage
- **Clean Architecture**: Separation of Domain, Data, and Presentation layers
- **RecyclerView Navigation**: Optimized list display with custom adapters
- **Dynamic Menu Structure**: Support for complex nested menu hierarchies
- **Charge System**: Specialized UI for mobile operators and charge amounts

## Project Structure

### Kotlin Implementation
```
com.miaadrajabi.menuxmlparser/
├── domain/
│   ├── model/          # Domain models
│   └── usecase/        # MenuEditor use case
├── data/
│   ├── xml/           # XML models and configuration
│   ├── mapper/        # XML-Domain mappers
│   ├── source/        # Assets and Local data sources
│   └── repository/    # MenuRepository
└── presentation/
    └── menu/          # ViewModel, Activity, Adapters
```

### Java Implementation
```
com.miaadrajabi.menuxmlparser.java/
├── domain/
│   ├── model/         # Java domain models
│   └── usecase/       # Java MenuEditor
├── data/
│   ├── xml/          # Java XML models
│   ├── mapper/       # Java XML-Domain mappers
│   ├── source/       # Java data sources
│   └── repository/   # Java MenuRepository
└── presentation/
    └── menu/         # Java ViewModel, Activity, Adapters
```

## Architecture

The project follows **Clean Architecture** principles:

- **Domain Layer**: Business logic and entities
- **Data Layer**: Data sources, repositories, and mappers
- **Presentation Layer**: UI components, ViewModels, and adapters

## Usage

1. Launch the app
2. Choose between Kotlin UI or Java UI
3. Navigate through menu groups by tapping on items
4. For charge menus: Select an operator to view available charge amounts
5. Tap on charge amounts to get detailed service information

## UI Navigation Flow

1. **Root Level**: Display menu groups (Sale, Balance, Bill Payment, Sim Charge, etc.)
2. **Group Level**: Show top-level menus within selected group
3. **Operator Level**: For charge menus, display horizontal operator chips (IRMCI, Irancell, Rightel, etc.)
4. **Amount Level**: Show vertical list of available charge amounts

## XML Structure Support

The parser supports complex XML structures including:
- Menu groups with multiple top-level menus
- Nested menu items with operators
- Service descriptors with charge amounts
- USSD commands and provider information
- Enable/disable states for menu items

## Dependencies

- **Jackson XML**: XML parsing and object mapping
- **RecyclerView**: Efficient list display
- **Material3**: Modern UI components
- **ViewModel & LiveData**: State management
- **ConstraintLayout**: Flexible UI layouts

## Building

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Build and run on Android device/emulator

## Requirements

- Android API 21+
- JDK 17
- Android Gradle Plugin 8.7.2

## License

This project is released under the MIT License.
