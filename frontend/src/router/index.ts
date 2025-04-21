// src/router/index.js
import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import Home from "../views/Home.vue";
import Cars from "../views/cars/ListCars.vue";
import CarDetails from "../views/cars/CarDetail.vue";
import Dealers from "../views/dealers/ListDealers.vue";
import More from "../views/More.vue";
import AddCarNew from "../views/cars/AddCarNew.vue";
import SignUp from "../views/auth/SignUp.vue";
import Landing from "../views/landing/Landing.vue";
import SignIn from "../views/auth/SignIn.vue";
import ForgotPassword from "../views/auth/ForgotPassword.vue";
import VerifyEmail from "../views/auth/VerifyEmail.vue";
import ResetPassword from "../views/auth/ResetPassword.vue";
import OtpVerify from "../views/auth/OtpVerify.vue";
import ProfileOverviewVue from '../views/profile/ProfileOverview.vue';
import NotFound from "../views/NotFound.vue";
import Admin from "../views/admin/Admin.vue";
import Users from "../views/admin/Users.vue";
import Roles from "../views/admin/Roles.vue";
import Makes from "../views/admin/Makes.vue";
import Classes from "../views/admin/Classes.vue";
import Engines from "../views/admin/Engines.vue";
import Fuel from "../views/admin/Fuel.vue";
import {useAuthStore} from "../stores/auth.ts";


const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        component: () => import("../layouts/LandingLayout.vue"),
        children:     [
            { path: '', name: 'Landing', component: Landing },
          ]
    },
    {
        path: "/",
        component: () => import("../layouts/DefaultLayout.vue"),
        children:     [
            { path: 'home', name: 'home', component: Home },

            {
                path: "cars",
                children: [
                    { path: "", name: "cars", component: Cars }, // /cars (default list page)
                    { path: "list", name: "cars-list", component: Cars }, // /cars/list
                    { path: "add-car",
                        name: "add-car",
                        component: AddCarNew,
                        meta: { requiresAuth: true },

                    }, // /cars/add-car
                    { path: ":id", name: "car-details", component: CarDetails }, // /cars/:id
                ],
            },
            {
                path: "dealers",
                children: [
                    { path: "", name: 'dealers', component: Dealers },
                    {
                        path: ":id",
                        name: 'profile',
                        component: ProfileOverviewVue,
                        meta: { requiresAuth: true },
                    },
                ],
            },

            { path: 'more', name: 'more', component: More },
            {
                path: '/:pathMatch(.*)*',  // This is the wildcard path for unmatched routes
                name: 'NotFound',
                component: NotFound,
            },

         ]
    },

    {
        path: "/auth",
        component: () => import("../layouts/AuthLayout.vue"),
        children:     [
            { path: 'sign-up', name: 'Sign Up', component: SignUp },
            { path: 'sign-in', name: 'Sign In', component: SignIn },
            { path: 'forgot-password', name: 'Forgot Password', component: ForgotPassword },
            { path: 'verify-email', name: 'Verify Email', component: VerifyEmail },
            { path: 'reset-password', name: 'Reset Password', component: ResetPassword },
            { path: 'otp-verify', name: 'OTP Verify', component: OtpVerify },

        ]
    },
    {
        path: "/admin",
        component: () => import("../layouts/AdminLayout.vue"),
        children:     [
            { path: '', name: 'Admin', component: Admin },
            { path: 'users', name: 'Users', component: Users },
            { path: 'roles', name: 'Roles', component: Roles },
            { path: 'makes', name: 'Makes', component: Makes },
            { path: 'classes', name: 'Classes', component: Classes },
            { path: 'engines', name: 'Engines', component: Engines },
            { path: 'fuels', name: 'Fuels', component: Fuel },


        ]
    },


];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from, next) => {
    console.log("Navigating from:", from.path);
    console.log("Navigating to:", to.path);

    const authStore = useAuthStore();

    try {
        await authStore.checkSession(); // Ensure session is fresh
    } catch (e) {
        console.warn("Session check failed:", e);
        // Already purged inside the store
    }

    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        return next("/auth/sign-in");
    } else if (Array.isArray(to.meta.roles) && !authStore.hasAnyRole(to.meta.roles)) {
        return next("/home"); // Redirect unauthorized users
    } else {
        return next();
    }
});


export default router;
