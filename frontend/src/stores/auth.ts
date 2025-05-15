import { defineStore } from "pinia";
import { ref } from "vue";
import ApiService from "../core/services/ApiService";
import type { UserDto } from "../core/models/UserDto";
import type { UserRegisterDto } from "../core/models/UserRegisterDto";
import type { UserLoginDto } from "../core/models/UserLoginDto";

export const useAuthStore = defineStore(
    "auth",
    () => {
        // Define the refs
        const errors = ref<Record<string, any>>({});
        const isAuthenticated = ref<boolean>(false);
        const userInfo = ref<UserDto | null>(null);

        // Set authentication details
        const setAuth = (authUser: UserDto) => {
            isAuthenticated.value = true;
            userInfo.value = authUser;
            errors.value = {}; // Reset errors on successful login
        };

        // Set error messages
        const setError = (error: any) => {
            errors.value = { ...error }; // Spread the error object to avoid direct mutation
        };

        // Manually reset the store state
        const resetStore = () => {
            errors.value = {};
            isAuthenticated.value = false;
            userInfo.value = null;
        };

        // Reset authentication details
        const purgeAuth = () => {
            isAuthenticated.value = false;
            userInfo.value = null;
            errors.value = {}; // Reset errors on logout
        };

        // Login logic
        const login = async (credentials: UserLoginDto) => {
            try {
                const response = await ApiService.post("auth/login", credentials, {
                    withCredentials: true,
                });


                setAuth(response.data);
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
                throw error; // This ensures the error is propagated
            }
        };

        // Logout logic
        const logout = async () => {
            try {
                await ApiService.post("auth/logout", {});
                purgeAuth();
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        // Register logic
        const register = async (credentials: UserRegisterDto) => {
            try {
                console.log("Registering with:", credentials);
                await ApiService.post("auth/register", credentials);
            } catch (error: any) {
                console.error("Error during registration:", error);
                setError(error.response?.data?.errors || {});
                throw error;  // Ensure the error is thrown after setting the error
            }
        };

        // Forgot password logic
        const forgotPassword = async (email: string) => {
            try {
                await ApiService.post("auth/forgot-password", { email });
                setError({}); // Clear errors after successful operation
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        // Update password logic
        const updatePassword = async (password: string, token: string) => {
            try {
                await ApiService.post("auth/reset-password", { password, token });
                setError({}); // Clear errors after successful operation
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        // Check session status logic
        const checkSession = async () => {
            try {
                const { data } = await ApiService.get1("/me");
                if (data) {
                    setAuth(data);
                } else {
                    purgeAuth();
                }
            } catch (error) {
                purgeAuth(); // Reset state on error
            }
        };

        // Fetch public key logic
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

        // Role-based authorization check
        const hasRole = (role: string) => {
            return userInfo.value?.roles.includes(role) ?? false;
        };

        // Check for any of the roles
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
            fetchPublicKey,
            resetStore
        };
    },
    {
        persist: false
    }
);
