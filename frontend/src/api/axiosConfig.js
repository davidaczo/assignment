import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    timeout: 100000,
});

export default axiosInstance;
