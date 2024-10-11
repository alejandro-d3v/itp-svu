/* tslint:disable */
// prettier-ignore

import { Authority } from '@/shared/security/authority';

const OficinaUserCreate = () => import('@/pages/oficina/oficina-user-create.vue');

export default [
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  {
    path: '/oficina-user/create',
    name: 'OficinaUserCreate',
    component: OficinaUserCreate,
    meta: { authorities: [Authority.ADMIN] },
  },
];
