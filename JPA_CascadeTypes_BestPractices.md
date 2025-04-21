## Detaillierte Beschreibung zu `CascadeType` – ideal als Referenz für deine zukünftigen Projekte.

---

### 📌 **CascadeType – Überblick & Beispiele**
#### 📝 **Was ist CascadeType?**
`CascadeType` steuert, wie sich Operationen (Speichern, Aktualisieren, Löschen) in JPA/Hibernate auf verknüpfte Entitäten auswirken.

---

### 1️⃣ **CascadeType.ALL**
💡 **Bedeutung**: Alle Änderungen an `Product` wirken sich **automatisch** auf die `Category` aus.

```java
@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "category_id")
private Category category;
```

🔍 **Beispiel:**
```java
Product product = new Product();
product.setName("Laptop");
Category category = new Category();
category.setName("Elektronik");

product.setCategory(category);
productRepository.save(product); // Speichert Product + Category

productRepository.delete(product); // ❌ Löscht auch die Category!
```

⚠️ **Achtung**: Falls mehrere Produkte dieselbe Kategorie nutzen, kann die `Category` ungewollt gelöscht werden!

---

### 2️⃣ **CascadeType.PERSIST**
💡 **Bedeutung**: Neue `Category` wird **automatisch gespeichert**, aber **nicht** gelöscht.

```java
@ManyToOne(cascade = CascadeType.PERSIST)
@JoinColumn(name = "category_id")
private Category category;
```

🔍 **Beispiel:**
```java
Product product = new Product();
product.setName("Laptop");
Category category = new Category();
category.setName("Elektronik");

product.setCategory(category);
productRepository.save(product); // ✅ Speichert Product + Category (wenn neu)

productRepository.delete(product); // 🗑️ Löscht nur Product, Category bleibt erhalten!
```

✅ **Gut für unabhängige Kategorien**, die nur beim Erstellen gespeichert werden sollen.

---

### 3️⃣ **CascadeType.MERGE**
💡 **Bedeutung**: Bestehende Kategorien werden **aktualisiert**, aber **nicht automatisch gespeichert**.

```java
@ManyToOne(cascade = CascadeType.MERGE)
@JoinColumn(name = "category_id")
private Category category;
```

🔍 **Beispiel:**
```java
Category category = categoryRepository.findById(1L).get(); // Existierende Kategorie holen
Product product = new Product();
product.setName("Laptop");
product.setCategory(category);

productRepository.save(product); // ✅ Speichert Product, aber nicht Category!

category.setName("Neue Kategorie");
productRepository.save(product); // 🔄 Aktualisiert die Kategorie
```

✅ **Hilfreich, wenn du bestehende Kategorien verändern möchtest, aber keine neuen automatisch speichern willst.**

---

### 🔥 **Fazit & Empfehlung**
🔹 Falls eine `Category` **unabhängig** existieren soll → `PERSIST` oder `MERGE`.  
🔹 Falls neue `Category` **automatisch gespeichert** werden sollen → `PERSIST`.  
🔹 Falls nur bestehende `Category` verändert werden darf → `MERGE`.  
🔹 Falls du möchtest, dass sich Änderungen **gar nicht** auf `Category` auswirken → **Kaskadierung ganz weglassen!**

