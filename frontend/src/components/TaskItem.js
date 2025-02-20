import "../styling/TaskItem.css";

const TaskItem = ({ task, onDelete, onEdit }) => {
  return (
    <div className="task-item">
      <div className="task-content">
        <p>{task.taskToBeDone}</p>
      </div>

      <div className="task-actions">
        <button className="edit-btn" onClick={() => onEdit(task)}>Edit</button>
        <button className="delete-btn" onClick={() => onDelete(task.id)}>Delete</button>
      </div>
    </div>
  );
};

export default TaskItem;
