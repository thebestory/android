# TheBestory

Этот changelog является заметками, написанными в течение разработки.

### 01.10.2016
* @woofilee: Минимальный API опущен с 21 до 17.
* @woofilee: Обновлен API для Fragment классов. Теперь используется библиотека Support.V13 для совместимости с App пакетом.
* @woofilee: Добавлен BaseActivity для будущей реализации смены светлой/темной темы.
* @woofilee: Добавлена интеграция с TravisCI.

### 25.09.2016
* @Oktai15: Снова изменен дизайн, и снова не навсегда.
* @Oktai15: Предпринята попытка изменения дизайна шапки Navigation Drawer, приведшая к новым слетам разметки.
* @Oktai15: Исправлена бага с незакрывающейся клавиатурой при закрытии станицы написания новой истории. 

### 24.09.2016
* @Oktai15 + @woofilee: Возвращена страница написания истории.
* @Oktai15 + @woofilee: Доработан дизайн шапки Navigation Drawer.
* @Oktai15 + @woofilee: Теперь у нас есть значок приложения :tada:
* @Oktai15 + @woofilee: Добавлена бага с незакрывающейся клавиатурой при закрытии станицы написания новой истории. Исправить.

    <img alt="New story" src="https://pp.vk.me/c626126/v626126257/29076/6xuG5NZ_2_s.jpg" height="144px">

### 23.09.2016
* @woofilee: Добавлены вкладки для страницы историй. Реализован адаптер для него.

    <img alt="Navigation Drawer" src="https://pp.vk.me/c626221/v626221257/2cd45/C_NaJFffeTo.jpg" height="144px">
    <img alt="Stories page view" src="https://pp.vk.me/c626221/v626221257/2cd4f/2zZe2Xehf08.jpg" height="144px">

### 22.09.2016
* @woofilee: Удалена страница создания истории, т.к. она более не является Activity классом. Вместо него будет реализован Fragment.
* @woofilee: Переписан Navigation Drawer - там теперь профиль взаместо логотипа. Логотип стоит будет разместить на заднем фоне.
* @woofilee: MainActivity переписан - теперь он является контейнером для всех страниц, содержащихся в меню (Navigation Drawer).
* @woofilee: Из класса страницы историй вынесен Adapter для табов, как отдельный класс. Сам адаптер не реализован.
* @woofilee: Цвета элементов временно переписаны. Они так же временные.

### 21.09.2016
* @Oktai15: Исправлены ошибки в Navigation Drawer.
* @Oktai15: Добавлены Tabs на All Stories.
* @Oktai15: Изменен EditText в Send your story.

### 20.09.2016
Проект создан! :tada:

* @Oktai15: Добавлено "Navigation Drawer", "Send your story" и создана Activity для "All stories". 
* @Oktai15: Подобран ориентировочный стиль для приложения. 

    <img src="https://pp.vk.me/c626521/v626521113/275be/k3-3EvZOit8.jpg" height="144px">
    <img src="https://pp.vk.me/c626521/v626521096/313d5/ouTHqoDlOZ8.jpg" height="144px">
