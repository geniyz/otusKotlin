# User Story 1: Сохранение в избранное

## Сохранение

### TC-A1: успешный

**Входные параметры:**
- **Тип:** `href`
- **Содержимое:** http://localhost/
- **Заголовок:** Тест
- **Метки:**
  - Test
  - test1
  - test2

**Выходные параметры:**
- **Результат**: Успех
- **Объект:**
  - **Идентификатор:** `уникальный идентификатор` 
  - **Тип:** `href`
  - **Содержимое:** http://localhost/
  - **Заголовок:** Тест
  - **Метки:** `["Test", "test1", "test2"]`
  - **Дата-время создания:** только что


### TC-A2: ошибка валидации содержимого

**Входные параметры:**
- **Тип:** `text`
- **Содержимое:** `null`
- **Заголовок:** Test
- **Метки:**
  - А

**Выходные параметры:**
- **Результат**: Ошибка валидации: содержимое не может быть пустым  
- **Объект:** `null`

### TC-A3: ошибка валидации меток

**Входные параметры:**
- **Тип:** `text`
- **Содержимое:** `null`
- **Заголовок:** Test
- **Метки:**
  - test1,test2
  - Test

**Выходные параметры:**
- **Результат**: Ошибка валидации: метка содержит недопустимые символы
- **Объект:** `null`

### TC-A4: ошибка валидации заголовка

**Входные параметры:**
- **Тип:** `text`
- **Содержимое:** 123
- **Заголовок:** Длинный-предлинный заголовок, такой длинный, что аж мурашки по коже, от самой макушки и до пят, это почти как от Земли до Луны и обратно
- **Метки:**
  - Test

**Выходные параметры:**
- **Результат**: Ошибка валидации: заголовок превышает ограничение 128 символов 
- **Объект:** `null`


### TC-A5: успешный, ссылка с пустым заголовком

**Входные параметры:**
- **Тип:** `href`
- **Содержимое:** http://localhost/
- **Заголовок:** `null`
- **Метки:**
  - Test

**Выходные параметры:**
- **Результат**: Успех
- **Объект:**
  - **Идентификатор:** `уникальный идентификатор`
  - **Тип:** `href`
  - **Содержимое:** http://localhost/
  - **Заголовок:** http://localhost/
  - **Метки:** `["Test"]`
  - **Дата-время создания:** только что

### TC-A6: успешный, текст с пустым заголовком

**Входные параметры:**
- **Тип:** `text`
- **Содержимое:** 123
- **Заголовок:** `null`
- **Метки:**
  - test1

**Выходные параметры:**
- **Результат**: Успех
- **Объект:**
  - **Идентификатор:** `уникальный идентификатор`
  - **Тип:** `text`
  - **Содержимое:** 123
  - **Заголовок:** 123
  - **Метки:** `["test1"]`
  - **Дата-время создания:** только что

### TC-A6: успешный, длинный текст с пустым заголовком

**Входные параметры:**
- **Тип:** `text`
- **Содержимое:** Длинный-предлинный текст, такой длинный, что аж мурашки по коже, от самой макушки и до пят, это почти как от Земли до Луны и обратно
- **Заголовок:** `null`
- **Метки:**
  - test2

**Выходные параметры:**
- **Результат**: Успех
- **Объект:**
  - **Идентификатор:** `уникальный идентификатор`
  - **Тип:** `text`
  - **Содержимое:** Длинный-предлинный текст, такой длинный, что аж мурашки по коже, от самой макушки и до пят, это почти как от Земли до Луны и обратно
  - **Заголовок:**  Длинный-предлинный текст, такой длинный, что аж мурашки по коже, от самой макушки и до пят, это почти как от Земли до Луны
  - **Метки:** `["test2"]`
  - **Дата-время создания:** только что
