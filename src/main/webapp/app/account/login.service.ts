import { useAccountStore } from '@/shared/config/store/account-store';

/**
 * Servicio para manejar la lógica de inicio de sesión.
 */
export default class LoginService {
  private emit: (event: string, ...args: any[]) => void;

  /**
   * Constructor del servicio de inicio de sesión.
   * @param emit - Función para emitir eventos.
   */
  constructor({ emit }: { emit: (event: string, ...args: any[]) => void }) {
    this.emit = emit;
  }

  /**
   * Abre el modal de inicio de sesión.
   */
  public openLogin(): void {
    this.emit('bv::show::modal', 'login-page');
  }

  /**
   * Cierra el modal de inicio de sesión.
   */
  public hideLogin(): void {
    this.emit('bv::hide::modal', 'login-page');
  }

  /**
   * Obtiene el rol del usuario actual.
   * @returns El rol del usuario o null si no está definido.
   */
  public getUserRole(): string | null {
    const accountStore = useAccountStore();
    const account = accountStore.account;

    if (account?.authorities) {
      if (account.authorities.includes('ROLE_ADMIN')) {
        return 'ROLE_ADMIN';
      }
      if (account.authorities.includes('ROLE_USER')) {
        return 'ROLE_USER';
      }
    }

    return null;
  }

  /**
   * Obtiene todos los detalles de la cuenta del usuario actual.
   * @returns Los detalles de la cuenta o null si no están definidos.
   */
  public getAccount(): any | null {
    const accountStore = useAccountStore();
    return accountStore.account || null;
  }
}
