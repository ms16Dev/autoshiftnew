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
                    { path: "add-car", name: "add-car", component: AddCarNew }, // /cars/add-car
                    { path: ":id", name: "car-details", component: CarDetails }, // /cars/:id
                ],
            },
            {
                path: "dealers",
                children: [
                    { path: "", name: 'dealers', component: Dealers },
                    { path: ":id", name: 'profile', component: ProfileOverviewVue },
                ],
            },

            { path: 'more', name: 'more', component: More },

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

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
