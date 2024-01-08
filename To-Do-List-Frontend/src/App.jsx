import { useState } from 'react';
import Task from './components/ToDoComponent/Todo'; // 
import TodoForm from './components/ToDoFormComponent/TodoForm';
import Search from './components/SearchComponent/Search';
import Filter from './components/FilterComponent/Filter';

import './App.css';
import TaskService from './services/TaskService';

function App() {
  const [tasks, setTasks] = useState([
    {
      id: 1,
      description: "criar funcionalidade x no sistema",
      category: "Trabalho",
      isCompleted: false,
    },
    {
      id: 2,
      description: "Ir para academia",
      category: "Pessoal",
      isCompleted: false,
    },
    {
      id: 3,
      description: "Estudar React",
      category: "Estudos",
      isCompleted: false,
    }
  ]);

  const [search, setSearch] = useState("");

  const [filter, setFilter] = useState("All");
  const [sort, setSort] = useState("Asc");

/*   const addTask = (text, category) => {
    const newTasks = [
      ...tasks,
      {
        id: Math.floor(Math.random() * 10000),
        text,
        category,
        isCompleted: false,
      },
    ];

    setTasks(newTasks);
  } */

/*   const removeTask = (id) => {
    const newTasks = [...tasks];
    const filteredTasks = newTasks.filter(task => task.id !== id ? task : null);
    setTasks(filteredTasks);
  } */

  const addTask = async (description, category) => {
    try {
      if(!description || !category){
        console.error('Erro ao adicionar tarefa: Campos obrigatórios não podem ser nulos');
        return;
      }

      const newTask = ({
        description,
        category,
        isCompleted: false,
      });

      const createdTask = await TaskService.createTask(newTask);
      setTasks([...tasks, createdTask]);
    } catch (error) {
      console.error('Erro ao adicionar tarefa:', error.response?.data || error.message);
    }
  }

  const removeTask = async (id) => {
    try {
      await TaskService.deleteTask(id);
      const newTasks = tasks.filter(task => task.id !== id);
      setTasks(newTasks);
    } catch (error) {
      console.error('Erro ao remover a tarefa: ', error )
    }
  }

  const completeTask = (id) => {
    const newTasks = [...tasks];
    newTasks.map((task) => task.id === id ? task.isCompleted = !task.isCompleted : task);
    setTasks(newTasks);
  }

  return (
    <div className='app'>
      <h1>Lista de Tarefas</h1>
      <Search search={search} setSearch={setSearch} />
      <Filter filter={filter} setFilter={setFilter} setSort={setSort} />
      <div className="todo-list">
        {tasks
          .filter((task) =>
            filter === "All"
              ? true
              : filter === "Completed"
                ? task.isCompleted
                : !task.isCompleted)
          .filter((task) =>
            task.description.toLowerCase().includes(search.toLowerCase())
          )
          .sort((a, b) =>
            sort === "Asc"
              ? a.description.localeCompare(b.description)
              : b.description.localeCompare(a.description)
          )
          .map((task) => (
            <Task
              key={task.id}
              task={task}
              removeTask={removeTask}
              completeTask={completeTask} />
          ))}
      </div>
      <TodoForm addTask={addTask} />
    </div>
  );
}

export default App;