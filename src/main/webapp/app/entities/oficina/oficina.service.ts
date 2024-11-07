import axios from 'axios';

import type { IPqrs } from '@/shared/model/pqrs.model';
import { type IOficina } from '@/shared/model/oficina.model';
import type { IResponsePag } from '@/shared/interfaces/pagination.interface';
import type { IParamsPqrsPag } from '@/entities/interfaces/pqrs.interface';

const baseApiUrl = 'api/oficinas';

export default class OficinaService {
  public find(id: string): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IOficina): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IOficina): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IOficina): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  //////////////////////////////////Modificacione aqui /////////////77
  // Método para crear una nueva oficina con un usuario responsable
  // async createWithUser(entity: IOficina, responsableId: string) {
  // Valida que el ID del responsable esté presente
  //public createWithUser(entity: IOficina, responsableId: string): Promise<IOficina> {
  public createWithUser(entity: IOficina): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        //.post(`${baseApiUrl}/oficinas/${responsableId}`, entity)
        .post(`${baseApiUrl}/oficinas`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findOficina(id: string): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/oficinas/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * Método para encontrar una oficina asociada a un usuario específico.
   *
   * @param userId - El ID del usuario para el cual se busca la oficina.
   * @returns Una promesa que resuelve con los datos de la oficina correspondiente al usuario.
   */
  public findOficinaUser(userId: string): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/oficinasUser/${userId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * Método para obtener PQRS asociados a un usuario específico con paginación.
   *
   * @param userId - El ID del usuario para el cual se buscan los PQRS.
   * @param params - Parámetros opcionales para la paginación.
   * @returns Una promesa que resuelve con un objeto de tipo IResponsePag<IPqrs>,
   *          que contiene los datos de PQRS paginados.
   *
   * @throws Error si la solicitud a la API falla.
   */
  public findPqrsByUserIdWithPag(params?: IParamsPqrsPag): Promise<IResponsePag<IPqrs>> {
    const userId: string = `${params?.userId}`;

    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${userId}/pqrs-with-pagination`, { params })
        .then(res => {
          const response: IResponsePag<IPqrs> = {
            data: res.data.content,
            page: res.data.number + 1,
            perPage: res.data.size,
            total: res.data.totalElements,
          };

          resolve(response);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
