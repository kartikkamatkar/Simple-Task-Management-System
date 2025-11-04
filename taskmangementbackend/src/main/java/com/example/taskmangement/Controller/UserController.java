package com.example.taskmangement.Controller;

import com.example.taskmangement.Model.TaskModel;
import com.example.taskmangement.Model.UserModel;
import com.example.taskmangement.Service.TaskService;
import com.example.taskmangement.Service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping({"/", "/home"})
    public String homePage(HttpSession session, Model model) {
        String userLogin = (String) session.getAttribute("userLogin");
        if (userLogin != null) {
            model.addAttribute("userLogin", userLogin);
            List<TaskModel> userTasks = taskService.getTasksByUser(userLogin);
            model.addAttribute("tasks", userTasks);
            long totalTasks = userTasks.size();
            long completedTasks = userTasks.stream().filter(TaskModel::isCompleted).count();
            long inProgressTasks = userTasks.stream().filter(task -> "progress".equals(task.getStatus())).count();
            long todoTasks = userTasks.stream().filter(task -> "todo".equals(task.getStatus())).count();
            model.addAttribute("totalTasks", totalTasks);
            model.addAttribute("completedTasks", completedTasks);
            model.addAttribute("inProgressTasks", inProgressTasks);
            model.addAttribute("todoTasks", todoTasks);
        }

        return "home";
    }

    // Show login screen
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Handle login form
    @PostMapping("/login")
    public String doLogin(@RequestParam("login") String login,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model) {

        UserModel authenticated = userService.authentication(login, password);

        // If authentication success, create session
        if (authenticated != null) {
            session.setAttribute("userLogin", authenticated.getLogin());
            return "redirect:/home";
        }

        // Otherwise show error message
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // Show register screen
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Handle user registration
    @PostMapping("/register")
    public String doRegister(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("confirmPassword") String confirmPassword,
                             HttpSession session,
                             Model model) {

        // Validate confirm password
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // Attempt to save user
        UserModel saved = userService.register(login, password, email);

        // If user already exists
        if (saved == null) {
            model.addAttribute("error", "Username or email already exists");
            return "register";
        }

        // Auto login after successful registration
        session.setAttribute("userLogin", saved.getLogin());
        return "redirect:/home";
    }

    // Logout user and clear session
    @PostMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Add a new task
    @PostMapping("/add-task")
    public String addTask(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("priority") String priority,
                          @RequestParam("dueDate") String dueDate,
                          @RequestParam("category") String category,
                          HttpSession session) {

        String userLogin = (String) session.getAttribute("userLogin");

        // Only allow task creation if user logged in
        if (userLogin != null) {
            TaskModel task = new TaskModel();
            task.setTitle(title);
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            task.setCategory(category);
            task.setStatus("todo");
            task.setUserLogin(userLogin);

            taskService.saveTask(task);
        }

        return "redirect:/home";
    }

    // Update task status (move between columns)
    @PostMapping("/update-task-status")
    public String updateTaskStatus(@RequestParam("taskId") Integer taskId,
                                   @RequestParam("status") String status) {

        taskService.updateTaskStatus(taskId, status);
        return "redirect:/home";
    }

    // Delete task
    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/home";
    }
}
