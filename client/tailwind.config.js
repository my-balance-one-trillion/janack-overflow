/** @type {import('tailwindcss').Config} */
module.exports = {
    //   purge: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
        "./node_modules/flowbite-vue/**/*.js",
        "./node_modules/flowbite/**/*.js",
    ],
    theme: {
        extend: {
            colors: {
                'main-red': '#BF1131',
                'sub-red': '#BF6071',
                'main-grn': '#025939',
                'sub-grn': '#B0D9C6',
                'bg-grey': '#F0F2F2',
                'btn-grey': '#888888',
                'hover-red': '#E21C3B',
                'hover-grn': '#007F4D',
                'main-sm-text': '#AAB4DA',
                'white': '#ffffff',
                'black': '#000000',
                'badge-bg': '#F6F2F2'
            },
            fontFamily: {
                'main': 'GongGothicMedium',
                'sub': 'D2Coding',
            }
        },
    },
    plugins: [
        require("flowbite/plugin")({
            charts: true,
        }),
    ],
    env: {
        node: true,
        'vue/setup-compiler-macros': true,
    },
};