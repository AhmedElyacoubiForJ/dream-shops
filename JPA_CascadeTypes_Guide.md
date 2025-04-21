

`CascadeType` ist ein wichtiges Konzept in JPA, das steuert, wie sich **Operationen** auf
verknüpfte Entitäten auswirken. Ich erkläre dir die drei Varianten, die für deine `Category`-Beziehung relevant sein
könnten:

### 1️⃣ **CascadeType.ALL**

Das bedeutet, dass alle Änderungen an `Product` automatisch auch auf die `Category` angewendet werden.  
👉 Beispiel:

- Wenn du ein `Product` löschst, wird auch die `Category` gelöscht.
- Wenn du ein `Product` speicherst, wird auch die `Category` gespeichert.

🔴 **Problem**: In deinem Fall könnte das gefährlich sein, weil eine **Kategorie normalerweise unabhängig existieren
sollte**. Du willst ja nicht, dass die gesamte `Category` verschwindet, wenn ein Produkt gelöscht wird.

### 2️⃣ **CascadeType.PERSIST**

Das bedeutet, dass beim Speichern eines `Product` die zugehörige `Category` automatisch mit gespeichert wird.  
👉 Beispiel:

- Wenn du ein `Product` neu erstellst und speicherst, wird die `Category`, falls sie neu ist, **auch gespeichert**.
- Aber wenn du ein `Product` löschst, bleibt die `Category` **unverändert**.

✅ **Vorteil**: Die `Category` wird beim Anlegen neuer Produkte automatisch persistiert, aber sie wird nicht gelöscht,
wenn Produkte entfernt werden.

### 3️⃣ **CascadeType.MERGE**

Das bedeutet, dass Änderungen an `Product` sich nur dann auf `Category` auswirken, wenn die `Category` bereits
existiert.  
👉 Beispiel:

- Wenn du ein `Product` aktualisierst und eine bereits bestehende `Category` zuweist, wird die Kategorie ebenfalls *
  *aktualisiert**.
- Wenn du ein neues `Product` anlegst, wird eine bereits gespeicherte `Category` **nicht automatisch gespeichert**.

✅ **Vorteil**: Gut, wenn du sicherstellen willst, dass bestehende Kategorien **nicht automatisch überschrieben werden**,
aber trotzdem aktualisierbar bleiben.

---

### 🔥 **Empfehlung:**

Da `Category` eine **unabhängige Entität** sein sollte, wäre `CascadeType.PERSIST` oder `CascadeType.MERGE` besser
als `ALL`.

- `PERSIST` ist ideal, wenn neue Kategorien automatisch gespeichert werden sollen.
- `MERGE` ist sinnvoll, wenn du **nur bestehende Kategorien** verändern willst.

💡 **Falls du möchtest, dass Produkte nur Kategorien zugewiesen werden, ohne sie direkt zu verändern, kannst du
Kaskadierung ganz weglassen!**

---

### 1️⃣ **CascadeType.ALL**
👉 **Bedeutung**: Alle Operationen an `Product` (Speichern, Aktualisieren, Löschen) wirken sich **automatisch** auf die zugehörige `Category` aus.
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
productRepository.save(product); // Speichert sowohl Product als auch Category

productRepository.delete(product); // Löscht auch die Category! (Problematisch)
```
⚠️ **Problem**: Wenn du ein Produkt löschst, wird auch die **Kategorie entfernt**, selbst wenn andere Produkte sie noch nutzen!

---

### 2️⃣ **CascadeType.PERSIST**
👉 **Bedeutung**: Eine neue `Category` wird **automatisch gespeichert**, wenn sie mit `Product` verknüpft ist.
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
productRepository.save(product); // Speichert Product + Category (wenn sie neu ist)

productRepository.delete(product); // Nur Product wird gelöscht, Category bleibt!
```
✅ **Vorteil**: Hilfreich, wenn du möchtest, dass neue Kategorien automatisch gespeichert werden, aber nicht gelöscht werden.

---

### 3️⃣ **CascadeType.MERGE**
👉 **Bedeutung**: Änderungen an einer bestehenden `Category` wirken sich aus, aber neue `Category` wird **nicht automatisch gespeichert**.
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

productRepository.save(product); // Speichert nur das Product, nicht Category!

category.setName("Neue Kategorie");
productRepository.save(product); // Aktualisiert die Kategorie
```
✅ **Vorteil**: Bestehende Kategorien können aktualisiert werden, aber sie werden **nicht einfach gespeichert**, wenn sie neu sind.

---

### 🔥 **Fazit & Empfehlung für dein Projekt**
🔹 Falls eine Kategorie **unabhängig** existieren soll, empfehle ich `PERSIST` oder `MERGE`.  
🔹 Falls neue Kategorien **automatisch gespeichert** werden sollen → `PERSIST`  
🔹 Falls nur bestehende Kategorien geändert werden dürfen → `MERGE`  
🔹 Falls du möchtest, dass sich Änderungen **gar nicht** auf `Category` auswirken, kannst du Kaskadierung **ganz weglassen**!
