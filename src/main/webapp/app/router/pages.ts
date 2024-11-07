import { Authority } from '@/shared/security/authority';

const OficinaUserCreate = () => import('@/pages/oficina/oficina-user-create.vue');
const OficinaUserHome = () => import('@/pages/oficina/oficina-user-home.vue');
const OficinaUserAnswerPqrs = () => import('@/pages/oficina/oficina-user-answer-pqrs.vue');

export default [
  {
    path: '/oficina-user/create',
    name: 'OficinaUserCreate',
    component: OficinaUserCreate,
    meta: { authorities: [Authority.ADMIN] },
  },

  {
    path: '/oficina-user/:userId/home',
    //path: '/oficina-user/home',
    name: 'OficinaUserHome',
    component: OficinaUserHome,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/oficina-user/:userId/home/:pqrsId/answer-pqrs',
    name: 'OficinaUserAnswerPqrs',
    component: OficinaUserAnswerPqrs,
    meta: { authorities: [Authority.USER] },
  },
];
