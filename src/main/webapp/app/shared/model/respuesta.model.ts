import { type IPqrs } from '@/shared/model/pqrs.model';

export interface IRespuesta {
  id?: string;
  contenido?: string;
  fechaRespuesta?: Date;
  estado?: string;
  pqr?: IPqrs | null;
}

export class Respuesta implements IRespuesta {
  constructor(
    public id?: string,
    public contenido?: string,
    public fechaRespuesta?: Date,
    public estado?: string,
    public pqr?: IPqrs | null,
  ) {}
}
