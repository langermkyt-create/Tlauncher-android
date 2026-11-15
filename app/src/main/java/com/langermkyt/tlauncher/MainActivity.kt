package com.langermkyt.tlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Night = Color(0xFF10131C)
private val Panel = Color(0xFF1B2130)
private val Mint = Color(0xFF79E0B2)
private val Muted = Color(0xFF9AA6B8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TLauncherApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TLauncherApp() {
    var tab by remember { mutableStateOf("Играть") }
    var version by remember { mutableStateOf("Последняя стабильная") }
    MaterialTheme(colorScheme = darkColorScheme(
        background = Night, surface = Panel, primary = Mint,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Scaffold(
            containerColor = Night,
            bottomBar = {
                NavigationBar(containerColor = Panel) {
                    listOf("Играть", "Моды", "Профили", "Настройки").forEach { item ->
                        val icon = when (item) {
                            "Играть" -> Icons.Default.SportsEsports
                            "Моды" -> Icons.Default.Extension
                            "Профили" -> Icons.Default.Person
                            else -> Icons.Default.Settings
                        }
                        NavigationBarItem(
                            selected = tab == item,
                            onClick = { tab = item },
                            icon = { Icon(icon, contentDescription = item) },
                            label = { Text(item) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Mint, selectedTextColor = Mint,
                                indicatorColor = Color(0xFF293D3A),
                                unselectedIconColor = Muted, unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("TLAUNCHER", color = Mint, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                            Text("Minecraft на телефоне", fontSize = 23.sp, fontWeight = FontWeight.Bold)
                        }
                        Surface(shape = RoundedCornerShape(50), color = Color(0xFF263A35)) {
                            Text("ANDROID", color = Mint, modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp), fontSize = 11.sp)
                        }
                    }
                }
                item {
                    Box(
                        Modifier.fillMaxWidth().height(160.dp)
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF294B46), Color(0xFF252C43), Color(0xFF171D2B))),
                                RoundedCornerShape(24.dp)
                            ).padding(20.dp)
                    ) {
                        Column(Modifier.align(Alignment.BottomStart)) {
                            Text(tab.uppercase(), color = Mint, fontSize = 11.sp, letterSpacing = 2.sp)
                            Text(
                                when (tab) {
                                    "Играть" -> "Твой мир ждёт"
                                    "Моды" -> "Расширяй Minecraft"
                                    "Профили" -> "Твои игровые профили"
                                    else -> "Настрой лаунчер"
                                },
                                fontSize = 22.sp, fontWeight = FontWeight.Bold
                            )
                            Text("Мобильный лаунчер • ранняя разработка", color = Muted, fontSize = 12.sp)
                        }
                    }
                }
                when (tab) {
                    "Играть" -> {
                        item {
                            Text("Версия игры", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            CardBlock {
                                Text("Выбранная версия", color = Muted, fontSize = 12.sp)
                                var expanded by remember { mutableStateOf(false) }
                                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                                    OutlinedTextField(
                                        value = version, onValueChange = {}, readOnly = true,
                                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                                    )
                                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                        listOf("Последняя стабильная", "Minecraft 1.21.4", "Minecraft 1.20.1").forEach {
                                            DropdownMenuItem(text = { Text(it) }, onClick = { version = it; expanded = false })
                                        }
                                    }
                                }
                                Button(
                                    onClick = { }, modifier = Modifier.fillMaxWidth().height(54.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Mint, contentColor = Night),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("ИГРАТЬ", fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                                }
                                Text("Запуск игры подключим после интеграции Android runtime.", color = Muted, fontSize = 11.sp)
                            }
                        }
                        item { FeatureCard(Icons.Default.Download, "Установка версий", "Загрузка игровых версий и необходимых библиотек") }
                        item { FeatureCard(Icons.Default.Tune, "Производительность", "Память, Java runtime и графический движок") }
                    }
                    "Моды" -> {
                        item { FeatureCard(Icons.Default.Search, "Каталог модов", "Поиск и установка модификаций из поддерживаемых каталогов") }
                        item { FeatureCard(Icons.Default.Extension, "Управление модами", "Включение, отключение, обновление и удаление модов") }
                        item { FeatureCard(Icons.Default.Archive, "Сборки и модпаки", "Импорт и экспорт профилей и modpack-архивов") }
                    }
                    "Профили" -> {
                        item { FeatureCard(Icons.Default.Person, "Игровые профили", "Управление отдельными экземплярами Minecraft") }
                        item { FeatureCard(Icons.Default.AccountCircle, "Учётные записи", "Подключение официальной авторизации Microsoft") }
                    }
                    else -> {
                        item { FeatureCard(Icons.Default.Memory, "Память и Java", "Настройка оперативной памяти и runtime") }
                        item { FeatureCard(Icons.Default.Palette, "Оформление", "Тёмная тема и адаптация под экран телефона") }
                        item { FeatureCard(Icons.Default.TouchApp, "Сенсорное управление", "Настраиваемые экранные кнопки и жесты") }
                    }
                }
                item {
                    Text(
                        "Начальный каркас интерфейса. Загрузка игры, модов и авторизация ещё не подключены.",
                        color = Muted, fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CardBlock(content: @Composable ColumnScope.() -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), content = content)
    }
}

@Composable
private fun FeatureCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String) {
    CardBlock {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(color = Color(0xFF293D3A), shape = RoundedCornerShape(14.dp)) {
                Icon(icon, contentDescription = null, tint = Mint, modifier = Modifier.padding(12.dp).size(24.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(description, color = Muted, fontSize = 12.sp)
            }
        }
    }
}
