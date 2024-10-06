import { type IOficina } from '@/shared/model/oficina.model';

export interface INotificacion {
  id?: string;
  tipo?: string;
  fecha?: Date;
  mensaje?: string;
  leido?: boolean | null;
  destinatarios?: IOficina[] | null;
}

export class Notificacion implements INotificacion {
  constructor(
    public id?: string,
    public tipo?: string,
    public fecha?: Date,
    public mensaje?: string,
    public leido?: boolean | null,
    public destinatarios?: IOficina[] | null,
  ) {
    this.leido = this.leido ?? false;
  }
}
