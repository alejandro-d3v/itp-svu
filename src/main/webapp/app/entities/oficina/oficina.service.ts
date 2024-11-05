import axios from 'axios';

import { type IOficina } from '@/shared/model/oficina.model';

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
  ////////////////////
  public findOficinaUser(idUser: string): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/oficinasUser/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  //oficinas/oficinasUserLogin/erika
  public findOficinaUserLogin(login: string): Promise<IOficina> {
    return new Promise<IOficina>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/oficinasUserLogin/${login}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
