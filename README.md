# TLauncher Android — Minecraft Java runtime prototype

Цель проекта — создать удобный Android-лаунчер Minecraft Java Edition с сенсорным управлением, профилями и установкой модификаций.

## Что изменено сейчас

GitHub Actions теперь собирает не прежнюю пустую Compose-оболочку, а Android runtime Amethyst (проект на базе PojavLauncher), который предназначен для запуска Minecraft Java Edition на Android. В upstream-проекте заявлена поддержка широкого набора версий Minecraft и моддинга через Forge и Fabric.

Сборка получает исходники Amethyst с рекурсивными submodules, скачивает multi-architecture Java runtime и собирает debug APK. Артефакт называется `tlauncher-android-runtime-debug`.

**Важно:** это пока интеграция существующего Android runtime как основы, а не законченный самостоятельный форк с собственным интерфейсом TLauncher. APK будет иметь приложение/интерфейс Amethyst upstream. Необходимо отдельно реализовать брендинг и интеграцию нашего мобильного UI, проверить загрузку модов на устройстве, Microsoft OAuth и совместимость конкретных версий/модлоадеров.

## Сборка

Workflow запускается при push в `main` или вручную через GitHub Actions:

https://github.com/langermkyt-create/Tlauncher-android/actions

После успешного выполнения скачай артефакт `tlauncher-android-runtime-debug`.

## Основа и лицензии

- Amethyst Android: https://github.com/AngelAuraMC/Amethyst-Android
- Ветка сборки: `v3_openjdk`
- Лицензия Amethyst: LGPL-3.0. При распространении производной сборки необходимо соблюдать LGPL, сохранять уведомления и предоставлять соответствующий исходный код/изменения.
- TLauncher Desktop: https://github.com/tlauncher/TLauncher-Desktop

Проект не аффилирован с Mojang, Microsoft, TLauncher или командой Amethyst. Minecraft — продукт Mojang/Microsoft.
