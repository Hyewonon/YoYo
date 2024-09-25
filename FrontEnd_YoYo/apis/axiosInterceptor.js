import { axiosInstance } from "./axiosInstance";

export function axiosInterceptor(authCtx) {
    axiosInstance.interceptors.request.use(
        (config) => {
            const token = authCtx.token; // 현재 토큰 가져오기
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );
}
