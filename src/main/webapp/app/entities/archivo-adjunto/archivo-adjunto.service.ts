import axios from 'axios';

import { type IArchivoAdjunto } from '@/shared/model/archivo-adjunto.model';

const baseApiUrl = 'api/archivo-adjuntos';

export default class ArchivoAdjuntoService {
  public find(id: string): Promise<IArchivoAdjunto> {
    return new Promise<IArchivoAdjunto>((resolve, reject) => {
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

  public create(entity: FormData): Promise<IArchivoAdjunto> {
    return new Promise<IArchivoAdjunto>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity, { headers: { 'Content-Type': 'multipart/form-data' } })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: FormData, id: string): Promise<IArchivoAdjunto> {
    return new Promise<IArchivoAdjunto>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${id}`, entity, { headers: { 'Content-Type': 'multipart/form-data' } })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IArchivoAdjunto): Promise<IArchivoAdjunto> {
    return new Promise<IArchivoAdjunto>((resolve, reject) => {
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

  public downloadFile(attachmentId: string): Promise<Blob> {
    return new Promise<Blob>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${attachmentId}/download`, { responseType: 'blob' })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
