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
        const isAuthenticated = ref<boolean>(false); // We will rely on session cookies now
        const userInfo = ref<UserDto>({
            id: 1,
            username: "John",
            email: "john.doe@example.com",
            active: true,
            roles: [
                "ROLE_USER",
                "ROLE_ADMIN"
            ],
        });

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
            errors.value = {};
            // No need to manage tokens with Redis session
        };

        const login = async (credentials: UserLoginDto) => {
            try {
                // Send the credentials to backend for authentication
                const { data } = await ApiService.post("auth/login", credentials, {
                    withCredentials: true // Ensure cookies (session) are sent with the request
                });

                // Backend manages session and sets the session cookie automatically
                setAuth(data);
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        const logout = async () => {
            try {
                await ApiService.post("auth/logout", {}); // Call to logout endpoint
                purgeAuth();
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
            }
        };

        const register = async (credentials: UserRegisterDto) => {
            try {
                await ApiService.post("auth/signup", credentials);
            } catch (error: any) {
                setError(error.response?.data?.errors || {});
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

        return {
            errors,
            isAuthenticated,
            userInfo,
            login,
            logout,
            register,
            forgotPassword,
            updatePassword,
        };
    },
    {
        persist: true, // Enable persistence (you can persist `userInfo`, etc.)
    }
);
