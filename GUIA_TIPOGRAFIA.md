# 🎨 Guía de Tipografía - Midnight Phonk

## ✅ Fuentes Implementadas

He configurado una tipografía profesional usando **FontFamily.SansSerif** que es una fuente del sistema optimizada y moderna.

---

## 📋 Cómo Usar las Fuentes en tu App

### 1. **Para Títulos Grandes** (Pantalla de bienvenida, títulos principales)
```kotlin
Text(
    text = "Midnight Phonk",
    style = MaterialTheme.typography.displayLarge
)
```

### 2. **Para Títulos de Sección** (Headers, categorías)
```kotlin
Text(
    text = "Catálogo",
    style = MaterialTheme.typography.headlineMedium
)
```

### 3. **Para Títulos de Cards/Items** (Nombres de productos)
```kotlin
Text(
    text = "Nombre del Producto",
    style = MaterialTheme.typography.titleLarge
)
```

### 4. **Para Texto Normal** (Descripciones, contenido)
```kotlin
Text(
    text = "Esta es una descripción del producto...",
    style = MaterialTheme.typography.bodyMedium
)
```

### 5. **Para Botones y Labels**
```kotlin
Button(onClick = {}) {
    Text(
        text = "Comprar",
        style = MaterialTheme.typography.labelLarge
    )
}
```

### 6. **Para Texto Pequeño** (Precios, notas)
```kotlin
Text(
    text = "$19.99",
    style = MaterialTheme.typography.bodySmall
)
```

---

## 🎯 Jerarquía de Estilos

### Display (Títulos muy grandes)
- `displayLarge` - 57sp, Bold
- `displayMedium` - 45sp, Bold  
- `displaySmall` - 36sp, SemiBold

### Headline (Títulos de sección)
- `headlineLarge` - 32sp, Bold
- `headlineMedium` - 28sp, SemiBold
- `headlineSmall` - 24sp, SemiBold

### Title (Títulos medianos)
- `titleLarge` - 22sp, SemiBold
- `titleMedium` - 16sp, Medium
- `titleSmall` - 14sp, Medium

### Body (Texto del cuerpo)
- `bodyLarge` - 16sp, Normal
- `bodyMedium` - 14sp, Normal
- `bodySmall` - 12sp, Normal

### Label (Botones, etiquetas)
- `labelLarge` - 14sp, Medium
- `labelMedium` - 12sp, Medium
- `labelSmall` - 11sp, Medium

---

## 💡 Ejemplos Prácticos para Midnight Phonk

### LoginView / RegisterView
```kotlin
// Logo/Título
Text(
    text = "Midnight Phonk",
    style = MaterialTheme.typography.displayMedium
)

// Labels de campos
Text(
    text = "Email",
    style = MaterialTheme.typography.labelMedium
)

// Botones
Button(onClick = {}) {
    Text(
        text = "Iniciar Sesión",
        style = MaterialTheme.typography.labelLarge
    )
}
```

### CatalogView
```kotlin
// Título de sección
Text(
    text = "Productos Destacados",
    style = MaterialTheme.typography.headlineMedium
)

// Nombre de producto
Text(
    text = "Auriculares Premium",
    style = MaterialTheme.typography.titleLarge
)

// Precio
Text(
    text = "$49.99",
    style = MaterialTheme.typography.titleMedium
)

// Descripción
Text(
    text = "Alta calidad de sonido...",
    style = MaterialTheme.typography.bodyMedium
)
```

### CartView
```kotlin
// Título del carrito
Text(
    text = "Mi Carrito",
    style = MaterialTheme.typography.headlineLarge
)

// Nombre del producto en carrito
Text(
    text = "Producto X",
    style = MaterialTheme.typography.titleMedium
)

// Total
Text(
    text = "Total: $199.99",
    style = MaterialTheme.typography.titleLarge
)
```

---

## 🚀 Ventajas de Esta Configuración

✅ **Profesional** - Tipografía consistente en toda la app
✅ **Legible** - Tamaños y espaciados optimizados
✅ **Moderna** - Sigue las guías de Material Design 3
✅ **Sin archivos extra** - Usa fuentes del sistema
✅ **Rápida** - No necesita cargar fuentes externas
✅ **Consistente** - Todos los textos se ven igual

---

## 📝 Nota Importante

Todas las fuentes ya están configuradas automáticamente en tu tema. 
Solo necesitas usar `MaterialTheme.typography.___` en tus componentes Text.

**No necesitas importar nada adicional**, simplemente usa:
```kotlin
Text(
    text = "Tu texto",
    style = MaterialTheme.typography.bodyMedium
)
```

---

## 🎨 ¿Quieres Cambiar la Fuente Principal?

Si más adelante quieres usar Google Fonts (Poppins, Roboto, etc.), solo necesitas:

1. Agregar la dependencia de Google Fonts en `build.gradle.kts`
2. Modificar `AppFontFamily` en `Type.kt`
3. ¡Listo! Todos tus textos cambiarán automáticamente

---

**¡Tu app ahora tiene una tipografía profesional y moderna!** 🎉

