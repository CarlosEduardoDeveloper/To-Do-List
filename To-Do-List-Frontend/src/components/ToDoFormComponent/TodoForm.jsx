import { useState } from 'react';
import styles from './TodoForm.module.css';

const TodoForm = ({addTask} ) => {
    const [value, setValue] = useState("");
    const [category, setCategory] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!value || !category) return;
        addTask(value, category);
        setValue("");
        setCategory("");
    }

    return <div className="todo-form">
        <h2 className={styles.title}>Criar tarefa:</h2>
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder='Digite o título'
                value={value}
                onChange={(e) => setValue(e.target.value)} />
            <select  value={category} onChange={(e) => setCategory(e.target.value)}>
                <option value="">Selectione uma categoria</option>
                <option value="Trabalho">Trabalho</option>
                <option value="Pessoal">Pessoal</option>
                <option value="Estudos">Estudos</option>
            </select>
            <button type='submit'>Criar tarefa</button>
        </form>
    </div>
}

export default TodoForm;