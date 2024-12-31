// src/router/index.js
import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import Home from "../views/Home.vue";
import Cars from "../views/cars/ListCars.vue";
import Parts from "../views/parts/ListParts.vue";
import Services from "../views/services/ListServices.vue";
import More from "../views/More.vue";
import AddCar from "../views/cars/AddCar.vue";
import AddCarNew from "../views/cars/AddCarNew.vue";
import AddPart from "../views/parts/AddPart.vue";
import AddService from "../views/services/AddService.vue";
import SignUp from "../views/auth/SignUp.vue";
import Landing from "../views/Landing.vue";
import SignIn from "../views/auth/SignIn.vue";
import ForgotPassword from "../views/auth/ForgotPassword.vue";
import VerifyEmail from "../views/auth/VerifyEmail.vue";
import ResetPassword from "../views/auth/ResetPassword.vue";
import OtpVerify from "../views/auth/OtpVerify.vue";


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
            { path: 'home', name: 'Home', component: Home },
            { path: 'cars', name: 'Cars', component: Cars },
            { path: 'parts', name: 'Parts', component: Parts },
            { path: 'services', name: 'Services', component: Services },
            { path: 'more', name: 'More', component: More },
            { path: 'add-car', name: 'Add Car', component: AddCar },
            { path: 'add-car-new', name: 'Add Car New', component: AddCarNew },
            { path: 'add-part', name: 'Add Part', component: AddPart },
            { path: 'add-service', name: 'Add Service', component: AddService },
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
