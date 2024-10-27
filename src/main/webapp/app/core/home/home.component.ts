import { type ComputedRef, defineComponent, inject, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRouter } from 'vue-router';

import type LoginService from '@/account/login.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const router = useRouter();

    const loginService = inject<LoginService>('loginService');

    // Inyectar referencias computadas para autenticación y nombre de usuario
    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');

    // Asegúrate de que authenticated no sea undefined antes de usarlo
    watch(authenticated!, (newValue: boolean) => {
      if (newValue && loginService) {
        const userRole = loginService.getUserRole();

        if (userRole === 'ROLE_USER') {
          const account = loginService?.getAccount();

          if (!account) return router.push({ name: 'Home' });

          router.push(`/oficina-user/${account.id}/home`);
        } else if (userRole === 'ROLE_ADMIN') {
          router.push({ name: 'Home' });
        }
      }
    });

    const openLogin = () => {
      if (loginService) loginService.openLogin();
    };

    return {
      t$: useI18n().t,

      authenticated,
      username,

      openLogin,
    };
  },
});
