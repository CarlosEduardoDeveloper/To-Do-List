import React from 'react';

const Todo = ({ task, removeTask, completeTask }) => {
    return (
        <div className="todo" style={{textDecoration: task.isCompleted ? "line-through" : "" }}>
            <div className="content">
                <p>{task.description}</p>
                <p className="category">{(task.category)}</p>
            </div>
            <div>
                <button className='complete' onClick={() => completeTask(task.id)}>Completar</button>
                <button className='remove' onClick={() => removeTask(task.id)}>
                    x
                </button>
            </div>
        </div>
    );
};

export default Todo;