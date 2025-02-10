import type { RouteMeta } from 'vue-router';

declare module 'my-vue-router' {
    interface RouteMeta {
        requiresAuth?: boolean;
        roles?: string[];
    }
}
