import React, { useEffect, useState } from 'react';
import { TodoForm } from './TodoForm';
import { TodoList } from './TodoList';
import { v4 as uuidv4 } from "uuid";
import TodoWrapperService from './services/TodoWrapperService'

export const TodoWrapper = () => {
    const [todos, setTodos] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await TodoWrapperService();
                response.data.forEach((item) => {
                    if (item.taskTitle) {
                        addTodo(item.taskId, item.taskTitle, item.TaskStatus);
                    }
                });
            } catch (error) {
                console.error('Error fetching tasks:', error);
            }
        }
        fetchData();
    }, [])

    const addTodo = (id, title, status) => {
        setTodos([
            ...todos,
            { id: id, title: title, completed: status },
        ]);
    }

    return (
        <div className='TodoWrapper'>
            <h1>Lista de Tarefas</h1>
            <TodoForm addTodo={addTodo} />
            {todos.map((todo) =>
                <TodoList
                    id={todo.id}
                    title={todo.title}
                    status={todo.completed}
                />
            )
            }
        </div>
    );
}