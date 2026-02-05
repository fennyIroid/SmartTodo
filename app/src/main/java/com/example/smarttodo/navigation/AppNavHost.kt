package com.example.smarttodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarttodo.ui.HomeScreen.HomeScreen
import com.example.smarttodo.ui.addtask.AddTaskScreen
import com.example.smarttodo.ui.planner.PlannerScreen
import com.example.smarttodo.ui.stats.StatsScreen
import com.example.smarttodo.ui.screens.CalendarScreen
import com.example.smarttodo.ui.screens.ProfileScreen
import com.example.smarttodo.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.SPLASH
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigationHome = {
                    navController.navigate(Routes.HOME){
                        popUpTo(Routes.SPLASH){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onAddTaskClick = {
                    navController.navigate(Routes.ADD_TASK)
                },
                onTaskClick = { taskId ->
                    navController.navigate("task_details/$taskId")
                }
            )
        }

        composable(Routes.ADD_TASK) {
            AddTaskScreen(
                onCancel = { navController.popBackStack() },
                onDone = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.TASK_DETAILS,
            arguments = listOf(
                androidx.navigation.navArgument("taskId") {
                    type = androidx.navigation.NavType.LongType
                }
            )
        ) {
            com.example.smarttodo.ui.taskdetails.TaskDetailsScreen(
                onBack = { navController.popBackStack() },
                onEdit = { taskId ->
                    navController.navigate("edit_task/$taskId")
                }
            )
        }

        composable(
            route = Routes.EDIT_TASK,
            arguments = listOf(
                androidx.navigation.navArgument("taskId") {
                    type = androidx.navigation.NavType.LongType
                }
            )
        ) {
            AddTaskScreen(
                onCancel = { navController.popBackStack() },
                onDone = { navController.popBackStack() }
            )
        }

        composable(Routes.CALENDAR) {
            PlannerScreen(
                onAddTaskClick = { navController.navigate(Routes.ADD_TASK) }
            )
        }

        composable(Routes.STATS) {
            StatsScreen()
        }

        composable(Routes.PROFILE) {
            ProfileScreen()
        }
    }
}