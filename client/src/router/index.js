import {createRouter, createWebHistory} from "vue-router";

import Main from "@/pages/Main.vue";
import Admin from "@/pages/Admin.vue";
import Login from "@/pages/Login.vue";
import Saving from "@/pages/Saving.vue";
import SignUp from "@/pages/SignUp.vue";
import Community from "@/pages/Community.vue";
import Mypage from "@/pages/Mypage.vue";
import SavingDetails from "@/components/Saving/SavingDetails.vue";
import OpenAccount from "@/components/Saving/OpenAccount.vue";
import SavingEdit from "@/components/Saving/SavingEdit.vue";
import Intro from "@/components/Saving/Intro.vue";
import OpenAccountFinish from "@/components/Saving/OpenAccountFinish.vue";
import SavingDelete from "@/components/Saving/SavingDelete.vue";
import SavingRecord from "../components/Saving/SavingRecord.vue";

const routes = [
    {path: "/", component: Main},
    {path: "/admin", component: Admin},
    {path: "/login", component: Login},
    {path: "/saving", component:Saving,
        children:[
            {path:'details', component: SavingDetails},
            {path:'record', component: SavingRecord},
            {path:'edit', component: SavingEdit},
            {path:'delete', component: SavingDelete},
        ]
    },
    {path:"/intro", component: Intro},
    {path: "/open", component: OpenAccount},
    {path: "/open/finish", component: OpenAccountFinish},
    {path: "/signup", component: SignUp},
    {path: "/community", component: Community},
    {path: "/mypage", component: Mypage},
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
