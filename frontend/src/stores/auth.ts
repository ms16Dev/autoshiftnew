import { defineStore } from "pinia";
import { ref } from "vue";
import ApiService from "../core/services/ApiService";
import type { UserDto } from "../core/models/UserDto";
import type { UserRegisterDto } from "../core/models/UserRegisterDto";
import type { UserLoginDto } from "../core/models/UserLoginDto";

export const useAuthStore = defineStore(
    "auth",
    () => {
        const errors = ref<Record<string, any>>({});
        const isAuthenticated = ref<boolean>(false);
        const userInfo = ref<UserDto | null>(null);

        const setAuth = (authUser: UserDto) => {
            isAuthenticated.value = true;
            userInfo.value = authUser;
            errors.value = {};
        };

        const setError = (error: any) => {
            errors.value = { ...error };
        };

        const purgeAuth = () => {
            isAuthenticated.value = false;
            userInfo.value = null;
            errors.value = {};
        };

        const login = async (credentials: UserLoginDto) => {
            try {
                const response = await ApiService.post("auth/login", credentials, {
                    withCredentials: true,  // Ensures cookies are sent if needed
                });

                // Convert headers to lowercase keys for reliable access
                const headers = response.headers;
                console.log("Headers:", headers);
                const authToken = headers["x-auth-token"] || headers["X-Auth-Token"] || headers["X_AUTH_TOKEN"];

                if (authToken) {
                    // Store the token in localStorage
                    localStorage.setItem("X-AUTH-TOKEN", authToken);
                    console.log("Token stored:", authToken);
                } else {
                    console.warn("No auth token received!");
                }

                // Set authentication state
                setAuth(response.data);
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };



        const logout = async () => {
            try {
                await ApiService.post("auth/logout", {});
                purgeAuth();
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        const register = async (credentials: UserRegisterDto) => {
            try {
                console.log("Registering with:", credentials);
                await ApiService.post("auth/register", credentials);
            } catch (error: any) {
                console.error("Error during registration:", error);
                setError(error.response?.data?.errors || {});
                throw error;  // <-- This line is required
            }
        };

        const forgotPassword = async (email: string) => {
            try {
                await ApiService.post("auth/forgot-password", { email });
                setError({});
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        const updatePassword = async (password: string, token: string) => {
            try {
                await ApiService.post("auth/reset-password", { password, token });
                setError({});
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        /** Check if the session is still valid and update the store */
        const checkSession = async () => {
            try {
                const { data } = await ApiService.get1("/me");
                if (data) {
                    setAuth(data);
                } else {
                    purgeAuth();
                }
            } catch (error) {
                purgeAuth();
            }
        };


        /** Fetch the public key from the backend */
        const fetchPublicKey = async (): Promise<string> => {
            try {
                const { data } = await ApiService.get1("auth/public-key");
                console.log("Public Key from backend:", data); // Debugging line
                return data;
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
                throw new Error("Failed to fetch public key");
            }
        };


        /** Role-based authorization */
        const hasRole = (role: string) => {
            return userInfo.value?.roles.includes(role) ?? false;
        };

        const hasAnyRole = (roles: string[]) => {
            return roles.some((role) => userInfo.value?.roles.includes(role));
        };

        return {
            errors,
            isAuthenticated,
            userInfo,
            login,
            logout,
            register,
            forgotPassword,
            updatePassword,
            checkSession,
            hasRole,
            hasAnyRole,
            fetchPublicKey
        };
    },
    {
        persist: true, // Persist authentication state
    }
);
