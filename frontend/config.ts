// src/config.ts
const env = {
    VITE_API_BASE_URL: import.meta.env.VITE_API_BASE_URL
};

export const config = {
    apiBaseUrl: env.VITE_API_BASE_URL,

    endpoints: {
        images: `${env.VITE_API_BASE_URL}/media/image`,
        api: `${env.VITE_API_BASE_URL}/api/v1`
    },

    getImageUrl: (path: string) => {
        if (path.startsWith('http')) return path;
        return `${env.VITE_API_BASE_URL}${path}`;
    }
};