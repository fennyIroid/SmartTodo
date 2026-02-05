# SmartTodo

SmartTodo is a modern Android productivity application built with Jetpack Compose, designed to help you organize tasks, track habits, and visualize your productivity.

## ✨ Features

### 📅 Smart Planner
- **Monthly Calendar**: A compact calendar view with color-coded dot indicators for task categories.
- **Daily View**: Time-blocked schedule list to easily manage your day's agenda.

### 📝 Task Management
- **Detailed Task Creation**: Add tasks with priority, due dates, categories, and categories.
- **Repeating Tasks**: Set tasks to repeat daily, weekly, or monthly.
- **Reminders**: Toggle notifications to never miss a deadline.
- **Categorization**: Organize with custom categories like Work, Personal, and Shopping.

### 📊 Productivity Stats
- **Activity Chart**: A custom-drawn, dynamic 7-day bar chart to visualize your workload.
- **Today's Focus**: A progress ring that fills as you complete today's tasks.
- **Streak Tracking**: Keeps count of your consecutive productive days.
- **Focus Time**: Estimates your deep work hours based on completed task priorities.
- **Achievements**: Unlock milestones like "First Steps" and "On Fire" as you progress.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel) w/ Clean Architecture
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Asynchronous**: Kotlin Coroutines & Flows
- **Navigation**: Jetpack Compose Navigation

## 🚀 Setup & Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/SmartTodo.git
   ```

2. **Open in Android Studio**:
   - Select "Open an existing Android Studio Project" and point to the cloned folder.

3. **Build the project**:
   - Let Gradle sync complete.
   - Run the app on an Emulator or Physical Device (API 26+ recommended).

## 🎨 Design

The app follows a modern, clean aesthetic featuring:
- **Soft Peach** highlights for a warm, inviting feel.
- **Glassmorphism** elements in cards and overlays.
- **Custom Canvas** drawings for charts and progress rings.

## 🤝 Contribution

Feel free to fork this repository and submit pull requests. Suggestions and bug reports are welcome!
