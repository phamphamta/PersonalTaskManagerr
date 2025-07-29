package main;

import model.Task;

import util.TaskValidator;

import repository.TaskRepository;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import java.time.LocalDate;

import java.time.format.DateTimeParseException;


public class PersonalTaskManager {


public JSONObject addTask(String title, String desc, String dueDateStr, String priority) {

if (!TaskValidator.isValidTitle(title)) {

System.out.println("Lỗi: Tiêu đề không được để trống.");

return null;

}


LocalDate dueDate;

try {

dueDate = TaskValidator.parseDueDate(dueDateStr);

} catch (DateTimeParseException e) {

System.out.println("Lỗi định dạng ngày.");

return null;

}


if (!TaskValidator.isValidPriority(priority)) {

System.out.println("Lỗi mức độ ưu tiên.");

return null;

}


JSONArray tasks = TaskRepository.load();

for (Object obj : tasks) {

JSONObject task = (JSONObject) obj;

if (task.get("title").equals(title) && task.get("due_date").equals(dueDateStr)) {

System.out.println("Trùng lặp nhiệm vụ.");

return null;

}

}


Task newTask = new Task(title, desc, dueDate, priority);

tasks.add(newTask.toJson());

TaskRepository.save(tasks);

System.out.println("Đã thêm nhiệm vụ.");

return newTask.toJson();

}


public static void main(String[] args) {

PersonalTaskManager manager = new PersonalTaskManager();

manager.addTask("Mua sách", "Java", "2025-07-20", "Cao");

manager.addTask("", "Không tiêu đề", "2025-07-20", "Thấp");

}

}
