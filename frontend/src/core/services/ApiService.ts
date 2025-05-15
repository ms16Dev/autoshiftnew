import type { App } from "vue";
import axios, { AxiosInstance } from "axios";

class ApiService {
  private static axiosInstance: AxiosInstance;

  public static init(app: App<Element>): void {
    // Create Axios instance with base URL
    ApiService.axiosInstance = axios.create({
      baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080/",
      withCredentials: true, // Important for session-based authentication
    });

    // Debug logging
    console.log("Base URL:", ApiService.axiosInstance.defaults.baseURL);

    // Provide Axios instance globally
    app.provide("axios", ApiService.axiosInstance);

    // Attach Interceptors
    ApiService.setInterceptors();
  }

  public static setHeader(): void {
    ApiService.axiosInstance.defaults.headers.common["Accept"] = "application/json";
  }

  public static query(resource: string, params: any) {
    return ApiService.axiosInstance.get(resource, { params });
  }

  public static get(resource: string, slug = "") {
    return ApiService.axiosInstance.get(`${resource}/${slug}`);
  }
  public static get1(resource: string) {
    return ApiService.axiosInstance.get(`${resource}`);
  }

  public static post(resource: string, params: any, config: object = {}) {
    return ApiService.axiosInstance.post(resource, params, config);
  }

  public static update(resource: string, slug: string, params: any) {
    return ApiService.axiosInstance.put(`${resource}/${slug}`, params);
  }

  public static delete(resource: string) {
    return ApiService.axiosInstance.delete(resource);
  }

  private static setInterceptors() {
    // Request Interceptor - Attach `X-AUTH-TOKEN` to every request
    ApiService.axiosInstance.interceptors.request.use(
        (config) => {

          return config;
        },
        (error) => Promise.reject(error)
    );

    // Response Interceptor - Handle 401 errors (session expiration)
    ApiService.axiosInstance.interceptors.response.use(
        (response) => response,
        (error) => {
          try {
            const fullUrl = new URL(error.config?.url, window.location.origin); // works even if url is relative
            const path = fullUrl.pathname;

            const isLoginRequest = path === "/auth/login";

            if (error.response?.status === 401 && !isLoginRequest) {
              alert("Your session has expired. Please log in again.");
              window.location.href = "/auth/sign-in";
            }
          } catch (e) {
            console.warn("Interceptor error URL parse failed", e);
          }

          return Promise.reject(error);
        }
    );



  }

}

export default ApiService;
