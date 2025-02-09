import type { App } from "vue";
import axios, { AxiosInstance } from "axios";

/**
 * @description service to call HTTP requests via Axios
 */
class ApiService {
  /**
   * @description Axios instance used by the service
   */
  private static axiosInstance: AxiosInstance;

  /**
   * @description initialize the Axios instance and provide it to the Vue app
   * @param app Vue application instance
   */
  public static init(app: App<Element>): void {
    // Create Axios instance with base URL
    ApiService.axiosInstance = axios.create({
      baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080/",
    });

    // Debug logging
    console.log("Base URL:", ApiService.axiosInstance.defaults.baseURL);

    // Provide Axios instance globally
    app.provide("axios", ApiService.axiosInstance);
  }

  /**
   * @description Set default HTTP request headers
   */
  public static setHeader(): void {
    ApiService.axiosInstance.defaults.headers.common["Accept"] = "application/json";
  }

  /**
   * @description Send a GET HTTP request with query parameters
   * @param resource API endpoint
   * @param params Request parameters
   * @returns Axios response promise
   */
  public static query(resource: string, params: any) {
    return ApiService.axiosInstance.get(resource, { params });
  }

  /**
   * @description Send a GET HTTP request
   * @param resource API endpoint
   * @param slug Additional path segment
   * @returns Axios response promise
   */
  public static get(resource: string, slug = "") {
    return ApiService.axiosInstance.get(`${resource}/${slug}`);
  }

  /**
   * @description Send a POST HTTP request
   * @param resource API endpoint
   * @param params Request body
   * @param config
   * @returns Axios response promise
   */
  public static post(resource: string, params: any, config: object = {}) {
    return ApiService.axiosInstance.post(resource, params, config);
  }

  /**
   * @description Send an UPDATE (PUT) HTTP request
   * @param resource API endpoint
   * @param slug Additional path segment
   * @param params Request body
   * @returns Axios response promise
   */
  public static update(resource: string, slug: string, params: any) {
    return ApiService.axiosInstance.put(`${resource}/${slug}`, params);
  }

  /**
   * @description Send a DELETE HTTP request
   * @param resource API endpoint
   * @returns Axios response promise
   */
  public static delete(resource: string) {
    return ApiService.axiosInstance.delete(resource);
  }
}

export default ApiService;
