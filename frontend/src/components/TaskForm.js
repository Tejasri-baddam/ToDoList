import { useState , useEffect} from "react";
import { addTaskToList,updateExistingTask } from "../services/TaskServices";
import "../styling/TaskForm.css";

const TaskForm = ({ onTaskAdded, taskToEdit, onEditComplete, closeForm }) => {
    const [task, setTask] = useState(taskToEdit ? taskToEdit.taskToBeDone : "");

  useEffect(() => {
    setTask(taskToEdit ? taskToEdit.taskToBeDone : "");
  }, [taskToEdit]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (task.trim()) {
      if (taskToEdit) {
        const updatedTask = await updateExistingTask(taskToEdit.id, task); // Call API to update task
        onEditComplete(updatedTask); 
      } else { 
        const newTask = await addTaskToList(task); // Call API to add task
        onTaskAdded(newTask); 
      }
      setTask(""); // Reset input field
      setTimeout(() => closeForm(), 100); 
    }
  };

  return (
    <form onSubmit={handleSubmit} className="task-form">
      <input
        type="text"
        value={task}
        onChange={(e) => setTask(e.target.value)}
        placeholder="Enter a task..."
      />
      <button type="submit">{taskToEdit ? "Update" : "Add"}</button>
      <button type="button" onClick={closeForm}>Cancel</button>
    </form>
  );
};

export default TaskForm;