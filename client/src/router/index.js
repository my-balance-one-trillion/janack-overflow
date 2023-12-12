import { createRouter, createWebHistory } from "vue-router";

import Main from "@/pages/Main.vue";
import Admin from "@/pages/Admin.vue";
import Login from "@/pages/Login.vue";
import Saving from "@/pages/Saving.vue";
import SignUp from "@/pages/SignUp.vue";
import Community from "@/pages/Community.vue";

const routes = [
  { path: "/", component: Main },
  { path: "/admin", component: Admin },
  { path: "/login", component: Login },
  { path: "/saving", component: Saving },
  { path: "/signup", component: SignUp },
  { path: "/community", component: Community },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
