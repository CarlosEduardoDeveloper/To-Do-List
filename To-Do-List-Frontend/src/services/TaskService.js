import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/tasks';

const api = axios.create({
    baseURL: API_BASE_URL
});

const TaskService = {
    getAllTasks: async () => {
        try {
            const response = await api.get(`${API_BASE_URL}`);
            return response.data;
        } catch (error) {
            throw error;
        }
    },


    getTaskById: async (id) => {
        try {
            const response = await api.get(`${API_BASE_URL}/${id}`);
            return response.data;
        } catch (error) {
            throw error;
        }
    },

    createTask: async (newTask) => {
        try {
            const response = await api.post(`${API_BASE_URL}`, newTask, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    },

    updateTask: async (id, taskUpdated) => {
        try {
            const response = await api.put(`${API_BASE_URL}/${id}`, taskUpdated);
            return response.data;
        } catch (error) {
            throw error;
        }
    },

    deleteTask: async (id) => {
        try {
            const response = await api.delete(`${API_BASE_URL}/${id}`);
            return response.data;
        } catch (error) {
            throw error;
        }
    },

};

export default TaskService;
