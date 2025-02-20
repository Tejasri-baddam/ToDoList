const API_URL = "http://localhost:8080/todolist";

// Get all tasks
export const getTaskToBeDone = async () => {
    const response = await fetch(API_URL);
    return response.json();
};

//Get task by Id
export const getTaskById = async (id) =>{
    const response = await fetch(`${API_URL}/${id}`);
    return response.json();
}

// Add a new task 
export const addTaskToList = async (taskToBeDone) => {
  try{
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ taskToBeDone, taskcompleted: true }),
    });

    if (!response.ok) {
      throw new Error("Failed to add task");
    }
    return await response.json(); 
  }
  catch (error) {
    console.error("Error adding task:", error);
    return null;
  }
};

//Update a existing task
export const updateExistingTask = async (id,taskToBeDone) =>{
    const response = await fetch(`${API_URL}/${id}`,{
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ taskToBeDone, taskcompleted: true })
    });
    if (!response.ok) {
      throw new Error("Failed to update task");
    }
  
  return response.json();
}

// Delete task (DELETE)
export const deleteDoneTask = async (id) => {
  await fetch(`${API_URL}/${id}`, { method: "DELETE" });
};
