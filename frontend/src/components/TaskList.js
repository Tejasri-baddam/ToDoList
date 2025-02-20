import { useState, useEffect } from "react";
import { getTaskToBeDone, deleteDoneTask } from "../services/TaskServices";
import TaskForm from "./TaskForm";
import TaskItem from "./TaskItem";
import "../styling/TaskList.css";

const TaskList = () => {
  const [tasks, setTasks] = useState([]);
  const [taskToEdit, setTaskToEdit] = useState(null);
  const [showForm, setShowForm] = useState(false); 

  // Fetch tasks on mount
  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    const data = await getTaskToBeDone();
    setTasks(data);
  };

  // Handle adding a new task
  const handleTaskAdded = (newTask) => {
    setTasks([...tasks, newTask]);
    setTaskToEdit(null);
  };

  // Handle updating a task
  const handleEdit = (task) => {
    setTaskToEdit(task); 
    setShowForm(true);
  };

  // Handle edit completion
  const handleEditComplete = (updatedTask) => {
    setTasks(tasks.map((task) => (task.id === updatedTask.id ? updatedTask : task)));
    setTaskToEdit(null); 
  };

  // Handle deleting a task
  const handleDelete = async (id) => {
    await deleteDoneTask(id);
    setTasks(tasks.filter((task) => task.id !== id));
  };

  const closeForm = () => {
    setShowForm(false); 
  }

  return (
    <div className="task-container">
      <h1>Welcome to Task Manager</h1>
      <p>Your personal assistant for managing tasks effectively!</p>
      <button className="add-btn" onClick={() => setShowForm(true)}>Add Task</button>
      {showForm && <TaskForm onTaskAdded={handleTaskAdded} taskToEdit={taskToEdit} onEditComplete={handleEditComplete} closeForm={closeForm} />}
      
      <div className="task-list">
        {tasks.length === 0 ? (
          <p>No tasks available. Please add a task!</p>
        ) : (
          tasks.map((task) => (
            <TaskItem key={task.id} task={task} onDelete={handleDelete} onEdit={handleEdit} />
          ))
        )}
      </div>
    </div>
  );
};

export default TaskList;
