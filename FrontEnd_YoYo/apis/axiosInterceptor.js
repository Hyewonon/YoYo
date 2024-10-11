import { axiosInstance } from "./axiosInstance";

export function axiosInterceptor(AuthCtx) {
    axiosInstance.interceptors.request.use(
        (config) => {
            if (AuthCtx.token) {
                config.headers.Authorization = `Bearer ${AuthCtx.token}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );
}
