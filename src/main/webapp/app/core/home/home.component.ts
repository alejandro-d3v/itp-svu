import { type ComputedRef, defineComponent, inject, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRouter } from 'vue-router';
import type LoginService from '@/account/login.service';
import { type IUser, User } from '@/shared/model/user.model';
import { useAccountStore } from '@/shared/config/store/account-store';
import UserManagementService from '@/admin/user-management/user-management.service';
import { useAlertService } from '@/shared/alert/alert.service';
import { type Ref, ref, onMounted } from 'vue';

//import { IUser, User } from '@/shared/model/user.model'; // Importa el modelo de usuario si es necesario

export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const loginService = inject<LoginService>('loginService');
    //const userManagementService = inject<UserManagementService>('userManagementService');
    const userManagementService = inject('userManagementService', () => new UserManagementService(), true);

    const accountStore = useAccountStore();
    const router = useRouter();

    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');

    const openLogin = () => {
      loginService.openLogin();
    };

    // Verificar y redirigir cuando el estado de autenticación cambie
    watch(authenticated, newValue => {
      if (newValue) {
        const userRole = loginService.getUserRole();
        console.log(username.value);

        const login = username.value;
        //if (login) {
        //  this.user =retrieveUser(login); // Hacer la llamada con el ID calculado
        //console.log(this.user);
        // }

        if (userRole === 'ROLE_USER') {
          //enviando el id del usuario si encuentra la oficina asociada a el
          router.push('/oficina-user/671407ba9b3c51683d40eec1/home'); //trampa
          //router.push(`/oficina-user/${this.user.id}/home`);
        } else if (userRole === 'ROLE_ADMIN') {
          router.push({ name: 'Home' }); // Redirige a la página de admin
        }
      }
    });

    const retrieveUser = async (login: string) => {
      try {
        const user = await userManagementService().getUserByLogin(login);
        console.log(user); // Aquí puedes almacenar el usuario o hacer algo con él
      } catch (error) {
        //alertService.showHttpError(error.response);
      }
    };

    return {
      authenticated,
      username,
      openLogin,
      t$: useI18n().t,
    };
  },
});

/*
import { type ComputedRef, defineComponent, inject, watch, Ref, ref } from 'vue';
//import { type ComputedRef, defineComponent, inject, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRouter } from 'vue-router';
//import { User } from '@/shared/model/user.model';
import { type IUser, User } from '@/shared/model/user.model';
import type LoginService from '@/account/login.service';
import { useAccountStore } from '@/shared/config/store/account-store';
import { useAlertService } from '@/shared/alert/alert.service';
import  UserManagementService  from '@/admin/user-management/user-management.service'; 
//import UserManagementService from './user-management.service';


export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const loginService = inject<LoginService>('loginService');
    const userAccount: Ref<IUser> = ref({ ...new User(), authorities: [] });
    const userManagementService = inject('userManagementService', () => new UserManagementService(), true);
    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');
    const authorities: Ref<string[]> = ref([]);
    const user: Ref<IUser> = ref({ ...new User()});
    const accountStore = useAccountStore();
    const router = useRouter();
    const alertService = inject('alertService', () => useAlertService(), true);

    const initAuthorities = async () => {
      const response = await userManagementService.retrieveAuthorities();
      authorities.value = response.data;
    };

    const loadUser = async (userId: string) => {
      const response = await userManagementService.get(userId);
      user.value = response.data;
    };
    

    const retrieveUser = async (login: string) =>{
        const response = await userManagementService.getUserByLogin(login); // Obtener usuario por login
        user.value = response.data;
        console.log(user.value);
       
      };
    

    const openLogin = () => {
      loginService.openLogin();
    };

    watch(authenticated, async (newValue) => {
      if (newValue) {
        const userRole = loginService.getUserRole();
        const login = username.value; 

        this.user = this.loadUser();
        
       // console.log(login);

       if (userRole === 'ROLE_USER') {
        //enviando el id del usuario si encuentra la oficina asociada a el
        router.push("/oficina-user/671407ba9b3c51683d40eec1/home");
        //router.push(`/oficina-user/${username.value}/home`);
      } else if (userRole === 'ROLE_ADMIN') {
        router.push({ name: 'Home' }); // Redirige a la página de admin
      }
      }
    });



    return {
      authenticated,
      username,
      user,
      openLogin,
      t$: useI18n().t,
    };
  },
});

*/
