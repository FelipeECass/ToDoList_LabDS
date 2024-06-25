import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrash } from '@fortawesome/free-solid-svg-icons'
import React from 'react'

export const TodoList = ({ id, title, status }) => {
    return (
        <div className="Todo">
            <p className={id}>{id}</p>
            <p className={title}>{title}</p>
            <p className={status}>{status}</p>
        </div>
    )
}